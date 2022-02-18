package model;

/**
 * Model class based on users object from database.
 * Contains string fields for user name and user password.
 * Contains integer field for user ID.
 * Has getters/setters for all fields.
 */

public class UserCredentials {

    private int userID;
    private String username;
    private String password;

    public UserCredentials(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }

    public UserCredentials(int userID, String username) {
        this.userID = userID;
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
