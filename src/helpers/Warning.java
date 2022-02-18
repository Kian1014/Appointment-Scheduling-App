package helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Helper class to display warnings and reduce redundant code.
 */

public class Warning {

    /**
     * Displays a warning popup alert with given message.
     * @param alertMessage Message to be displayed.
     */

    public static void displayAlert(String alertMessage){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setContentText(alertMessage);
        a.show();
    }
    //
    //    public static boolean displayConfirmation(String confirmationMessage) {
    //        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
    //        a.setContentText(confirmationMessage);
    //        Optional<ButtonType> result = a.showAndWait();
    //        return result.isPresent() && result.get() == ButtonType.OK;
    //    }

}
