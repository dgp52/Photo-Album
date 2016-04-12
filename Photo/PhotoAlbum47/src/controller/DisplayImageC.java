package controller;

import java.io.IOException;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Admin;
import model.Photo;
import model.util.View;

/**
 * @author hassan and deep
 *
 */
public class DisplayImageC {

	/**
	 *  buttons for Gui
	 */
	@FXML
	Button back, savecap, cancelcap, addcap, addtag, forward, backward, savetag, canceltag;

	/**
	 * pane to show ImageView
	 */
	@FXML
	Pane tagview;
	
	/**
	 * tool bar to hold buttons
	 */
	@FXML
	ToolBar toolbar;
	
	/**
	 * check box for specifying whehter picture is a selfie
	 */
	@FXML
	CheckBox selfiec;

	/**
	 * labels for show tags and caption
	 */
	@FXML
	Label captionl, peoplel, locationl, me, otherl, peoplev, datela, timela, locationv, otherv;

	/**
	 * text areas to edit tags adn caption
	 */
	@FXML
	TextArea capedit, tagedit, peoplet, locationt, othert;

	/**
	 * 
	 */
	boolean isCap;

	/**
	 * index of the photo in slide show
	 */
	int slideNum = UsersController.k;
	/**
	 * to show current image in slide show
	 */
	@FXML
	ImageView imagev;
	/**
	 * current photo
	 */
	Photo p;

	/**
	 * singleton instance of amdin
	 */
	Admin admin = Admin.getInstance("admin");

	/**
	 * set the initial screen when this screen opens
	 */
	@FXML
	protected void initialize() {
		p = UsersController.show.get(UsersController.k);
		imagev.setImage(UsersController.tempImg);
		setarea(peoplet,"people");
		setarea(locationt,"location");
		setarea(othert,"other");
		setCaption(p, 200.0);
		setTag(p);
		setDate(p);
		setselfie(p);
	}

	/**to set caption
	 * @param po Photo
	 * @param si double width
	 */
	public void setCaption(Photo po, double si){
		captionl.setText("Caption: " + po.getCaption());
		captionl.setPrefWidth(si);
		captionl.setWrapText(true);
	}
	
	/**to set the tags
	 * @param po Photo
	 */
	public void setTag(Photo po){
		String[] photo = new String[po.getPeople().size()];
		po.getPeople().toArray(photo);
		peoplev.setText("People: " + Arrays.toString(photo).replace("[", "").replace("]", ""));
		
		String[] loco = new String[po.getLocation().size()];
		po.getLocation().toArray(loco);
		locationv.setText("Location: " + Arrays.toString(loco).replace("[", "").replace("]", ""));
		
		String[] ot = new String[po.getOther().size()];
		po.getOther().toArray(ot);
		otherv.setText("Other: " + Arrays.toString(ot).replace("[", "").replace("]", ""));
	}
	
	/**to set the selfie check
	 * @param po Photo
	 */
	public void setselfie(Photo po){
		if(po.isSelfie()){
			me.setText("Selfie: yes");
		} else {
			me.setText("Selfie: no");
		}
	}
	
