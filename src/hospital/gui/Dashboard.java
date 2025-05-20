package hospital.gui;

import dao.AppointmentDAO;
import dao.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Dashboard extends JFrame {

    private String role;
    private JPanel contentPanel; // Main area to switch panels

    public Dashboard(String role) {
        this.role = role;

        setTitle(role + " Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        initUI();
        setVisible(true);
    }

    private void initUI() {
        // LEFT SIDEBAR PANEL
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        JLabel welcomeLabel = new JLabel("Welcome, " + role + "!", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(128, 0, 0));
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons depending on role
        if (role.equalsIgnoreCase("Doctor")) {
            sidebar.add(createAppointmentButton("View Appointments"));
            sidebar.add(createNavigationButton("Manage Patients"));

            JButton medicalRecordsButton = createDashboardButton("Medical Records");
            sidebar.add(medicalRecordsButton);

            medicalRecordsButton.addActionListener(e -> {
                String patientIdStr = JOptionPane.showInputDialog(this, "Enter Patient ID:");
                if (patientIdStr != null && !patientIdStr.trim().isEmpty()) {
                    try {
                        int patientId = Integer.parseInt(patientIdStr.trim());
                        showPanel(new MedicalRecordsPanel(patientId));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid Patient ID.");
                    }
                }
            });
        } else if (role.equalsIgnoreCase("Admin")) {
            sidebar.add(createDashboardButton("Manage Users"));
            sidebar.add(createDashboardButton("System Reports"));
            sidebar.add(createAppointmentButton("View Appointments"));
            sidebar.add(createNavigationButton("Manage Patients"));
        } else if (role.equalsIgnoreCase("Patient")) {
            sidebar.add(createDashboardButton("Edit Profile"));
            sidebar.add(createDashboardButton("View Medical History"));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        logoutButton.setBackground(Color.LIGHT_GRAY);
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            dispose();
            new AuthScreen(role);
        });

        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(logoutButton);

        add(sidebar, BorderLayout.WEST);

        // MAIN CONTENT PANEL (swappable)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createDashboardButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
        button.setBackground(new Color(230, 230, 230));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private JButton createNavigationButton(String text) {
        JButton button = createDashboardButton(text);

        button.addActionListener(e -> {
            if (text.equals("Manage Patients")) {
                showPanel(new PatientManagementPanel());
            }
        });

        return button;
    }

    private JButton createAppointmentButton(String text) {
        JButton button = createDashboardButton(text);

        button.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();
                AppointmentDAO dao = new AppointmentDAO(conn);
                showPanel(new AppointmentManagementPanel(dao));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load Appointment Panel");
            }
        });

        return button;
    }

    // Replaces content in the main area
    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}