package application;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

/**
 * Created by javier on 16-11-21.
 */
public class Dialogs {

    /**
     * Shows an information alert.
     * @param title The title of the pop-up window.
     * @param headerText The string in the dialog header area.
     * @param infoText The string in the dialog content area.
     */
    public static void alert(String title, String headerText, String infoText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(infoText);
        alert.showAndWait();
    }

    public static void aboutTheApp(String title, String headerText, String infoText) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setGraphic(new ImageView(Dialogs.class.getResource("sudoku.png").toString()));
        dialog.setContentText(infoText);
        dialog.showAndWait();

    }

}
