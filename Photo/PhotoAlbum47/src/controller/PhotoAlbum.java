package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author hassan and deep
 *
 */
public class PhotoAlbum extends Application {
	
	/**
	 * primary stag
	 */
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
	    PhotoAlbum.primaryStage = primaryStage;
		try {
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("view/userlogin.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(PhotoAlbum.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Log in");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args String
	 */
	public static void main(String[] args) {
		launch(args);
	}
}