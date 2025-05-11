package dao;

import model.Patient;
import java.sql.*;

public class PatientDAO {
    // INSERT (STATIC)
    public static boolean addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getContact());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}