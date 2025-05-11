package dao;

import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

public class DatabaseConnection {
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream("config.properties"));

                String url = props.getProperty(
                        "jdbc:sqlserver://PATRICIAJOY\\\\localhost:1433;databaseName=HospitalDB;encrypt=true;trustServerCertificate=true;");
                String user = props.getProperty("LMS_ADMIN");
                String password = props.getProperty("122636");

                connection = DriverManager.getConnection(url, user, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}