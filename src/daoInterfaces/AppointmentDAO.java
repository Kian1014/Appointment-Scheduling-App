package daoInterfaces;

import database.DatabaseConnection;
import helpers.Search;
import helpers.StringBuild;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * Appointment DAO class to execute queries and updates on appointments table in database
 */

public class AppointmentDAO {

/**
 * Retrieves all appointments from appointments table and transforms set into appointment list.
 * @return Returns ObservableList of Appointments
 */

    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        try {

            String sql = "select * from appointments";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                Timestamp createDate = rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String updatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                String cusName = Search.getCustomerByID(customerID).getCusName();
                String userName = Search.getUserByID(userID).getUsername();
                String contactName = Search.getContactByID(contactID).getContactName();

                Appointment appointment = new Appointment(appointmentID, title,description, location, type, start, end, createDate, createdBy, lastUpdated, updatedBy, customerID, userID, contactID, cusName,userName,contactName);

                allAppointments.add(appointment);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allAppointments;

    }

    /**
     * Executes update on appointments table row where id is matched.
     * @param appointment Appointment with data to overwrite old appointment values
     */

    public static void updateAppointment(Appointment appointment) {

        try {

            String title = StringBuild.surroundWithQuote(appointment.getTitle());
            String description = StringBuild.surroundWithQuote(appointment.getDescription());
            String location = StringBuild.surroundWithQuote(appointment.getLocation());
            String type = StringBuild.surroundWithQuote(appointment.getType());
            String start = StringBuild.surroundWithQuote(appointment.getStart().toString());
            String end = StringBuild.surroundWithQuote(appointment.getEnd().toString());
            String lastUpdate = StringBuild.surroundWithQuote(appointment.getLastUpdated().toString());
            String updatedBy = StringBuild.surroundWithQuote(appointment.getUpdatedBy());
            int customerID = appointment.getCustomerID();
            int userID = appointment.getUserID();
            int contactID = appointment.getContactID();
            int appointmentID = appointment.getAppointmentID();

            String sql = "update appointments " +
                         "set Title = " + title + ", Description = " + description + ", Location = " + location + ", Type = " + type + ", Start = " + start + ", End = " + end + ", Last_Update = CURRENT_TIMESTAMP"  + ", Last_Updated_By = " + updatedBy + ", Customer_ID = " + customerID + ", User_ID = " + userID + ", Contact_ID = " + contactID + " " +
                         "where Appointment_ID = " + appointmentID;
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts new appointment into appointments table based on given appointment value.
     * @param appointment Appointment with values to be inserted
     */

    public static void addAppointment(Appointment appointment) {

        try {

            String title = StringBuild.surroundWithQuote(appointment.getTitle());
            String description = StringBuild.surroundWithQuote(appointment.getDescription());
            String location = StringBuild.surroundWithQuote(appointment.getLocation());
            String start = StringBuild.surroundWithQuote(appointment.getStart().toString());
            String type = StringBuild.surroundWithQuote(appointment.getType());
            String end = StringBuild.surroundWithQuote(appointment.getEnd().toString());
            String createdBy = StringBuild.surroundWithQuote(appointment.getCreatedBy());
            String updatedBy = StringBuild.surroundWithQuote(appointment.getUpdatedBy());
            int customerId = appointment.getCustomerID();
            int userID = appointment.getUserID();
            int contactID = appointment.getContactID();

            String sql = "insert into appointments (Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                         "values (" + title + ", " + description + ", " + location + ", " + type + ", " + start + ", " + end + ", " + createdBy + ", " + updatedBy + ", " + customerId + ", "+ userID + ", " + contactID + ")";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Deletes appointment from appointments table given PK ID.
     * @param appointmentID Appointment ID used to locate and delete appointment.
     */

    public static void deleteAppointment (int appointmentID) {

        try {

            String sql = "delete from appointments where Appointment_ID = " + appointmentID;
            System.out.println(sql);
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
