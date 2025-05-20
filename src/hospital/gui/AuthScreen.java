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
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel(role + " Login / Register", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 42));
        header.setForeground(new Color(128, 0, 0));
        add(header, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.PLAIN, 22));
        tabs.add("Login", createLoginPanel());
        tabs.add("Register", createRegisterPanel());

        add(tabs, BorderLayout.CENTER);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        JTextField username = new JTextField(20);
        username.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        JPasswordField password = new JPasswordField(20);
        password.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JButton loginBtn = styledButton("Login");
        JButton backBtn = backButton();

        loginBtn.addActionListener(e -> {
            // (OPTIOMN) Real login validation
            String user = username.getText();
            String pass = new String(password.getPassword());

            if (!user.isEmpty() && !pass.isEmpty()) {
                // Assume successful login
                dispose();
                new Dashboard(role);
            } else {
                JOptionPane.showMessageDialog(this, "Enter credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(userLabel, gbc);
        gbc.gridy++;
        panel.add(username, gbc);
        gbc.gridy++;
        panel.add(passLabel, gbc);
        gbc.gridy++;
        panel.add(password, gbc);
        gbc.gridy++;
        panel.add(loginBtn, gbc);
        gbc.gridy++;
        panel.add(backBtn, gbc);

        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JButton registerBtn = styledButton("Register");
        JButton backBtn = backButton();

        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if (!name.isEmpty() && !user.isEmpty() && !pass.isEmpty()) {
                // Simulate successful registration
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(nameLabel, gbc);
        gbc.gridy++;
        panel.add(nameField, gbc);
        gbc.gridy++;
        panel.add(userLabel, gbc);
        gbc.gridy++;
        panel.add(userField, gbc);
        gbc.gridy++;
        panel.add(passLabel, gbc);
        gbc.gridy++;
        panel.add(passField, gbc);
        gbc.gridy++;
        panel.add(registerBtn, gbc);
        gbc.gridy++;
        panel.add(backBtn, gbc);

        return panel;
    }

    private JButton styledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(128, 0, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 50));
        return button;
    }

    private JButton backButton() {
        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(180, 50));
        back.setBackground(Color.LIGHT_GRAY);
        back.setForeground(Color.BLACK);
        back.setFont(new Font("SansSerif", Font.PLAIN, 20));
        back.addActionListener(e -> {
            dispose();
            new RoleSelection(); // Role selection screen
        });
        return back;
    }
}