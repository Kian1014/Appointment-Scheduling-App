package helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Helper class for switching between views for reducing repetitive code.
 */

public class ScreenSwitch {

    /**
     * Switches views.
     * @param viewPath ViewPath to be switched to.
     * @param stage Stage to be modified.
     * @param windowName New window name.
     */

    public static void switchScreen(String viewPath, Stage stage, String windowName) throws IOException {
        FXMLLoader loader = new FXMLLoader(ScreenSwitch.class.getResource(viewPath));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle(windowName);
        stage.show();
    }
}
