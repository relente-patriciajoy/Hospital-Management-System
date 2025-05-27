package hospital.gui;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

// import hospital.gui.MedicalRecordsPanel;
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

        // Style table
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(128, 0, 0));
        table.setSelectionForeground(Color.WHITE);
        refreshTable();

        // Double-click to open MedicalRecordsPanel
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int patientId = (int) tableModel.getValueAt(selectedRow, 0);

                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(PatientManagementPanel.this);
                        if (topFrame instanceof Dashboard) {
                            ((Dashboard) topFrame).showPanel(new MedicalRecordsPanel(patientId));
                        }
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        add(scrollPane, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(128, 0, 0)),
                "Patient Details"));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields size
        nameField = new JTextField(20);
        ageField = new JTextField(10);
        genderField = new JTextField(10);
        contactField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 3;
        formPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        formPanel.add(genderField, gbc);
        gbc.gridx = 2;
        formPanel.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 3;
        formPanel.add(contactField, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton addBtn = new JButton("Add");
        addBtn.setBackground(new Color(0, 128, 0));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addBtn.setPreferredSize(new Dimension(120, 35));

        JButton updateBtn = new JButton("Update");
        updateBtn.setBackground(new Color(0, 102, 204));
        updateBtn.setForeground(Color.WHITE);
        updateBtn.setFocusPainted(false);
        updateBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        updateBtn.setPreferredSize(new Dimension(120, 35));

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBackground(new Color(204, 0, 0));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        deleteBtn.setPreferredSize(new Dimension(120, 35));

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
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