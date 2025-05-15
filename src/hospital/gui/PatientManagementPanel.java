package hospital.gui;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

// import hospital.gui.MedicalRecordsPanel;  // Adjust if your package differs
// import hospital.gui.Dashboard;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PatientManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, ageField, genderField, contactField;

    public PatientManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Table setup
        String[] columns = { "ID", "Name", "Age", "Gender", "Contact" };
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        refreshTable();

        // Double-click to open MedicalRecordsPanel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // double-click event
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int patientId = (int) tableModel.getValueAt(selectedRow, 0); // ID in column 0

                        // Get top JFrame (Dashboard)
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PatientManagementPanel.this);
                        if (topFrame instanceof Dashboard) {
                            ((Dashboard) topFrame).showPanel(new MedicalRecordsPanel(patientId));
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        ageField = new JTextField();
        genderField = new JTextField();
        contactField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(contactField);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        addBtn.addActionListener(e -> {
            Patient patient = new Patient();
            patient.setName(nameField.getText());
            patient.setAge(Integer.parseInt(ageField.getText()));
            patient.setGender(genderField.getText());
            patient.setContact(contactField.getText());
            if (PatientDAO.addPatient(patient)) {
                refreshTable();
                clearFields();
            }
        });

        updateBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                int id = (int) tableModel.getValueAt(selected, 0);
                Patient patient = new Patient();
                patient.setId(id);
                patient.setName(nameField.getText());
                patient.setAge(Integer.parseInt(ageField.getText()));
                patient.setGender(genderField.getText());
                patient.setContact(contactField.getText());
                if (PatientDAO.updatePatient(patient)) {
                    refreshTable();
                    clearFields();
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                int id = (int) tableModel.getValueAt(selected, 0);
                if (PatientDAO.deletePatient(id)) {
                    refreshTable();
                    clearFields();
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected != -1) {
                nameField.setText(tableModel.getValueAt(selected, 1).toString());
                ageField.setText(tableModel.getValueAt(selected, 2).toString());
                genderField.setText(tableModel.getValueAt(selected, 3).toString());
                contactField.setText(tableModel.getValueAt(selected, 4).toString());
            }
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Patient> patients = PatientDAO.getAllPatients();
        for (Patient p : patients) {
            Object[] row = { p.getId(), p.getName(), p.getAge(), p.getGender(), p.getContact() };
            tableModel.addRow(row);
        }
    }

    private void clearFields() {
        nameField.setText("");
        ageField.setText("");
        genderField.setText("");
        contactField.setText("");
    }
}