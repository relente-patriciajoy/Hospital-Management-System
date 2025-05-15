package hospital.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoleSelection extends JFrame {

    public RoleSelection() {
        setTitle("Select Role");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Choose Your Role", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(new Color(139, 0, 0));
        add(title, BorderLayout.NORTH);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        rolePanel.setBackground(new Color(245, 245, 245));

        // Roles
        addRole(rolePanel, "Admin", "/assets/madmin.png");
        addRole(rolePanel, "Doctor", "/assets/mdoctor.png");
        addRole(rolePanel, "Staff", "/assets/mstaff.png");
        addRole(rolePanel, "Patient", "/assets/mpatient.png");

        add(rolePanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addRole(JPanel panel, String role, String imagePath) {
        ImageIcon icon = loadIcon(imagePath, 180, 240);
        JLabel roleLabel = new JLabel(role, icon, JLabel.CENTER);
        roleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        roleLabel.setHorizontalTextPosition(JLabel.CENTER);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        roleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        roleLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new AuthScreen(role); // Role passed to AuthScreen
            }
        });

        panel.add(roleLabel);
    }

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                Image img = new ImageIcon(imgURL).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } else {
                System.err.println("Couldn't find file: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
        }
        return new ImageIcon(); // Fallback
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoleSelection());
    }
}