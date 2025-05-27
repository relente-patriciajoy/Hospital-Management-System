package hospital.gui;

import javax.swing.*;
import java.awt.*;

public class AuthScreen extends JFrame {
    private String role;

    public AuthScreen(String role) {
        this.role = role;

        setTitle(role + " Portal");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Pastel background
        getContentPane().setBackground(new Color(230, 240, 255));
        setLayout(new BorderLayout());

        // Header
        JLabel header = new JLabel(role + " Login / Register", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(new Color(139, 0, 0));
        header.setPreferredSize(new Dimension(1000, 70));
        add(header, BorderLayout.NORTH);

        // Card Panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cardPanel.setPreferredSize(new Dimension(700, 550));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 18));
        tabs.add("Login", createLoginPanel());
        tabs.add("Register", createRegisterPanel());

        cardPanel.add(tabs, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(cardPanel);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JTextField username = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField password = new JPasswordField(20);

        stylizeField(userLabel, username);
        stylizeField(passLabel, password);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(username, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(password, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginBtn = styledButton("Login");
        panel.add(loginBtn, gbc);

        gbc.gridy++;
        panel.add(backButton(), gbc);

        // Handle Login Button Click
        loginBtn.addActionListener(e -> {
            String user = username.getText().trim();
            String pass = new String(password.getPassword()).trim();

            // No Authentication for login check
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                new Dashboard(role);
            }
        });

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JTextField firstNameField = createTextField();
        JTextField middleNameField = createTextField();
        JTextField lastNameField = createTextField();
        JTextField userField = createTextField();
        JPasswordField passField = createPasswordField();

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel("First Name:"), gbc);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel("Middle Name:"), gbc);
        gbc.gridx = 1;
        panel.add(middleNameField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(userField, gbc);
        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(createLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passField, gbc);
        y++;

        JButton registerBtn = styledButton("Register");
        JButton backBtn = backButton();

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(registerBtn, gbc);
        gbc.gridx = 1;
        panel.add(backBtn, gbc);

        registerBtn.addActionListener(e -> {
            if (firstNameField.getText().isEmpty() || userField.getText().isEmpty()
                    || passField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(250, 35));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(250, 35));
        return field;
    }

    private void stylizeField(JLabel label, JTextField field) {
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(250, 35));
    }

    private JButton styledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(139, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(140, 40));
        return button;
    }

    private JButton backButton() {
        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.setBackground(Color.LIGHT_GRAY);
        back.setForeground(Color.BLACK);
        back.setFont(new Font("SansSerif", Font.PLAIN, 16));
        back.addActionListener(e -> {
            dispose();
            new RoleSelection();
        });
        return back;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthScreen("Admin"));
    }
}