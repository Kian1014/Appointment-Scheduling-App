package model;

//store logged in user ID and Name

/**
 * Class that represents the user which is logged in to program.
 * Has static string field for logged in user name and static int field for users id, which are set at time of successful log in.
 */

public class LoggedInUser {

    private static String loggedInUser;
    private static int userID;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        LoggedInUser.userID = userID;
    }

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(String loggedInUser) {
        LoggedInUser.loggedInUser = loggedInUser;
    }
}
