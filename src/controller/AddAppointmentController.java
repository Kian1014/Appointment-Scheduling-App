package controller;

import daoInterfaces.AppointmentDAO;
import helpers.HoursMinutes;
import helpers.ScreenSwitch;
import helpers.Search;
import helpers.Warning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.LoggedInUser;

import javax.naming.directory.SearchControls;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

/**
 * Controller class for adding appointments view.
 * Contains text fields for location, appointment id, title, description, and type.
 * Contains comboboxes of strings for start and end hour and minute, as well as ids and names of customers, contacts, and users.
 * Contains datepicker for start and end date.
 * Contains label of local business hours.
 */

public class AddAppointmentController {

    @FXML
    private TextField locationText;

    @FXML
    private TextField appIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField typeText;

    @FXML
    private ComboBox<String> startHourCombo;

    @FXML
    private ComboBox<String> startMinuteCombo;

    @FXML
    private ComboBox<String> endHourCombo;

    @FXML
    private ComboBox<String> endMinuteCombo;

    @FXML
    private DatePicker startDatePicker;


    @FXML
    private ComboBox<String> contactCombo;

    @FXML
    private ComboBox<String> customerCombo;

    @FXML
    private ComboBox<String> userCombo;

    @FXML
    private Label localTimeBusinessHours;

    /**
     * Initialize method to set all combo box values as well as local business time label.
     */

    public void initialize() throws ParseException {

        startHourCombo.getItems().setAll(HoursMinutes.getAllHours());
        startMinuteCombo.getItems().setAll(HoursMinutes.getAllMinutes());
        endHourCombo.getItems().setAll(HoursMinutes.getAllHours());
        endMinuteCombo.getItems().setAll(HoursMinutes.getAllMinutes());
        contactCombo.getItems().setAll(Search.getAllContactIdName());
        customerCombo.getItems().setAll(Search.getAllCusIdName());
        userCombo.getItems().setAll(Search.getAllUserIdName());
        localTimeBusinessHours.setText(HoursMinutes.getLocalBusinessHourStart() + " to " + HoursMinutes.getLocalBusinessHourEnd());

    }

    /**
     * Switches view back to appointment records.
     */

    @FXML
    void onBack(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/AppointmentRecord.fxml", (Stage) userCombo.getScene().getWindow(), "Appointment Records");
    }

    /**
     * Creates appointment from form values and enters it into database.
     * Displays error if a field is empty, chosen time not within business hours, or if there is a conflicting appointment for given customer.
     */

    @FXML
    void onSubmit(ActionEvent event) throws ParseException, IOException {

        if (startMinuteCombo.getSelectionModel().isEmpty() || endMinuteCombo.getSelectionModel().isEmpty() || startHourCombo.getSelectionModel().isEmpty() || endHourCombo.getSelectionModel().isEmpty() || startDatePicker.getValue() == null || contactCombo.getSelectionModel().isEmpty() || userCombo.getSelectionModel().isEmpty() || customerCombo.getSelectionModel().isEmpty()) {
           Warning.displayAlert("One or more fields empty");
            return;
        }

        String startTime = startHourCombo.getValue() + ":" + startMinuteCombo.getValue();
        String endTime = endHourCombo.getValue() + ":" + endMinuteCombo.getValue();

        if (!HoursMinutes.timeWithinBusinessHours(startTime) || !HoursMinutes.timeWithinBusinessHours(endTime)) {
            Warning.displayAlert("Start or end time not within business hours");
            return;
        }

        String location = locationText.getText();
        String title = titleText.getText();
        String description = descriptionText.getText();
        String startDate = startDatePicker.getValue().toString();
        String type = typeText.getText();

        SimpleDateFormat utcDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = localDate.parse(startDate + " " + startTime + ":00");

        Timestamp startDateTime = Timestamp.valueOf(utcDate.format(date));
        date = localDate.parse(startDate + " " + endTime + ":00");
        Timestamp endDateTime = Timestamp.valueOf(utcDate.format(date));

        int customerID = Integer.parseInt((customerCombo.getValue()).split("-")[0]);
        int contactID = Integer.parseInt(contactCombo.getValue().split("-")[0]);
        int userID = Integer.parseInt(userCombo.getValue().split("-")[0]);

        if (Search.conflictingCustomerAppointments(customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(startTime)) || Search.conflictingCustomerAppointments(customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(endTime)) || Search.conflictingCustomerAppointments(customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(startTime), LocalTime.parse(endTime))) {
            Warning.displayAlert("Cannot add appointment, conflicting appoint times with selected customer exist");
            return;
        }

        Appointment appointment = new Appointment(title, description, location, type, startDateTime, endDateTime, new Timestamp(System.currentTimeMillis()), LoggedInUser.getLoggedInUser(), new Timestamp(System.currentTimeMillis()), LoggedInUser.getLoggedInUser(), customerID, userID, contactID);

        AppointmentDAO.addAppointment(appointment);

        ScreenSwitch.switchScreen("/view/AppointmentRecord.fxml", (Stage) appIdText.getScene().getWindow(), "Appointment Records");
    }

}
