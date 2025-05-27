package hospital.gui;

import dao.AppointmentDAO;
import dao.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class Dashboard extends JFrame {

    private String role;
    private JPanel contentPanel;

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
        // TOP NAVIGATION BAR
        JPanel topNav = new JPanel();
        topNav.setBackground(new Color(128, 0, 0)); // Maroon
        topNav.setPreferredSize(new Dimension(getWidth(), 50));
        topNav.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JLabel titleLabel = new JLabel(role + " Dashboard");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        topNav.add(titleLabel);

        add(topNav, BorderLayout.NORTH);

        // LEFT SIDEBAR PANEL
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(255, 248, 230)); // Warm light beige
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        JLabel welcomeLabel = new JLabel("Welcome, " + role + "!", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(100, 0, 0));
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(welcomeLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        if (role.equalsIgnoreCase("Doctor")) {
            sidebar.add(createAppointmentButton("View Appointments"));
            sidebar.add(createNavigationButton("Manage Patients"));

            JButton medicalRecordsButton = createSidebarButton("Medical Records");
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
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
            sidebar.add(createSidebarButton("Manage Users"));
            sidebar.add(createSidebarButton("System Reports"));
            sidebar.add(createAppointmentButton("View Appointments"));
            sidebar.add(createNavigationButton("Manage Patients"));
        } else if (role.equalsIgnoreCase("Patient")) {
            sidebar.add(createSidebarButton("Edit Profile"));
            sidebar.add(createSidebarButton("View Medical History"));
        }

        sidebar.add(Box.createVerticalGlue());

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        logoutButton.setBackground(new Color(192, 57, 43)); // Deep red
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.addActionListener(e -> {
            dispose();
            new AuthScreen(role);
        });

        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(logoutButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        add(sidebar, BorderLayout.WEST);

        // MAIN CONTENT PANEL
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setBackground(new Color(240, 240, 240));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Optional hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });

        sidebarSpacing(button);
        return button;
    }

    private void sidebarSpacing(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createRigidArea(new Dimension(0, 10)));
        wrapper.add(button);
    }

    private JButton createNavigationButton(String text) {
        JButton button = createSidebarButton(text);

        button.addActionListener(e -> {
            if (text.equals("Manage Patients")) {
                showPanel(new PatientManagementPanel());
            }
        });

        return button;
    }

    private JButton createAppointmentButton(String text) {
        JButton button = createSidebarButton(text);

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

    public void showPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}