package hospital.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("MedAssistant");
        setSize(1000, 700); // Not full screen
        setUndecorated(false);  // Allows minimize, maximize, close buttons
        setExtendedState(JFrame.NORMAL); // Full screen
        setResizable(true); // Allow resizing
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        JLabel title = new JLabel("Welcome to MedAssistant", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 60));
        title.setForeground(new Color(128, 0, 0));

        add(title);

        Timer timer = new Timer(2500, e -> {
            dispose();
            new RoleSelection();
        });
        timer.setRepeats(false);
        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}