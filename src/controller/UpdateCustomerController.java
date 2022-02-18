package controller;

import daoInterfaces.CustomerDAO;
import helpers.ObjectToBeUpdated;
import helpers.ScreenSwitch;
import helpers.Search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;

/**
 * Controller class for updating customer view.
 * Contains text fields for customer name, address, postal code, and phone number.
 * Contains combo box of strings for country names and division names.
 */

public class UpdateCustomerController {

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneNumText;

    @FXML
    private TextField cusIdText;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private ComboBox<String> divisionComboBox;

    /**
     * Retrieves customer info from ObjectToBeUpdated class and sets all text fields and combo boxes according to retrieved customers field values.
     */

    public void initialize() {

        Customer c = (Customer) ObjectToBeUpdated.getObject();

        cusIdText.setText(Integer.toString(c.getCusID()));
        nameText.setText(c.getCusName());
        addressText.setText(c.getCusAddress());
        postalCodeText.setText(c.getPostalCode());
        phoneNumText.setText(c.getPhoneNum());
        countryComboBox.getItems().setAll(Search.getAllCountryNames());
        countryComboBox.getSelectionModel().select(c.getCusCountry());
        String selectedCountry = countryComboBox.getSelectionModel().getSelectedItem();
        divisionComboBox.getItems().setAll(Search.getAllDivisionsInCountry(selectedCountry));
        divisionComboBox.getSelectionModel().select(c.getCusDivision());

    }

    /**
     * Action listener for back button, sends user to customer records.
     */

    @FXML
    void onBack(ActionEvent event) throws IOException {
        ScreenSwitch.switchScreen("/view/CustomerRecord.fxml", (Stage) nameText.getScene().getWindow(), "Customer Record");
    }

    /**
     * Action listener for submit button.
     * Creates customer object updates database customer table based on form field values and customer id using customerdao.
     * Switches screen to customer records.
     */

    @FXML
    void onSubmit(ActionEvent event) throws IOException {

        Customer c = (Customer) ObjectToBeUpdated.getObject();

        String name = nameText.getText();
        String address = addressText.getText();
        String postal = postalCodeText.getText();
        String phoneNum = phoneNumText.getText();
        String countryName = countryComboBox.getValue();
        String divisionName = divisionComboBox.getValue();
        int divisionID = Search.getDivisionByName(divisionName).getDivisionID();

        Customer cUpdated = new Customer(c.getCusID(), name, address, postal, phoneNum, c.getCreateDate(), c.getCreatedBy(), c.getLastUpdate(), c.getLastUpdatedBy(), divisionID, divisionName, countryName);

        CustomerDAO.updateCustomer(cUpdated);
        ScreenSwitch.switchScreen("/view/CustomerRecord.fxml", (Stage) nameText.getScene().getWindow(), "Customer Records");
    }

    /**
     * Action listener for country combo box, sets division combo box to display divisions in selected country.
     */

    @FXML
    public void onCountrySelect(ActionEvent actionEvent) {
        divisionComboBox.getItems().clear();
        if (countryComboBox.getItems() != null) {
            divisionComboBox.setDisable(false);
            String countryName = countryComboBox.getValue();
            divisionComboBox.getItems().setAll(Search.getAllDivisionsInCountry(countryName));
        }
    }

}
