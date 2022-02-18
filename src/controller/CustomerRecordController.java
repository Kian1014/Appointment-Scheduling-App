package controller;

import daoInterfaces.CustomerDAO;
import helpers.ObjectToBeUpdated;
import helpers.ScreenSwitch;
import helpers.Search;
import helpers.Warning;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;

/**
 * Controller class for customer record view.
 * Contains tableview to display customer info.
 * Contains table columns for customer ID, name, address, postal code, phone number, created by, create date, updated by, last updated date, division name, and country name.
 */

public class CustomerRecordController {

    @FXML
    private AnchorPane cusPane;

    @FXML
    private TableView<Customer> cusTable;

    @FXML
    private TableColumn<?, ?> cusIdCol;

    @FXML
    private TableColumn<?, ?> cusNameCol;

    @FXML
    private TableColumn<?, ?> addressCol;

    @FXML
    private TableColumn<?, ?> postalCodeCol;

    @FXML
    private TableColumn<?, ?> phoneCol;

    @FXML
    private TableColumn<?, ?> createDateCol;

    @FXML
    private TableColumn<?, ?> createdByCol;

    @FXML
    private TableColumn<?, ?> lastUpdateCol;

    @FXML
    private TableColumn<?, ?> lastUpdatedByCol;

    @FXML
    private TableColumn<?, ?> divisionCol;

    @FXML
    private TableColumn<?, ?> countryCol;

    /**
     * Intialize method sets customer table to list of all customers based on info pulled from database using customerdao getter.
     */

    public void initialize() {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("cusID"));
        cusNameCol.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("cusDivision"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("cusCountry"));
        cusTable.setItems(CustomerDAO.getAllCustomers());
    }

    /**
     * Action listener for add button, sends user to add customer screen.
     */

    @FXML
    void addClick(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/AddCustomer.fxml", (Stage)cusPane.getScene().getWindow(), "Add Customer");
    }

    /**
     * Action listener for delete button.
     * Displays confirmation box to delete selected customer from table view.
     * If customer exists within an appointment record, will display a warning alert and not delete selected customer.
     * Resets tableview to reflect database list changes.
     * discussion of lambda - lambda expression usage in confirmation alert reduced amount of code needed and improved readability of code.
     */

    @FXML
    void deleteClick(ActionEvent event) {

        if (cusTable.getSelectionModel().getSelectedCells().isEmpty()) {
            return;
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.showAndWait().ifPresent((pressed) -> {
            if (pressed == ButtonType.OK) {
                Customer c = cusTable.getSelectionModel().getSelectedItem();
                if (Search.customerAppointmentExists(c.getCusID())) {
                    Warning.displayAlert("Customer " + c.getCusName() + " has one or more appointments and cannot be deleted");
                    return;
                }
                CustomerDAO.deleteCustomer(c.getCusID());
                Warning.displayAlert("Customer " + c.getCusName() + " successfully deleted.");
                cusTable.getItems().clear();
                cusTable.setItems(CustomerDAO.getAllCustomers());
            }
        });

//        if (Warning.displayConfirmation("Before deleting any customer records, all associated appointments must be deleted first. Delete selected customer?")) {
//            Customer c = cusTable.getSelectionModel().getSelectedItem();
//            if (Search.customerAppointmentExists(c.getCusID())){
//                Warning.displayAlert("Customer " + c.getCusName() + " has one or more appointments and cannot be deleted");
//                return;
//            }
//                CustomerDAO.deleteCustomer(c.getCusID());
//            Warning.displayAlert("Customer " + c.getCusName() + " successfully deleted.");
//            cusTable.getItems().clear();
//            cusTable.setItems(CustomerDAO.getAllCustomers());
//        }
    }

    /**
     * Action listener for return button, sends user back to main screen.
     */

    @FXML
    void returnClick(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/MainScreen.fxml", (Stage) cusPane.getScene().getWindow(), "Main Screen");
    }

    /**
     * Action listener for update button.
     * Sends user to update customer screen if a tableview value is selected.
     * Sets Customer value in ObjectToBeStored helper to transfer data to update cotroller.
     */

    @FXML
    void updateClick(ActionEvent event) throws IOException {

        if (cusTable.getSelectionModel().getSelectedCells().isEmpty()) {
            return;
        }

        Customer c = cusTable.getSelectionModel().getSelectedItem();
        ObjectToBeUpdated.setObject(c);
        ScreenSwitch.switchScreen("/view/UpdateCustomer.fxml", (Stage) cusPane.getScene().getWindow(), "Update Customer");

    }

}
