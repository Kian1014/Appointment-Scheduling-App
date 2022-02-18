package controller;

import daoInterfaces.AppointmentDAO;
import helpers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.LoggedInUser;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * Controller class for updating appointments view.
 * Contains text fields for location, appointment id, title, description, and type.
 * Contains comboboxes of strings for start and end hour and minute, as well as ids and names of customers, contacts, and users.
 * Contains datepicker for start and end date.
 * Contains label of local business hours.
 */

public class UpdateAppointmentController {

    @FXML
    private TextField appIdText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

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

    @FXML
    private TextField typeText;

    /**
     * Retrieves appointment info from ObjectToBeUpdated class and sets text field and datepicker values to reflect retrieved appointments info.
     * Sets business hours label to local business hours.
     */

    public void initialize() throws ParseException {

        Appointment app = (Appointment) ObjectToBeUpdated.getObject();

        appIdText.setText(Integer.toString(app.getAppointmentID()));
        titleText.setText(app.getTitle());
        descriptionText.setText(app.getDescription());
        locationText.setText(app.getDescription());
        typeText.setText(app.getType());

        String cusIdName = app.getCustomerID() + "-" + Search.getCustomerByID(app.getCustomerID()).getCusName();
        String contactIdName = app.getContactID() + "-" + Search.getContactByID(app.getContactID()).getContactName();
        String userIdName = app.getUserID() + "-" + Search.getUserByID(app.getUserID()).getUsername();

        contactCombo.getItems().setAll(Search.getAllContactIdName());
        customerCombo.getItems().setAll(Search.getAllCusIdName());
        userCombo.getItems().setAll(Search.getAllUserIdName());

        contactCombo.getSelectionModel().select(contactIdName);
        customerCombo.getSelectionModel().select(cusIdName);
        userCombo.getSelectionModel().select(userIdName);

        startHourCombo.getItems().setAll(HoursMinutes.getAllHours());
        startMinuteCombo.getItems().setAll(HoursMinutes.getAllMinutes());
        endHourCombo.getItems().setAll(HoursMinutes.getAllHours());
        endMinuteCombo.getItems().setAll(HoursMinutes.getAllMinutes());

        startHourCombo.getSelectionModel().select(HoursMinutes.getHoursFromTimestamp(app.getStart()));
        startMinuteCombo.getSelectionModel().select(HoursMinutes.getMinutesFromTimestamp(app.getStart()));
        endHourCombo.getSelectionModel().select(HoursMinutes.getHoursFromTimestamp(app.getEnd()));
        endMinuteCombo.getSelectionModel().select(HoursMinutes.getMinutesFromTimestamp(app.getEnd()));

        startDatePicker.setValue(HoursMinutes.getLocalDateFromTimestamp(app.getStart()));
        startDatePicker.setPromptText("Original date: " + HoursMinutes.getLocalDateFromTimestamp(app.getStart()));

        localTimeBusinessHours.setText(HoursMinutes.getLocalBusinessHourStart() + " to " + HoursMinutes.getLocalBusinessHourEnd());

    }

    /**
     * Action listener for back button, sends user to appointment records.
     */

    @FXML
    void onBack(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/AppointmentRecord.fxml", (Stage) appIdText.getScene().getWindow(), "Appointment Records");
    }

    /**
     * Action listener for submit button.
     * Displays alert warning if one or mor fields are left empty, or if start or end time are not within business hours.
     * Displays warning if there is a conflicting appointment with associated customer.
     * Updates appointment values in table and returns to appointments records on successful submission.
     */

    @FXML
    void onSubmit(ActionEvent event) throws ParseException, IOException {

        if (startMinuteCombo.getSelectionModel().isEmpty() || endMinuteCombo.getSelectionModel().isEmpty() || startHourCombo.getSelectionModel().isEmpty() || endHourCombo.getSelectionModel().isEmpty() || startDatePicker.getValue() == null || contactCombo.getSelectionModel().isEmpty() || userCombo.getSelectionModel().isEmpty() || customerCombo.getSelectionModel().isEmpty()) {
            Warning.displayAlert("One or more fields empty");
            return;
        }

        String startTime = startHourCombo.getValue() + ":" + startMinuteCombo.getValue();
        String endTime = endHourCombo.getValue() + ":" + endMinuteCombo.getValue();

        int customerID = Integer.parseInt((customerCombo.getValue()).split("-")[0]);
        int contactID = Integer.parseInt(contactCombo.getValue().split("-")[0]);
        int userID = Integer.parseInt(userCombo.getValue().split("-")[0]);

        if (!HoursMinutes.timeWithinBusinessHours(startTime) || !HoursMinutes.timeWithinBusinessHours(endTime)) {
            Warning.displayAlert("Start or end time not within business hours");
            return;
        }

        if (Search.conflictingCustomerAppointments(Integer.parseInt(appIdText.getText()), customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(startTime)) || Search.conflictingCustomerAppointments(Integer.parseInt(appIdText.getText()), customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(endTime)) || Search.conflictingCustomerAppointments(Integer.parseInt(appIdText.getText()), customerID, LocalDate.from(startDatePicker.getValue()), LocalTime.parse(startTime), LocalTime.parse(endTime)) ) {
            Warning.displayAlert("Cannot add appointment, conflicting appoint times with selected customer exist");
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

        Appointment appointment = new Appointment(title, description, location, type, startDateTime, endDateTime, new Timestamp(System.currentTimeMillis()), LoggedInUser.getLoggedInUser(), new Timestamp(System.currentTimeMillis()), LoggedInUser.getLoggedInUser(), customerID, userID, contactID);
        appointment.setAppointmentID(Integer.parseInt(appIdText.getText()));

        AppointmentDAO.updateAppointment(appointment);

        ScreenSwitch.switchScreen("/view/AppointmentRecord.fxml", (Stage) appIdText.getScene().getWindow(), "Appointment Records");

    }

}
