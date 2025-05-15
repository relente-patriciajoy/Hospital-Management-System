package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DatabaseConnection {
    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("Could not find 'config.properties' in classpath");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // Always return a fresh connection
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}