package daoInterfaces;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import database.DatabaseConnection;
import helpers.Search;
import helpers.StringBuild;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.LoggedInUser;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Customer DAO class used to query and update customers table in database.
 */

public class CustomerDAO  {

    /**
     * Retrieves all customers from customers table and transforms them into Customer List.
     * @return Returns ObservableList of all Customers.
     */

    public static ObservableList<Customer> getAllCustomers() {

        ObservableList <Customer> allCustomers = FXCollections.observableArrayList();

        try {

            String sql = "select * from customers";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Customer cus;

                int cusID = rs.getInt("Customer_ID");
                String cusName = rs.getString("Customer_Name");
                String cusAddress = rs.getString("Address");
                String cusPostalCode = rs.getString("Postal_Code");
                String cusPhone = rs.getString("Phone");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionID = rs.getInt("Division_ID");
                String divisionName = Search.getDivisionByID(DivisionDAO.getAllDivisions(),divisionID).getDivisionName();
                String countryName = Search.getCountryByID(CountryDAO.getAllCountries(),divisionID).getCountryName();

                cus = new Customer(cusID,cusName,cusAddress,cusPostalCode, cusPhone, createDate, createdBy, lastUpdated, lastUpdatedBy, divisionID, divisionName, countryName);

                allCustomers.add(cus);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Updates customers table using customer param values.
     * @param c Customer data used to replace old data.
     */

    public static void updateCustomer(Customer c) {

        try {

            String name = StringBuild.surroundWithQuote(c.getCusName());
            String address = StringBuild.surroundWithQuote(c.getCusAddress());
            String postalCode = StringBuild.surroundWithQuote(c.getPostalCode());
            String phoneNum = StringBuild.surroundWithQuote(c.getPhoneNum());
            String updatedBy = StringBuild.surroundWithQuote(LoggedInUser.getLoggedInUser());
            int divisionID = c.getDivisionID();


            String sql = "update customers " +
                         "set Customer_Name = " + name + ", Address = " + address + ", Phone = " + phoneNum + ", Postal_Code = " + postalCode +
                         ", Division_ID = " + c.getDivisionID() + ", Last_Updated_By = " + updatedBy + ", Division_ID = " + divisionID + ", Last_Update = CURRENT_TIMESTAMP"  +
                         " where Customer_ID = " + c.getCusID();

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Updates customers table by deleting customer data from table.
     * @param customerID Customer ID used to identify and delete customer row.
     */

    public static void deleteCustomer(int customerID) {

        try{

            String sql = "delete from customers where Customer_ID = " + customerID;
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Inserts new customer row into customer table with customer param info
     * @param customer Customer whose info is to be inserted.
     */

    public static void addCustomer(Customer customer) {

        try{

            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Created_By, Last_Updated_By) " +
                    "values (\'" + customer.getCusName() + "\', \'" + customer.getCusAddress() + "\', \'" + customer.getPostalCode() +
                    "\', \'" + customer.getPhoneNum() + "\', \'" + customer.getDivisionID() + "\', \'" +
                    LoggedInUser.getLoggedInUser() + "\', \'" + LoggedInUser.getLoggedInUser() + "\')";

            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
