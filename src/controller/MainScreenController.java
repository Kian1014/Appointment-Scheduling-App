package controller;

import helpers.ScreenSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for main screen view. Contains exit button reference to get stage and switch screens.
 */

public class MainScreenController {


    @FXML
    private Button exitBtn;

    /**
     * Action listener for appointments button, sends user to appointments record screen.
     */

    @FXML
    void toAppointments(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/AppointmentRecord.fxml", (Stage) exitBtn.getScene().getWindow(), "Appointments");
    }

    /**
     * Action listener for customer records button, sends user to customer records screen.
     */

    @FXML
    void toCusRecords(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/CustomerRecord.fxml", (Stage) exitBtn.getScene().getWindow(), "Customer Records");
    }


    /**
     * Action listener for reports button, sends user to reports screen.
     */

    @FXML
    void toReports(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/Reports.fxml", (Stage) exitBtn.getScene().getWindow(), "Reports");
    }

}
