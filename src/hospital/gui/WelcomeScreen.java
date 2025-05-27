package hospital.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    private static final long serialVersionUID = 1L;

    public WelcomeScreen() {
        setTitle("MedAssistant");
        setSize(1000, 700);
        setUndecorated(false);
        setExtendedState(JFrame.NORMAL);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image using classpath
        Image bannerImage = null;
        try {
            bannerImage = new ImageIcon(getClass().getResource("/assets/logo.png")).getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Banner image not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JPanel backgroundPanel = new BackgroundPanel(bannerImage);
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        // Timer to switch screens
        Timer timer = new Timer(2500, e -> {
            dispose();
            new RoleSelection();
        });
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }

    // Inner class to draw the background image
    static class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}