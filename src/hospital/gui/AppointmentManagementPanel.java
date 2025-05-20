package hospital.gui;

import dao.AppointmentDAO;
import model.Appointment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentManagementPanel extends JPanel {
    private JTextField txtId, txtPatientId, txtDoctorName, txtDate;
    private DefaultTableModel tableModel;
    private JTable table;
    private AppointmentDAO appointmentDAO;

    /**
     * @param appointmentDAO
     */
    public AppointmentManagementPanel(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
        setLayout(new BorderLayout());

        // Input Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Appointment Form"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        txtId = new JTextField(10);
        txtId.setEditable(false);
        JLabel lblPatientId = new JLabel("Patient ID:");
        txtPatientId = new JTextField(10);
        JLabel lblDoctorName = new JLabel("Doctor Name:");
        txtDoctorName = new JTextField(10);
        JLabel lblDate = new JLabel("Appointment Date (yyyy-MM-dd):");
        txtDate = new JTextField(10);

        JButton btnAdd = new JButton("Add Appointment");
        JButton btnModify = new JButton("Update Appointment");
        JButton btnCancel = new JButton("Cancel Appointment");

        // Layout Rows
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(lblId, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(txtId, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(lblDoctorName, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        formPanel.add(txtDoctorName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(lblPatientId, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(txtPatientId, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(lblDate, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        formPanel.add(txtDate, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        formPanel.add(btnAdd, gbc);
        gbc.gridx = 1;
        formPanel.add(btnModify, gbc);
        gbc.gridx = 2;
        formPanel.add(btnCancel, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[] { "ID", "Patient ID", "Doctor", "Date", "Status" }, 0);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                String status = getValueAt(row, 4).toString();
                if ("Cancelled".equalsIgnoreCase(status)) {
                    comp.setForeground(Color.RED);
                } else {
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadAppointments();

        // Table row selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                txtPatientId.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtDoctorName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                txtDate.setText(tableModel.getValueAt(selectedRow, 3).toString());
            }
        });

        // Add Button
        btnAdd.addActionListener(e -> {
            try {
                int patientId = Integer.parseInt(txtPatientId.getText().trim());
                String doctorName = txtDoctorName.getText().trim();
                LocalDate date = LocalDate.parse(txtDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);

                String status = "Active";
                Appointment appt = new Appointment(0, patientId, doctorName, date.atStartOfDay(), "Active");
                if (appointmentDAO.addAppointment(appt)) {
                    JOptionPane.showMessageDialog(this, "Appointment added.");
                    loadAppointments();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add appointment.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        // Modify Button
        btnModify.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select an appointment to modify.");
                return;
            }

            try {
                int id = Integer.parseInt(txtId.getText().trim());
                int patientId = Integer.parseInt(txtPatientId.getText().trim());
                String doctorName = txtDoctorName.getText().trim();
                LocalDate date = LocalDate.parse(txtDate.getText().trim(), DateTimeFormatter.ISO_LOCAL_DATE);
                String status = tableModel.getValueAt(table.getSelectedRow(), 4).toString();

                Appointment appt = new Appointment(id, patientId, doctorName, date.atStartOfDay(), status);
                if (appointmentDAO.updateAppointment(appt)) {
                    JOptionPane.showMessageDialog(this, "Appointment updated.");
                    loadAppointments();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update appointment.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
            }
        });

        // Cancel Button
        btnCancel.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an appointment to cancel.");
                return;
            }

            int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "Cancel this appointment?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (appointmentDAO.cancelAppointment(id)) {
                    JOptionPane.showMessageDialog(this, "Appointment cancelled.");
                    loadAppointments();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to cancel appointment.");
                }
            }
        });
    }

    private void loadAppointments() {
        tableModel.setRowCount(0);
        List<Appointment> list = appointmentDAO.getAllAppointments();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Appointment appt : list) {
            tableModel.addRow(new Object[] {
                    appt.getId(),
                    appt.getPatientId(),
                    appt.getDoctorName(),
                    appt.getAppointmentDate().toLocalDate().format(formatter),
                    appt.getStatus()
            });
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtPatientId.setText("");
        txtDoctorName.setText("");
        txtDate.setText("");
        table.clearSelection();
    }
}