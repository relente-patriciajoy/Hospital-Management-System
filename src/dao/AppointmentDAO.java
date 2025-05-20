package dao;

import model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    private Connection conn;

    public AppointmentDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERT
    public boolean addAppointment(Appointment appt) {
        String sql = "INSERT INTO Appointments (patient_id, doctor_name, appointment_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appt.getPatientId());
            stmt.setString(2, appt.getDoctorName());
            stmt.setTimestamp(3, Timestamp.valueOf(appt.getAppointmentDate()));
            stmt.setString(4, appt.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ ALL
    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointments";
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Appointment appt = new Appointment(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("doctor_name"),
                        rs.getTimestamp("appointment_date").toLocalDateTime(),
                        rs.getString("status"));
                list.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public boolean updateAppointment(Appointment appt) {
        String sql = "UPDATE Appointments SET patient_id = ?, doctor_name = ?, appointment_date = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appt.getPatientId());
            stmt.setString(2, appt.getDoctorName());
            stmt.setTimestamp(3, Timestamp.valueOf(appt.getAppointmentDate()));
            stmt.setString(4, appt.getStatus());
            stmt.setInt(5, appt.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cancel
    public boolean cancelAppointment(int id) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Cancelled");
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}