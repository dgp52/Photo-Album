package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;
import model.Album;
import model.Photo;
import model.SerialImage;
import model.User;
import model.util.Dialog;
import model.util.Search;
import model.util.View;

/**
 * @author hassan and deep
 *
 */
public class UsersController {

	/**
	 * buttons for gui
	 */
	@FXML
	Button delete, rename, create, savere, cancelre, savecr, cancelcr, addimage, deleteimage, searchalbum, logout, go,
			moveto, copyto;

	/**
	 * date picker for searching by dates
	 */
	@FXML
	DatePicker dateto, datefrom;

	/**
	 * combo box for searching type
	 */
	@FXML
	ComboBox<String> cb;

	/**
	 * to show image
	 */
	@FXML
	ImageView imageone;

	/**
	 * text field for searching and creating new album
	 */
	@FXML
	TextField albumname, search;

	/**
	 *contains gridpane and images
	 */
	TilePane vb;

	/**
	 * labels for showing photo's info
	 */
	@FXML
	Label numphotos, al, oldest, range, msg, searchby, movetol, movetol1;

	/**
	 * listview for albums
	 */
	@FXML
	ListView<Album> listview;

	/**
	 * scroll pane for showing large quantity of photos
	 */
	@FXML
	ScrollPane scrollp;

	/**
	 * combo boxes for move to and copy to
	 */
	@FXML
	ComboBox<Album> albumlist, albumlistM;

	/**
	 * first time when user selects an album
	 */
	boolean isClicked = false;

	/**
	 * 
	 */
//	Image image;
	/**
	 * keeps track of last selected image
	 */
	int lastI, isSearchC;
	/**
	 * temporary save the image for slide show
	 */
	public static Image tempImg;
	/**
	 * save image array for slide show
	 */
	public static Image[] images;
	/**
	 * temp save index
	 */
	public static int k;
	/**
	 * index of selected photo
	 */
	int del;

	/**
	 * singleton instance of admin
	 */
	Admin admin = Admin.getInstance("admin");

	/**
	 * index of the album selected
	 */
	static int indAl;
	/**
	 * instance of the user logged in
	 */
	User user = admin.getUsers().get(LoginController.ind);
	/**
	 * query for searching
	 */
	List<String> query = new ArrayList<>();
	/**
	 * result of the search
	 */
	List<Photo> result = new ArrayList<>();
	/**
	 * list of images to be shown in scroll pane
	 */
	static List<Photo> show = new ArrayList<>();

	/**
	 * observable list for albums
	 */
	public ObservableList<Album> albums = FXCollections
			.observableList(admin.getUsers().get(LoginController.ind).getAlbums());

