package dao;

import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection conn;

    public PatientDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (name, age, gender, contact) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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

    // GET ALL
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patients";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // GET BY ID (optional)
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM Patients WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}