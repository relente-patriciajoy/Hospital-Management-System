package dao;

import model.MedicalRecords;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MedicalRecordsDAO {

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
                        rs.getDate("record_date").toLocalDate()
                    );
                    records.add(record);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }
}