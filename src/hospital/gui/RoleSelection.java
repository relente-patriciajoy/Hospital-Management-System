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

        // Main layered panel
        setContentPane(new BackgroundPanel("/assets/maroon-bg.png"));
        getContentPane().setLayout(new BorderLayout());

        // Navigation Bar Panel
        JPanel navbar = new JPanel();
        navbar.setBackground(new Color(139, 0, 0));
        navbar.setPreferredSize(new Dimension(getWidth(), 60));
        navbar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));

        JLabel appTitle = new JLabel("MedAssistant");
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        appTitle.setForeground(Color.WHITE);
        navbar.add(appTitle);
        getContentPane().add(navbar, BorderLayout.NORTH);

        // Title
        JLabel title = new JLabel("Choose Your Role", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 42));
        title.setForeground(Color.WHITE);
        getContentPane().add(title, BorderLayout.CENTER);

        // Role Panel
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 50));
        rolePanel.setOpaque(false);

        addRole(rolePanel, "Admin", "/assets/madmin.png");
        addRole(rolePanel, "Doctor", "/assets/mdoctor.png");
        addRole(rolePanel, "Staff", "/assets/mstaff.png");
        addRole(rolePanel, "Patient", "/assets/mpatient.png");

        getContentPane().add(rolePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addRole(JPanel panel, String role, String imagePath) {
        ImageIcon icon = loadIcon(imagePath, 180, 240);
        JLabel roleLabel = new JLabel(role, icon, JLabel.CENTER);
        roleLabel.setVerticalTextPosition(JLabel.BOTTOM);
        roleLabel.setHorizontalTextPosition(JLabel.CENTER);
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        roleLabel.setForeground(Color.WHITE);
        roleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        roleLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new AuthScreen(role);
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
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + path);
        }
        return new ImageIcon();
    }

    // Custom JPanel for background image
    class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel(String imagePath) {
            try {
                java.net.URL url = getClass().getResource(imagePath);
                if (url != null) {
                    background = new ImageIcon(url).getImage();
                }
            } catch (Exception e) {
                System.err.println("Background image load failed: " + imagePath);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoleSelection());
    }
}