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
    private JButton addButton;
    private int patientId;

    public MedicalRecordsPanel(int patientId) {
        this.patientId = patientId;
        setLayout(new BorderLayout());

        // Top form for input
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Medical Record"));

        inputPanel.add(new JLabel("Diagnosis:"));
        diagnosisField = new JTextField();
        inputPanel.add(diagnosisField);

        inputPanel.add(new JLabel("Prescription:"));
        prescriptionField = new JTextField();
        inputPanel.add(prescriptionField);

        addButton = new JButton("Add Record");
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table for viewing records
        tableModel = new DefaultTableModel(new Object[]{"Date", "Diagnosis", "Prescription"}, 0);
        recordsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Medical History"));
        add(scrollPane, BorderLayout.CENTER);

        // Load records
        loadRecords();

        // Button Action
        addButton.addActionListener((ActionEvent e) -> {
            String diagnosis = diagnosisField.getText();
            String prescription = prescriptionField.getText();
            if (!diagnosis.isEmpty() && !prescription.isEmpty()) {
                MedicalRecords record = new MedicalRecords(patientId, diagnosis, prescription, LocalDate.now());
                if (MedicalRecordsDAO.addMedicalRecord(record)) {
                    JOptionPane.showMessageDialog(this, "Record added successfully.");
                    diagnosisField.setText("");
                    prescriptionField.setText("");
                    loadRecords();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add record.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in both diagnosis and prescription.");
            }
        });
    }

    private void loadRecords() {
        tableModel.setRowCount(0);
        List<MedicalRecords> records = MedicalRecordsDAO.getRecordsByPatientId(patientId);
        for (MedicalRecords record : records) {
            tableModel.addRow(new Object[]{record.getRecordDate(), record.getDiagnosis(), record.getPrescription()});
        }
    }
}