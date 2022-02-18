package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection class that handles establishing, retrieving, and ending connection to database server using string fields. Also stores connection reference.
 */

public class DatabaseConnection {

    private static Connection connection;

    private static final String protocol = "jdbc";
    private static final String vendor = "mysql";
    private static final String ip = "wgudb.ucertify.com";
    private static final String dbName = "WJ073wy";
    private static final String username = "U073wy";
    private static final String password = "53688942075";
    private static final String jdbcURL = protocol + ":" + vendor + "://" + ip + ":3306/" + dbName + "?connectionTimeZone=SERVER";

    /**
     * Starts connection to db using driver manager.
     */

    public static Connection startConn() {
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Getter for Connection.
     */

    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes connection to db.
     */

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
