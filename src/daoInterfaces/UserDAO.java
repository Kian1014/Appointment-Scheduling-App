package daoInterfaces;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.SqlStatement;
import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LoggedInUser;
import model.UserCredentials;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * User DAO class that queries User info from user table and login verification.
 */

public class UserDAO {

    /**
     * Retrieves all Users from user table and transforms them into a list of UserCredentials.
     * @return Returns a list of UserCredentials.
     */

    public static ObservableList<UserCredentials> getAllUsers() {

        ObservableList<UserCredentials> allUsers = FXCollections.observableArrayList();

        try{

            String sql = "select * from users";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String username = rs.getString("User_Name");
                int userId = rs.getInt("User_ID");

                UserCredentials uc = new UserCredentials(userId, username);
                allUsers.add(uc);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allUsers;

    }

    /** Boolean method that takes verifies login credentials (username/password) against list of users credentials. List of users is populated from DB.
     * @param username Username used when logging in.
     * @param password Password used when logging in.
     * @return Returns true if param credentials match with some DB credentials.
     * */
    public static boolean loginVerification(String username, String password) {

        ArrayList<UserCredentials> allUsers = new ArrayList<>();
        boolean loginVerified = false;

        try {
            String sql = "select * from users";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                allUsers.add(new UserCredentials(userID,userName,userPassword));
            }
            for (UserCredentials uc : allUsers) {
                if (username.equals(uc.getUsername()) && password.equals(uc.getPassword())) {
                    loginVerified = true;
                    LoggedInUser.setUserID(uc.getUserID());
                    LoggedInUser.setLoggedInUser(uc.getUsername());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return loginVerified;

    }

}
