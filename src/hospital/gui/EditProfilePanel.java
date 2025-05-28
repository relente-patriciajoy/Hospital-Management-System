package hospital.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditProfilePanel extends JPanel {

    private JTextField firstNameField, middleNameField, lastNameField, ageField, contactField;
    private JTextArea addressArea;
    private JComboBox<String> genderCombo;
    private RoundedImagePanel profilePicture;

    public EditProfilePanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Profile Picture Panel
        JPanel picturePanel = new JPanel();
        picturePanel.setBackground(Color.WHITE);
        picturePanel.setPreferredSize(new Dimension(200, 200));

        // Load and display circular image
        profilePicture = new RoundedImagePanel("/assets/profile.jpg", 150);
        profilePicture.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profilePicture.setToolTipText("Click to change profile picture");
        profilePicture.setBorder(null);

        // Borderless Profile
        picturePanel.setBorder(BorderFactory.createEmptyBorder());
        picturePanel.add(profilePicture);

        // Click listener to upload image
        profilePicture.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(EditProfilePanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedImage img = ImageIO.read(selectedFile);
                        profilePicture.setImage(img);  // Update the image
                        profilePicture.repaint();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(EditProfilePanel.this,
                                "Failed to load image.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        picturePanel.add(profilePicture);

        // Wrap for vertical centering of profile picture
        JPanel pictureWrapper = new JPanel(new GridBagLayout());
        pictureWrapper.setBackground(Color.WHITE);
        GridBagConstraints picConstraints = new GridBagConstraints();
        picConstraints.gridy = 0;
        picConstraints.weighty = 1;
        picConstraints.anchor = GridBagConstraints.CENTER;
        pictureWrapper.add(picturePanel, picConstraints);

        // Right form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx++;

        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Middle Name:"), gbc);
        gbc.gridx++;
        middleNameField = new JTextField(20);
        formPanel.add(middleNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx++;
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx++;
        ageField = new JTextField(5);
        formPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx++;
        genderCombo = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        formPanel.add(genderCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx++;
        contactField = new JTextField(15);
        formPanel.add(contactField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx++;
        addressArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(addressArea);
        formPanel.add(scrollPane, gbc);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(new Color(128, 0, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Final layout
        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(pictureWrapper, BorderLayout.WEST);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
    }
}