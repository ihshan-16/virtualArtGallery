
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnUtil {

    private static Connection connection; 

    public static Connection getConnection(String propertyFileName) {
        if (connection == null) {
            try {
                Properties properties = DBPropertyUtil.loadProperties(propertyFileName);
                String driver = properties.getProperty("driver");
                String url = properties.getProperty("url");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Database connection established successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("Database driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Failed to establish database connection.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection.");
                e.printStackTrace();
            }
        }
    }
}