	/**
	 * set the screen when user logs in
	 * 
	 * @throws MalformedURLException
	 */
	@FXML
	protected void initialize() throws MalformedURLException {
		populate();
		scrollp.setVisible(false);
		cb.getItems().addAll("People", "Location", "Selfies", "All Selfies", "Date", "Other");
		cb.setPromptText("Search by");
		cb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ob, String s, String s1) {
				searchingFor(s1);
			}
		});
	}

	/**
	 * format the date
	 * 
	 * @param ld
	 *            LocalDate
	 * @return String
	 */
	public String formatDate(LocalDate ld) {
		Date date = Date.from(ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		return new SimpleDateFormat("MM/dd/yyyy").format(date);
	}

	/**
	 * hide/show search related gui
	 * 
	 * @param b
	 *            boolean
	 * @param c
	 *            boolean
	 */
	public void visibilityCB(boolean b, boolean c) {
		search.setVisible(b);
		go.setVisible(c);
		dateto.setVisible(!b);
		datefrom.setVisible(!b);
	}

	/**
	 * show specific gui when searching
	 * 
	 * @param s
	 *            String
	 */
	public void searchingFor(String s) {
		switch (s) {
		case "People":
			visibilityCB(true, true);
			go.setLayoutX(525.0);
			go.setLayoutY(19.0);
			break;

		case "Location":
			visibilityCB(true, true);
			go.setLayoutX(525.0);
			go.setLayoutY(19.0);
			break;

		case "Date":
			visibilityCB(false, true);
			dateto.setStyle("-fx-base: #ffffff;");
			datefrom.setStyle("-fx-base: #ffffff;");
			go.setLayoutX(509.0);
			go.setLayoutY(19.0);
			break;

		case "Selfies":
			visibilityCB(true, true);
			go.setLayoutX(525.0);
			go.setLayoutY(19.0);
			break;

		case "All Selfies":
			search.setVisible(false);
			go.setVisible(true);
			go.setLayoutX(400.0);
			go.setLayoutY(19.0);
			break;

		case "Other":
			visibilityCB(true, true);
			go.setLayoutX(525.0);
			go.setLayoutY(19.0);
			break;

		default:
			break;
		}
	}

	/**
	 * handles all the event for buttons
	 * 
	 * @param handler
	 *            ActionEvent
	 * @throws IOException
	 *             input/output exception
	 * @throws NoSuchAlgorithmException
	 *             no such argument exception
	 */
	public void handle(ActionEvent handler) throws IOException, NoSuchAlgorithmException {
		Button b = (Button) handler.getSource();
		if (b == logout) {
			admin.make(admin);
			Parent p = FXMLLoader.load(getClass().getResource("/controller/view/userlogin.fxml"));
			View.openScreen("userlogin.fxml", handler, "Log in", p);
		} else if (b == searchalbum) {
			isSearchC = 1;
			foggyCR(false, false);
			create.setVisible(false);
			rename.setVisible(false);
			delete.setVisible(false);
			albumname.requestFocus();
			al.setVisible(true);
		} else if (b == delete) {
			try {
				Alert alertBox = Dialog.pop(Alert.AlertType.CONFIRMATION, "delete selected album? ",
						"Are you sure you want to delete this album?");
				if (alertBox.getResult() == ButtonType.OK) {
					int n = getSelectedIndex();
					user.getAlbums().remove(n);
					if (n - 1 >= 0) {
						listview.getSelectionModel().select(n - 1);
					}

					if (user.getAlbums().size() == 0) {
						listview.getSelectionModel().clearSelection();
						albums = FXCollections.observableList(user.getAlbums());
						listview.setItems(albums);

					} else {
						albums = FXCollections.observableList(user.getAlbums());
						listview.setItems(albums);
						albumlist.setItems(albums);
						albumlistM.setItems(albums);
					}

				} else {
					alertBox.close();
				}
			} catch (IndexOutOfBoundsException i) {

			}

		} else if (b == rename) {
			foggyRE(false, false);
			albumname.requestFocus();
			al.setVisible(false);
		} else if (b == create) {
			albumname.requestFocus();
			al.setVisible(false);
			foggyCR(false, false);
		} else if (b == savecr) {

			if (!albumname.getText().isEmpty()) {
				Album ale = new Album(albumname.getText());

				if (user.getAlbums().contains(ale)) {
					Dialog.pop(Alert.AlertType.ERROR, "Album", "album already exists!");
				} else {
					if (isSearchC == 1) {
						Album bum = new Album(albumname.getText());
						bum.setallp(result);
						bum.setallh(result);
						user.getAlbums().add(bum);
						isSearchC = 0;
					} else {
						user.addAlbum(ale);
						al.setVisible(false);
					}
					albums = FXCollections.observableList(user.getAlbums());
					listview.setItems(albums);
					albumlist.setItems(albums);
					albumlistM.setItems(albums);
					listview.requestFocus();
					listview.getSelectionModel().select(user.getAlbums().size() - 1);
					scrollp.setVisible(true);
					msg.setVisible(false);
					albumname.clear();
					foggyCR(true, false);
					isClicked = false;
					addimage.setDisable(false);
					delete.setVisible(true);
					delete.setDisable(true);
					rename.setDisable(true);
					create.setVisible(true);
					create.setDisable(false);
					rename.setVisible(true);
					searchalbum.setVisible(false);
				}
			}

		} else if (b == cancelcr) {
			albumname.clear();
			foggyCR(true, false);
			if (isSearchC != 1) {
				create.setDisable(false);
				searchalbum.setVisible(false);
			}else{
				searchalbum.setVisible(true);
			}
			create.setVisible(true);
			rename.setVisible(true);
			
			rename.setDisable(true);
			delete.setVisible(true);
			delete.setDisable(true);
			al.setVisible(false);

		} else if (b == savere) {
			if (!albumname.getText().isEmpty()) {
				Album dummy = new Album(albumname.getText());

				if (!user.getAlbums().contains(dummy)) {
					int k = listview.getSelectionModel().getSelectedIndex();

					List<Photo> p = new ArrayList<>();
					p.addAll(listview.getSelectionModel().getSelectedItem().getPhotos());
					user.getAlbums().remove(k);
					dummy.setallp(p);
					user.getAlbums().add(k, dummy);
					al.setVisible(false);
					albums = FXCollections.observableArrayList(admin.getUsers().get(LoginController.ind).getAlbums());
					listview.setItems(albums);
					albumlist.setItems(albums);
					albumlistM.setItems(albums);
					listview.getSelectionModel().select(k);
					listview.requestFocus();
					searchalbum.setVisible(false);
				} else {
					Dialog.pop(Alert.AlertType.ERROR, "", "Album already exists!");
				}
				albumname.clear();
				foggyRE(true, false);
				isClicked = false;
			} else {

			}

		} else if (b == cancelre) {
			albumname.clear();
			foggyRE(true, false);
			al.setVisible(false);
		} else if (b == addimage) {
			indAl = listview.getSelectionModel().getSelectedIndex();
			fileChooser(handler);
			listViewSelection(indAl);
		} else if (b == go) {
			rename.setDisable(true);
			listview.getSelectionModel().clearSelection();
			if (isClicked) {
				vb.getChildren().clear();
			}
			checkifE();
			result.clear();
			Search.hash.clear();
			if (cb.getValue().equalsIgnoreCase("Date")) {
				LocalDate localDateFrom = datefrom.getValue();
				LocalDate localDateto = dateto.getValue();
				Calendar g = Calendar.getInstance();
				Calendar s = Calendar.getInstance();
				Date dd = Date.from(localDateto.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date ddd = Date.from(localDateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant());
				g.setTime(ddd);
				s.setTime(dd);
				result = Search.range(user.getAlbums(), g, s);
				Album a = new Album("default");
				a.setallp(result);
				checkifE();
				showImages(a.getPhotos());
			} else {
				searchalbum.setVisible(false);
			}
			if (cb.getValue().equalsIgnoreCase("all selfies")) {
				result = Search.selfies(user.getAlbums());
				Album a = new Album("default");
				a.setallp(result);
				showImages(a.getPhotos());
				checkifE();
			} else {
				searchalbum.setVisible(false);
			}
			if (!search.getText().isEmpty()) {
				String[] s = search.getText().split(",");
				for (int i = 0; i < s.length; i++) {
					s[i] = s[i].trim().toLowerCase();
				}
				query.addAll(Arrays.asList(s));
				if (cb.getValue().equalsIgnoreCase("people"))
					sear("people");
				if (cb.getValue().equalsIgnoreCase("other"))
					sear("other");
				if (cb.getValue().equalsIgnoreCase("location"))
					sear("location");
				if (cb.getValue().equalsIgnoreCase("selfies"))
					sel("people");
				if (cb.getValue().equalsIgnoreCase("selfies"))
					sel("location");
				checkifE();
				query.clear();
			} else {
				Dialog.pop(Alert.AlertType.ERROR, "Error", "Can't be Done");
			}

			if (cb.getValue().equalsIgnoreCase("selfies")) {
				// sel(s, g);
			}

		} else if (b == deleteimage) {
			try {
				Alert alertBox = Dialog.pop(Alert.AlertType.CONFIRMATION, "delete selected picture",
						"Are you sure you want to delete this picture?");

				if (alertBox.getResult() == ButtonType.OK) {
					showImages(user.getAlbums().get(indAl).getPhotos());
					user.getAlbums().get(indAl).getPhotos().remove(del);
					user.getAlbums().get(indAl).getHashes().remove(del);
					user.getAlbums().get(indAl).getdates().remove(del);
					listViewSelection(indAl);
					listview.requestFocus();
					if (user.getAlbums().get(indAl).getPhotos().size() == 0) {
						listview.getSelectionModel().select(-1);
						scrollp.setContent(null);
					} else {
						showImages(user.getAlbums().get(indAl).getPhotos());
					}
				}
			} catch (IndexOutOfBoundsException i) {
			}
		} else if (b == copyto) {
			Album al = albumlist.getSelectionModel().getSelectedItem();
			al.addPhotos(user.getAlbums().get(indAl).getPhotos().get(del));
		} else if (b == moveto) {
			Album al = albumlistM.getSelectionModel().getSelectedItem();
			if (!al.getHashes().contains(user.getAlbums().get(indAl).getHashes().get(del))) {
				al.addPhotos(user.getAlbums().get(indAl).getPhotos().get(del));
				user.getAlbums().get(indAl).getPhotos().remove(del);
				user.getAlbums().get(indAl).getHashes().remove(del);
				vb.getChildren().clear();
				showImages(user.getAlbums().get(indAl).getPhotos());
			} else {
				Dialog.pop(Alert.AlertType.ERROR, "Error", "picture already exists in that album");
			}
		}
	}

	/**
	 * hiding/showing buttons
	 */
	public void checkifE() {
		if (vb.getChildren().size() > 0) {
			rename.setDisable(true);
			searchalbum.setVisible(true);
			create.setVisible(true);
			create.setDisable(true);
			delete.setDisable(true);

		} else if (search.getText().isEmpty()) {
			defaultLa();
			searchalbum.setVisible(false);
			delete.setDisable(true);
			delete.setVisible(true);
			rename.setDisable(true);
			rename.setVisible(true);

		} else {
			defaultLa();
			searchalbum.setVisible(false);
			delete.setDisable(true);
			delete.setVisible(true);
			rename.setDisable(true);
			rename.setVisible(true);
		}
	}

	/**
	 * searching for categories
	 * 
	 * @param s
	 *            String
	 * @throws MalformedURLException
	 *             malformed url exception
	 */
	public void sear(String s) throws MalformedURLException {
		listview.getSelectionModel().clearSelection();
		result.addAll(Search.searchPeople(user.getAlbums(), query, s));
		Album n = new Album("default");
		n.setallp(result);
		showImages(n.getPhotos());
		query.clear();
		numphotos.setText("Number of Photos: " + result.size());
		oldest.setText("Oldest: " + n.getOldest());
		range.setText("Range: " + n.getRange());
	}

	/**
	 * searching for selfies
	 * 
	 * @param s
	 *            String
	 * @throws MalformedURLException
	 *             malformed url exception
	 */
	public void sel(String s) throws MalformedURLException {
		result = Search.otherselfie(user.getAlbums(), query, s);
		Album a = new Album("default");
		a.setallp(result);
		showImages(a.getPhotos());
		numphotos.setText("Number of Photos: " + result.size());
		oldest.setText("Oldest: " + a.getOldest());
		range.setText("Range: " + a.getRange());
	}

	/**
	 * selected album
	 * 
	 * @return index of selected album
	 */
	public int getSelectedIndex() {
		return listview.getSelectionModel().getSelectedIndex();
	}

	/**
	 * hide/show labels
	 * 
	 * @param b
	 *            boolean
	 * @param r
	 *            boolean
	 */
	public void foggyCR(boolean b, boolean r) {
		numphotos.setVisible(b);
		oldest.setVisible(b);
		range.setVisible(b);

		albumname.setVisible(!b);
		savecr.setVisible(!b);
		cancelcr.setVisible(!b);

		savere.setVisible(r);
		cancelre.setVisible(r);
	}

	/**
	 * hide/show buttons
	 * 
	 * @param b
	 *            boolean
	 * @param c
	 *            boolean
	 */
	public void foggyRE(boolean b, boolean c) {
		numphotos.setVisible(b);
		oldest.setVisible(b);
		range.setVisible(b);

		albumname.setVisible(!b);
		savere.setVisible(!b);
		cancelre.setVisible(!b);

		savecr.setVisible(c);
		cancelcr.setVisible(c);
	}

	/**
	 * uploading file from computer
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws IOException
	 *             input/output exception
	 * @throws NoSuchAlgorithmException
	 *             no such algorithm exception
	 */
	public void fileChooser(ActionEvent e) throws IOException, NoSuchAlgorithmException {

		Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select an image");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
		File file = fileChooser.showOpenDialog(st);

		if (file != null) {
			Calendar cdl = Calendar.getInstance();
			cdl.setTime(new Date(file.lastModified()));
			BufferedImage g = ImageIO.read(file);
			SerialImage serial = new SerialImage(g);
			Photo photo = new Photo(serial);
			photo.setDate(new Date(file.lastModified()));
			user.getAlbums().get(indAl).addPhotos(photo);
			showImages(user.getAlbums().get(indAl).getPhotos());
		}
	}

	/**
	 * show images in scroll pane
	 * 
	 * @param list
	 *            list of photos
	 * @throws MalformedURLException
	 *             malformed url exception
	 */
	public void showImages(List<Photo> list) throws MalformedURLException {
		show.clear();
		show.addAll(list);
		vb = new TilePane();
		vb.setPrefColumns(4);
		vb.setPadding(new Insets(10, 10, 10, 10));
		vb.setVgap(10.0);
		vb.setHgap(10.0);

		images = new Image[list.size()];
		ImageView[] iView = new ImageView[list.size()];
		Label[] la = new Label[list.size()];
		GridPane[] gp = new GridPane[list.size()];

		for (int i = 0; i < iView.length; i++) {
			la[i] = new Label();
			la[i].setText(list.get(i).getCaption());
			la[i].setPrefWidth(scrollp.getWidth() / 5);
			la[i].setWrapText(true);
			gp[i] = new GridPane();
			RowConstraints row1 = new RowConstraints();
			RowConstraints row2 = new RowConstraints();
			gp[i].getRowConstraints().addAll(row1, row2);

			images[i] = list.get(i).getSerial().getFXimg();

			iView[i] = new ImageView(images[i]);
			iView[i].setFitWidth(scrollp.getWidth() / 5);
			iView[i].setFitHeight(scrollp.getHeight() / 5);
			gp[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (lastI == -1) {
					} else {
						gp[lastI].setStyle("-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0), 0, 0, 0, 0);");
					}

					if (event.getClickCount() == 2) {
						try {
							tempImg = list.get(vb.getChildren().indexOf(event.getSource())).getSerial().getFXimg();
							k = vb.getChildren().indexOf(event.getSource());

							fullscreen("displayimage.fxml", "Image");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						scrollp.setStyle("-fx-focus-color: transparent;");
						deleteimage.setDisable(false);
						albumlist.setDisable(false);
						albumlistM.setDisable(false);
						moveto.setDisable(false);
						movetol.setDisable(false);
						movetol1.setDisable(false);
						copyto.setDisable(false);
						del = vb.getChildren().indexOf(event.getSource());
						((GridPane) vb.getChildren().get(vb.getChildren().indexOf(event.getSource())))
								.setStyle("-fx-background-color: #7DB4E7;"
										+ "-fx-effect: dropshadow(gaussian, rgba(125, 180, 231), 4,4, 0, 0);");
					}

					lastI = vb.getChildren().indexOf(event.getSource());
				}
			});
			gp[i].add(iView[i], 0, 0);
			gp[i].add(la[i], 0, 1);
			vb.getChildren().addAll(gp[i]);
			scrollp.setContent(vb);
		}
	}

	/**
	 * open new screen when double click on image
	 * 
	 * @param ss
	 *            name of fxml
	 * @param title
	 *            title of new window
	 * @throws IOException
	 *             input/output exception
	 */
	public void fullscreen(String ss, String title) throws IOException {
		Parent p = FXMLLoader.load(getClass().getResource("/controller/view/" + ss));
		Scene s = new Scene(p);
		s.getStylesheets().add(PhotoAlbum.class.getResource("application.css").toExternalForm());
		Stage st = new Stage();
		st.setScene(s);
		st.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if (listview.getSelectionModel().getSelectedIndex() == -1) {
					try {
						Album num = new Album("e");
						num.setallp(result);
						showImages(num.getPhotos());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} else {
					listViewSelection(listview.getSelectionModel().getSelectedIndex());
				}

			}
		});
		st.initModality(Modality.WINDOW_MODAL);
		st.initOwner(PhotoAlbum.primaryStage);
		st.setResizable(false);
		st.setTitle(title);
		st.show();
	}

	/**
	 * defaults labels for photo's info
	 */
	public void defaultLa() {
		numphotos.setText("Number of Photos: None");
		oldest.setText("Oldest photo: None");
		range.setText("Range: None");
	}

	/**
	 * updates gui with selected album
	 * 
	 * @param select
	 *            index of slected album
	 */
	public void listViewSelection(int select) {
		int in = select;
		int size = 0;
		if (in >= 0)
			indAl = in;
		try {
			size = user.getAlbums().get(indAl).getPhotos().size();
		} catch (Exception b) {

		}

		if (user.getAlbums().size() <= 0) {
			// defaultLa();
		} else {

			numphotos.setText("Number of Photos: " + size);

			if (size != 0) {
				String s = user.getAlbums().get(indAl).getOldest();
				oldest.setText("Oldest photo: " + s);

				if (size > 1) {
					range.setText("Range: " + user.getAlbums().get(indAl).getRange());
				}
				if (size == 1) {
					range.setText("Range: " + s);
				}
			}

			if (size == 0) {
				oldest.setText("Oldest photo: None");
				range.setText("Range: None");
			}

			try {
				showImages(user.getAlbums().get(indAl).getPhotos());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * set the observable list and listview for albums
	 */
	public void populate() {
		albums = FXCollections.observableList(admin.getUsers().get(LoginController.ind).getAlbums());
		listview.setItems(albums);
		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
			@Override
			public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
				cb.setDisable(false);
				al.setVisible(false);
				searchby.setDisable(false);
				scrollp.setContent(null);
				searchalbum.setVisible(false);
				create.setDisable(false);
				delete.setVisible(true);
				albumname.clear();
				foggyRE(true, false);
				delete.setDisable(false);
				rename.setDisable(false);
				addimage.setDisable(false);
				deleteimage.setDisable(true);
				albumlist.setDisable(true);
				albumlistM.setDisable(true);
				copyto.setDisable(true);
				moveto.setDisable(true);
				movetol.setDisable(true);
				movetol1.setDisable(true);
				lastI = -1;
				if (isClicked) {
					vb.getChildren().clear();
				} else {
					isClicked = true;
					scrollp.setVisible(true);
					msg.setVisible(false);
				}
				listViewSelection(listview.getSelectionModel().getSelectedIndex());
			}
		});

		albumlist.setItems(albums);
		albumlistM.setItems(albums);
	}

}
