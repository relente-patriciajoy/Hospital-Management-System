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

    // GET ALL
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
}