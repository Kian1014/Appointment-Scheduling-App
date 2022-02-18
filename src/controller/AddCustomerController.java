package controller;

import daoInterfaces.CustomerDAO;
import helpers.ScreenSwitch;
import helpers.Search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.LoggedInUser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Controller class for adding customer view.
 * Contains text fields for customer name, address, postal code, and phone number.
 * Contains combo box of strings for country names and division names.
 */

public class AddCustomerController {

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumText;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> divisionComboBox;

    /**
     * Sets country name values for country combobox.
     */

    public void initialize () {
        countryComboBox.getItems().setAll(Search.getAllCountryNames());
    }

    /**
     * Action listener for country combobox, sets division combo box with divisions within selected country and enables box.
     */

    @FXML
    void onCountrySelect(ActionEvent event) {
        divisionComboBox.getItems().clear();
        if (countryComboBox.getItems() != null) {
            divisionComboBox.setDisable(false);
            String countryName = countryComboBox.getValue();
            divisionComboBox.getItems().setAll(Search.getAllDivisionsInCountry(countryName));
        }
    }

    /**
     * Action listener for back button, sends user back to customer records.
     */

    @FXML
    void onBack(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/CustomerRecord.fxml", (Stage) addressText.getScene().getWindow(), "Customer Records");
    }

    /**
     * Action listener for submit button, takes form fields and creates customer object. Inserts customer into database.
     */

    @FXML
    void onSubmit(ActionEvent event) throws IOException {

        String name = nameText.getText();
        String address = addressText.getText();
        String postalCode = postalCodeText.getText();
        String phoneNum = phoneNumText.getText();
        String countryName = countryComboBox.getValue();
        String divisionName = divisionComboBox.getValue();
        int divisionID = Search.getDivisionByName(divisionName).getDivisionID();

        Customer c = new Customer(name, address, postalCode, phoneNum, divisionID);
        CustomerDAO.addCustomer(c);

        ScreenSwitch.switchScreen("/view/CustomerRecord.fxml", (Stage) addressText.getScene().getWindow(), "Customer Records");

    }

}
