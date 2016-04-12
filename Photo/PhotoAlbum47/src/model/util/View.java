package model.util;

import java.io.IOException;

import controller.PhotoAlbum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Admin;

/**
 * @author hassan and deep
 *util class.  it provides all the new screen capabilities
 */
public class View {

	/**
	 * private constructor for View
	 */
	private View() {
		// TODO Auto-generated constructor stub
	}

	/**opens new screen
	 * @param ss name of fxml
	 * @param e ActionEvent
	 * @param title name of the window
	 * @param p Parent window
	 * @throws IOException input/output exception
	 */
	public static void openScreen(String ss, ActionEvent e, String title, Parent p) throws IOException {
		Scene s = new Scene(p);
		s.getStylesheets().add(PhotoAlbum.class.getResource("application.css").toExternalForm());;
		Stage st = (Stage) ((Node) e.getSource()).getScene().getWindow();
		st.setScene(s);
		st.setTitle(title);
		st.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				if (ss.equalsIgnoreCase("useralbums.fxml")) {
					Admin admin = Admin.getInstance("admin");
					try {
						admin.make(admin);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (ss.equalsIgnoreCase("adminscreen.fxml")) {
					Admin admin = Admin.getInstance("admin");
					try {
						admin.make(admin);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		st.setResizable(false);
		st.show();
	}

}
