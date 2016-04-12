package controller;

import java.io.File;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Admin;
import model.User;
import model.util.Dialog;
import model.util.View;

/**
 * @author hassan and deep
 *
 */
public class AdminController {

	/**
	 * Buttons for Gui
	 */
	@FXML
	Button add, delete, cancel, save, logout;
	/**
	 * listview for users
	 */
	@FXML
	ListView<User> listview;
	/**
	 * testfield for creating new user
	 */
	@FXML
	TextField newuser;
	/**
	 * labels for showing slected users info
	 */
	@FXML
	Label alluser, curuser, numalbum;

	/**
	 * set the initial screen when logged in
	 */
	@FXML
	protected void initialize() {
		populate();
		alluser.setText("Total users: " + admin.getUsers().size());
		listview.getSelectionModel().select(0);
	}

	/**
	 * index of the current user
	 */
	int index;
	
	/**
	 * singelton instance of admin
	 */
	Admin admin = Admin.getInstance("admin");
	
	/**
	 * observable list for users
	 */
	public ObservableList<User> users = FXCollections.observableList(admin.getUsers());

	/** event handle for all the buttons
	 * @param handler ActionEvnet
	 * @throws IOException input/output exception
	 */
	public void handle(ActionEvent handler) throws IOException {
		Button b = (Button) handler.getSource();
		if (b == logout) {
			admin.make(admin);
			Parent p = FXMLLoader.load(getClass().getResource("/controller/view/userlogin.fxml"));
			View.openScreen("userlogin.fxml", handler, "Log in", p);
		} else if (b == add) {
			foggy(true);
			newuser.clear();
		} else if (b == delete) {
			try {
				Alert alertBox = Dialog.pop(Alert.AlertType.ERROR, "Delete selected user ", "Are you sure you want to delete this?");
				if (alertBox.getResult() == ButtonType.OK) {
					int n = listview.getSelectionModel().getSelectedIndex();
					File f = new File("dat/" + admin.getUsers().get(n).getName());
					f.delete();
					admin.getUsers().remove(n);
					if(n -1 >= 0) {
						listview.getSelectionModel().select(n-1);
					}
					if(admin.getUsers().size()==0){
				           listview.getSelectionModel().clearSelection();
				           users = FXCollections.observableList(admin.getUsers());
				           listview.setItems(users);
					} else {
						users = FXCollections.observableList(admin.getUsers());
						listview.setItems(users);
					}
					if(admin.getUsers().size()==0) {
						foggy(true);
					} else {
					}
				} else {
					alertBox.close();
				}
			} catch (IndexOutOfBoundsException i) {

			}
		} else if (b == cancel) {
			foggy(false);

		} else if (b == save) {
			if(newuser.getText().replaceAll("\\s+","").equals("")) {
			} else {
				User dummy = new User(newuser.getText());
				if (!admin.getUsers().contains(dummy)) {
					admin.addUser(dummy);
					users = FXCollections.observableList(admin.getUsers());
					listview.setItems(users);
					alluser.setText("Total users: " + admin.getUsers().size());
					foggy(false);
				} else {
					Dialog.pop(Alert.AlertType.ERROR, "conflict", "current user already exists!");
				}
			}
		}
	}

	/**for hiding/showing labels and buttons
	 * @param b boolean value
	 */
	public void foggy(boolean b) {
		newuser.setVisible(b);
		save.setVisible(b);
		cancel.setVisible(b);
		alluser.setVisible(!b);
		curuser.setVisible(!b);
		numalbum.setVisible(!b);
	}

	/**
	 * set the listview and observable list
	 */
	public void populate() {
		users = FXCollections.observableList(admin.getUsers());
		listview.setItems(users);

		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {

				int in = listview.getSelectionModel().getSelectedIndex();

				if (in >= 0)
					index = in;
				if (!(admin.getUsers().size()==0)) {
					alluser.setText("Total users: " + admin.getUsers().size());
					curuser.setText("Selected user: " + admin.getUsers().get(index));
					numalbum.setText("Total albums: " + admin.getUsers().get(index).getAlbums().size());
				} 
			}
		});
	}
}
