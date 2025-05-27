package hospital.gui;

import dao.MedicalRecordsDAO;
import model.MedicalRecords;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

public class MedicalRecordsPanel extends JPanel {
    private JTable recordsTable;
    private DefaultTableModel tableModel;
    private JTextField diagnosisField, prescriptionField;
    private JButton addButton, updateButton, deleteButton;
    private int patientId;

    public MedicalRecordsPanel(int patientId) {
        this.patientId = patientId;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 250));

        // Top input form panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Manage Medical Record"),
                BorderFactory.createEmptyBorder(10, 15, 15, 15)));
        inputPanel.setBackground(Color.WHITE);

        // Labels & fields
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        JLabel diagLabel = new JLabel("Diagnosis:");
        diagLabel.setFont(labelFont);
        diagLabel.setForeground(new Color(50, 50, 50));
        diagnosisField = new JTextField();
        diagnosisField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel prescLabel = new JLabel("Prescription:");
        prescLabel.setFont(labelFont);
        prescLabel.setForeground(new Color(50, 50, 50));
        prescriptionField = new JTextField();
        prescriptionField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        inputPanel.add(diagLabel);
        inputPanel.add(diagnosisField);
        inputPanel.add(prescLabel);
        inputPanel.add(prescriptionField);

        // Buttons
        addButton = new JButton("Add Record");
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");

        JButton[] buttons = { addButton, updateButton, deleteButton };
        Color[] btnColors = {
                new Color(34, 139, 34), // Add
                new Color(65, 105, 225), // Update
                new Color(178, 34, 34) // Delete
        };

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setBackground(btnColors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            buttons[i].setFocusPainted(false);
            inputPanel.add(buttons[i]);
        }

        add(inputPanel, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel(new Object[] { "ID", "Date", "Diagnosis", "Prescription" }, 0);
        recordsTable = new JTable(tableModel);
        recordsTable.setFillsViewportHeight(true);
        recordsTable.setGridColor(new Color(200, 200, 200));
        recordsTable.setSelectionBackground(new Color(135, 206, 250)); // Light sky blue
        recordsTable.setSelectionForeground(Color.BLACK);
        recordsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        recordsTable.getTableHeader().setBackground(new Color(230, 230, 230));
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Medical History"));
        add(scrollPane, BorderLayout.CENTER);

        loadRecords();

        // Button actions
        addButton.addActionListener((ActionEvent e) -> {
            String diagnosis = diagnosisField.getText();
            String prescription = prescriptionField.getText();

            if (!diagnosis.isEmpty() && !prescription.isEmpty()) {
                MedicalRecords record = new MedicalRecords(patientId, diagnosis, prescription, LocalDate.now());
                if (MedicalRecordsDAO.addMedicalRecord(record)) {
                    JOptionPane.showMessageDialog(this, "Record added successfully.");
                    clearFields();
                    loadRecords();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add record.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            }
        });

        updateButton.addActionListener((ActionEvent e) -> {
            int selectedRow = recordsTable.getSelectedRow();
            if (selectedRow != -1) {
                int recordId = (int) tableModel.getValueAt(selectedRow, 0);
                String newDiagnosis = diagnosisField.getText();
                String newPrescription = prescriptionField.getText();

                if (!newDiagnosis.isEmpty() && !newPrescription.isEmpty()) {
                    MedicalRecords record = new MedicalRecords(recordId, patientId, newDiagnosis, newPrescription,
                            LocalDate.now());
                    if (MedicalRecordsDAO.updateMedicalRecord(record)) {
                        JOptionPane.showMessageDialog(this, "Record updated.");
                        clearFields();
                        loadRecords();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update record.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill in both fields.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a record to update.");
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            int selectedRow = recordsTable.getSelectedRow();
            if (selectedRow != -1) {
                int recordId = (int) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?",
                        "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (MedicalRecordsDAO.deleteMedicalRecord(recordId)) {
                        JOptionPane.showMessageDialog(this, "Record deleted.");
                        clearFields();
                        loadRecords();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to delete record.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a record to delete.");
            }
        });

        // Table row selection to populate fields
        recordsTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = recordsTable.getSelectedRow();
            if (selectedRow != -1) {
                diagnosisField.setText((String) tableModel.getValueAt(selectedRow, 2));
                prescriptionField.setText((String) tableModel.getValueAt(selectedRow, 3));
            }
        });
    }

    private void loadRecords() {
        tableModel.setRowCount(0);
        List<MedicalRecords> records = MedicalRecordsDAO.getRecordsByPatientId(patientId);
        for (MedicalRecords record : records) {
            tableModel.addRow(new Object[] { record.getId(), record.getRecordDate(), record.getDiagnosis(),
                    record.getPrescription() });
        }
    }

    private void clearFields() {
        diagnosisField.setText("");
        prescriptionField.setText("");
        recordsTable.clearSelection();
    }
}