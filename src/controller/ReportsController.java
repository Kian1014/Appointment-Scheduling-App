package controller;

import daoInterfaces.AppointmentDAO;
import daoInterfaces.CustomerDAO;
import helpers.ScreenSwitch;
import helpers.Search;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;

/**
 * Controller for reports view.
 * Contains combo box for appointment type, month to filter appointment by, contact name, and division name.
 * Contains text area to display report information.
 */

public class ReportsController {

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private ComboBox<String> monthCombo;

    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private ComboBox<String> divisionCombo;

    @FXML
    private TextArea reportText;

    /**
     * Sets combo boxes using list return values from search helper class. Sets month using list of month names.
     */

    public void initialize () {

        List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        typeCombo.getItems().setAll(Search.getAllAppTypes());
        monthCombo.getItems().setAll(months);

        contactCombo.getItems().setAll(Search.getAllContactIdName());

        divisionCombo.getItems().setAll(Search.getAllDivisionNames());

    }

    /**
     * Action listener for back button, sends user to main screen.
     */

    @FXML
    void backToMain(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/MainScreen.fxml", (Stage) typeCombo.getScene().getWindow(), "Main Screen");
    }

    /**
     * Iterates through all appointments and generates list of appointments in which contact is associated in the text area.
     */

    @FXML
    void generateContactSchedule(ActionEvent event) {

        if (contactCombo.getValue().isEmpty()) {
            return;
        }

        StringBuilder contactSchedule = new StringBuilder();

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
             if (a.getContactID() == Integer.parseInt(contactCombo.getValue().split("-")[0])) {
                 //appointment ID, title, type and description, start date and time, end date and time, and customer ID
                 contactSchedule.append("Appointment ID: ").append(a.getAppointmentID()).append(" Type: ").append(a.getType()).append(" Description: ").append(a.getDescription()).append(" Start: ").append(a.getStart()).append(" End: ").append(a.getEnd()).append(" Customer ID: ").append(a.getCustomerID()).append("\n");
            }
        }

        if (contactSchedule.toString().isEmpty()){
            return;
        }

        reportText.clear();
        reportText.setText(contactSchedule.toString());

    }

    /**
     * Filters through list of appointments and sets text area to display number of appointments that match given type and month value.
     */

    @FXML
    void generateNumOfApptByTypeMonth(ActionEvent event) {

        if(monthCombo.getValue().isEmpty() || typeCombo.getValue().isEmpty()) {
            return;
        }

        int counter = 0;



        for (Appointment a : AppointmentDAO.getAllAppointments()) {

            String aMonth = LocalDate.parse(a.getStart().toString().split(" ")[0]).getMonth().toString();

            if (a.getType().equals(typeCombo.getValue()) && aMonth.equals(monthCombo.getValue().toUpperCase())) {
                counter++;
            }
        }

        reportText.clear();
        reportText.setText("Number of appointments of type : " + typeCombo.getValue() + " for month of " + monthCombo.getValue() + ": " + counter);

    }

    /**
     * Sets text area to display number of customers in the division selected in combo box.
     */

    @FXML
    void generateNumOfCusByDivision(ActionEvent event) {

        int counter = 0;

        int divisionID = Search.getDivisionByName(divisionCombo.getValue()).getDivisionID();

        for (Customer c : CustomerDAO.getAllCustomers()) {
            if (c.getDivisionID() == divisionID) {
                counter++;
            }
        }

        reportText.clear();
        reportText.setText("Number of customers in division " + divisionCombo.getValue() + " : " + counter);

    }

}