	/**handles all the events for buttons
	 * @param handler ActionEvent
	 * @throws IOException Input/output Exception
	 */
	public void handle(ActionEvent handler) throws IOException {
		Button b = (Button) handler.getSource();
		if (b == back) {
			Parent p = FXMLLoader.load(getClass().getResource("/controller/view/useralbums.fxml"));
			View.openScreen("useralbums.fxml", handler, "User", p);
		} else if (b == savecap) {
			p.setCaption(capedit.getText());
			setCaption(p, toolbar.getWidth());
			foggyCap(false);
		} else if (b == cancelcap) {
			foggyCap(false);
			captionl.setPrefWidth(toolbar.getWidth());
			captionl.setWrapText(true);
		} else if (b == addcap) {
			foggyCap(true);
			capedit.setText(p.getCaption());
		} else if (b == addtag) {
			foggyTag(true);
			setarea(peoplet,"people");
			setarea(locationt,"location");
			setarea(othert,"other");
			if (p.isSelfie())
				selfiec.setSelected(true);
		} else if (b == forward) {
			foggyCap(false);
			foggyTag(false);
			if (!(slideNum == UsersController.images.length - 1)) {
				imagev.setImage(UsersController.images[slideNum + 1]);
				slideNum++;
				p = admin.getUsers().get(LoginController.ind).getAlbums().get(UsersController.indAl).getPhotos()
						.get(slideNum);
				setCaption(p, toolbar.getWidth());
				setTag(p);
				setDate(p);
				setselfie(p);
			}
		} else if (b == backward) {
			foggyCap(false);
			foggyTag(false);
			if (!(slideNum == 0)) {
				imagev.setImage(UsersController.images[slideNum - 1]);
				slideNum--;
				p = admin.getUsers().get(LoginController.ind).getAlbums().get(UsersController.indAl).getPhotos()
						.get(slideNum);
				setCaption(p, toolbar.getWidth());
				setTag(p);
				setDate(p);
				setselfie(p);
			}
		} else if (b == savetag) {
			addPtag();
			addLtag();
			addOtag();
			foggyTag(false);
			setlabel(peoplev, "people");
			setlabel(locationv, "location");
			setlabel(otherv, "other");
			if (selfiec.isSelected()) {
				p.setSelfit(true);
			} else {
				p.setSelfit(false);
			}
			p = UsersController.show.get(slideNum);
			setTag(p);
			setselfie(p);
		} else if(b == canceltag) {
			foggyTag(false);
		}
	}

	/** set the label for date
	 * @param ph Photo
	 */
	public void setDate(Photo ph) {
		datela.setText("Date taken: " + ph.getDate());
		timela.setText(ph.getTime());
	}

	/**
	 * adding people tags
	 */
	public void addPtag() {
		if (!peoplet.getText().isEmpty()) {
			String[] sa = peoplet.getText().split(",");
			for (int i = 0; i < sa.length; i++) {
				sa[i] = sa[i].trim();
			}
			p.getTags().setPeople(Arrays.asList(sa));
		}
	}

	/**
	 * adding location tags
	 */
	public void addLtag() {
		if (!locationt.getText().isEmpty()) {
			String[] sa = locationt.getText().split(",");
			for (int i = 0; i < sa.length; i++) {
				sa[i] = sa[i].trim();
			}
			p.getTags().setLocation(Arrays.asList(sa));
		}
	}

	/**
	 * adding other tags
	 */
	public void addOtag() {
		if (!othert.getText().isEmpty()) {
			String[] sa = othert.getText().split(",");
			for (int i = 0; i < sa.length; i++) {
				sa[i] = sa[i].trim();
			}
			p.getTags().setOhter(Arrays.asList(sa));
		}
	}

	/** setting the labels
	 * @param l Label
	 * @param t String
	 */
	public void setlabel(Label l, String t) {
		String s = "";
		for (int i = 0; i < p.getTags().getTags(t).size(); i++) {
			s = s.concat(p.getTags().getTags(t).get(i) + " ");
			if (i >= 3) {
				s = s.concat("...");
				break;
			}

		}
		l.setText(s);
	}


	/** setting the text area for tags
	 * @param area TextArea
	 * @param t String 
	 */
	public void setarea(TextArea area, String t) {
		area.clear();
		for (String s : p.getTags().getTags(t)) {
			area.appendText(s + ",");
		}
	}

	/**hide/show caption
	 * @param b boolean
	 */
	public void foggyCap(boolean b) {
		capedit.setVisible(b);
		captionl.setVisible(!b);
		savecap.setVisible(b);
		cancelcap.setVisible(b);
	}

	/**
	 * @param b
	 */
	public void foggyTag(boolean b) {
		tagview.setVisible(b);
	}
}
