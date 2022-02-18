package daoInterfaces;

import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact DAO class used to query customers table in database.
 */

public class ContactDAO {

    /**
     * Retrieves all contacts from contacts table and transforms them into contact list.
     * @return Returns ObservableList of Contacts.
     */

    public static ObservableList<Contact> getAllContacts () {

        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {

            String sql = "select * from contacts";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(id,name,email);
                allContacts.add(contact);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allContacts;

    }


}
