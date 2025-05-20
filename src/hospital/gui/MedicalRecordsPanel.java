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

        // Top input form
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Manage Medical Record"));

        inputPanel.add(new JLabel("Diagnosis:"));
        diagnosisField = new JTextField();
        inputPanel.add(diagnosisField);

        inputPanel.add(new JLabel("Prescription:"));
        prescriptionField = new JTextField();
        inputPanel.add(prescriptionField);

        addButton = new JButton("Add Record");
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table for displaying records
        tableModel = new DefaultTableModel(new Object[] { "ID", "Date", "Diagnosis", "Prescription" }, 0);
        recordsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Medical History"));
        add(scrollPane, BorderLayout.CENTER);

        loadRecords();

        // Add button action
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

        // Update button action
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

        // Delete button action
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

        // Click row to edit
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