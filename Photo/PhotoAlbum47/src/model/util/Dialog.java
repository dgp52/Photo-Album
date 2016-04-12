package model.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @author hassan and deep
 * util class.  it provides all the error capabilities
 *
 */
public class Dialog {

	/**
	 * private constructor
	 */
	private Dialog() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param c Alert.AlertType type of dialog
	 * @param message message of dialog
	 * @param s name of the dialog
	 * @return Alert box
	 */
	public static Alert pop(Alert.AlertType c ,String message, String s) {
		Alert alertBox = new Alert(c, message, ButtonType.OK, ButtonType.CANCEL);
		alertBox.setContentText(s);
		alertBox.showAndWait();
		return alertBox;
	}

}
