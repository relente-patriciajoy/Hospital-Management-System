package dao;

import model.MedicalRecords;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MedicalRecordsDAO {

    // CREATE
    public static boolean addMedicalRecord(MedicalRecords record) {
        String sql = "INSERT INTO MedicalRecords (patient_id, diagnosis, prescription, record_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatientId());
            stmt.setString(2, record.getDiagnosis());
            stmt.setString(3, record.getPrescription());
            stmt.setDate(4, Date.valueOf(record.getRecordDate()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ
    public static List<MedicalRecords> getRecordsByPatientId(int patientId) {
        List<MedicalRecords> records = new ArrayList<>();
        String sql = "SELECT * FROM MedicalRecords WHERE patient_id = ? ORDER BY record_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MedicalRecords record = new MedicalRecords(
                            rs.getInt("id"),
                            rs.getInt("patient_id"),
                            rs.getString("diagnosis"),
                            rs.getString("prescription"),
                            rs.getDate("record_date").toLocalDate());
                    records.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    // UPDATE (by ID and fields)
    public static boolean updateMedicalRecord(int id, String diagnosis, String prescription) {
        String sql = "UPDATE MedicalRecords SET diagnosis = ?, prescription = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, diagnosis);
            stmt.setString(2, prescription);
            stmt.setInt(3, id);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE (overloaded version accepting MedicalRecords object)
    public static boolean updateMedicalRecord(MedicalRecords record) {
        return updateMedicalRecord(record.getId(), record.getDiagnosis(), record.getPrescription());
    }

    // DELETE
    public static boolean deleteMedicalRecord(int id) {
        String sql = "DELETE FROM MedicalRecords WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}