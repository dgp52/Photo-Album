package controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Admin;
import model.User;
import model.util.Dialog;
import model.util.View;

/**
 * @author hassan and deep
 *
 */
public class LoginController {

	/**
	 * button for gui
	 */
	@FXML
	Button login;

	/**
	 * textfield for adding username
	 */
	@FXML
	TextField username;

	/**
	 * singleton instance of admin
	 */
	Admin admin = Admin.getInstance("admin");
	/**
	 * index of the logged in user in admin's list of users
	 */
	static int ind;

	/**
	 * @param handler ActoinEvent
	 * @throws IOException input/output Exception
	 * @throws ClassNotFoundException class not found exception
	 */
	public void handle(ActionEvent handler) throws IOException, ClassNotFoundException {
		Button b = (Button) handler.getSource();
		if (b == login) {
			if (username.getText().isEmpty()) {
				username.setPromptText("Enter a username");
			} else {
				if (username.getText().trim().equals("admin")) {
					try {
						File f = new File("data/admin");
						if (f.exists()) {
							admin = Admin.remake();
							Parent p = FXMLLoader.load(getClass().getResource("/controller/view/adminscreen.fxml"));
							View.openScreen("adminscreen.fxml", handler, "Admin", p);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}

				} else {
					admin = Admin.remake();
					String g = username.getText().trim();
					File f = new File("data/" + g);
					if (f.exists()) {
						User dummy = new User(g);
						ind = admin.getUsers().indexOf(dummy);
						Parent p = FXMLLoader.load(getClass().getResource("/controller/view/useralbums.fxml"));
						View.openScreen("useralbums.fxml", handler, "User: " + username.getText(), p);
					}else {
						Dialog.pop(Alert.AlertType.ERROR, g, "User doesn't exist");
					}
				}
			}
		}
	}
}
