package dao;

import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // CREATE - Add new patient
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

    // READ - Get all patients
    public static List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patients";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setAge(rs.getInt("age"));
                patient.setGender(rs.getString("gender"));
                patient.setContact(rs.getString("contact"));
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    // UPDATE - Update existing patient
    public static boolean updatePatient(Patient patient) {
        String sql = "UPDATE Patients SET name=?, age=?, gender=?, contact=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, patient.getName());
            stmt.setInt(2, patient.getAge());
            stmt.setString(3, patient.getGender());
            stmt.setString(4, patient.getContact());
            stmt.setInt(5, patient.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Delete patient by ID
    public static boolean deletePatient(int id) {
        String sql = "DELETE FROM Patients WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}