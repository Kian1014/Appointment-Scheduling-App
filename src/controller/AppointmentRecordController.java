package controller;

import daoInterfaces.AppointmentDAO;
import helpers.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.text.ParseException;

/**
 * Controller class for appointment record view.
 * Contains Table view to display appointment info.
 * Contains table columns for appointment id, title, description, location, type, start, end, created by, updated by, customer id and name, contact id and name, and user id and name.
 *
 */

public class AppointmentRecordController {

    @FXML
    private AnchorPane appointmentsPane;

    @FXML
    private TableView<Appointment> appTable;

    @FXML
    private TableColumn<?, ?> appointmentIdCol;

    @FXML
    private TableColumn<?, ?> titleCol;

    @FXML
    private TableColumn<?, ?> descriptionCol;

    @FXML
    private TableColumn<?, ?> loctationCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> startCol;

    @FXML
    private TableColumn<?, ?> endCol;

    @FXML
    private TableColumn<?, ?> createDateCol;

    @FXML
    private TableColumn<?, ?> createdByCol;

    @FXML
    private TableColumn<?, ?> lastUpdateCol;

    @FXML
    private TableColumn<?, ?> updatedByCol;

    @FXML
    private TableColumn<?, ?> cusIdCol;

    @FXML
    private TableColumn<?, ?> cusNameCol;

    @FXML
    private TableColumn<?, ?> userIdCol;

    @FXML
    private TableColumn<?, ?> userNameCol;

    @FXML
    private TableColumn<?, ?> contactID;

    @FXML
    private TableColumn<?, ?> contactNameCol;

    /**
     * Initialize method sets table fields to be populated by list of all appointments from database using appointmentdao getter.
     */

    public void initialize() {

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        loctationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appTable.setItems(AppointmentDAO.getAllAppointments());

    }

    /**
     * Action listener for add button, sends user to add appointments screen.
     */

    @FXML
    void addClick(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/AddAppointment.fxml", (Stage) appointmentsPane.getScene().getWindow(), "Add Appointment");
    }

    /**
     * Action listener for delete button, deletes table selection and resets tableview values to reflect updated database info.
     */

    @FXML
    void deleteClick(ActionEvent event) {
        int appID = appTable.getSelectionModel().getSelectedItem().getAppointmentID();
        String type = appTable.getSelectionModel().getSelectedItem().getType();
        AppointmentDAO.deleteAppointment(appID);
        appTable.getItems().clear();
        appTable.getItems().setAll(AppointmentDAO.getAllAppointments());
        Warning.displayAlert("Appointment ID: " + appID + " Type: " + type + " successfully deleted.");
    }

    /**
     * Action listener for return button, sends user back to main screen.
     */

    @FXML
    void returnClick(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/MainScreen.fxml", (Stage) appointmentsPane.getScene().getWindow(), "Main Screen");
    }

    /**
     * Action listener for update button, sends user to update appointment screen. Records current tableview selection appointment in ObjectToBeRecorded helper class.
     */

    @FXML
    void updateClick(ActionEvent event) throws IOException {

        if (appTable.getSelectionModel().getSelectedCells().isEmpty()) {
            return;
        }

        Appointment app = appTable.getSelectionModel().getSelectedItem();
        ObjectToBeUpdated.setObject(app);
        ScreenSwitch.switchScreen("/view/UpdateAppointment.fxml", (Stage) appointmentsPane.getScene().getWindow(), "Update Appointment");

    }

    /**
     * Action listener for view all appointments radio button, sets tableview items to display all appointments.
     */

    @FXML
    void viewAllAppointments(ActionEvent event) {
        appTable.getItems().clear();
        appTable.setItems(AppointmentDAO.getAllAppointments());
    }

    /**
     * Action listener for view appointments by week radio button, sets tableview items to display only appointments within 7 days of users local datetime.
     */

    @FXML
    void viewAppointmentsByWeek(ActionEvent event) throws ParseException {
        appTable.getItems().clear();
        appTable.setItems(Search.getAppointmentsByCurrentWeek());
    }

    /**
     * Action listener for view appointments by month radio button, sets tableview items to display only appointments within users month.
     */

    @FXML
    void viewAppointmentsByMonth(ActionEvent event) throws ParseException {
        appTable.getItems().clear();
        appTable.setItems(Search.getAppointmentsByCurrentMonth());
    }

}
