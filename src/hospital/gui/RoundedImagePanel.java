package hospital.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RoundedImagePanel extends JPanel {
    private BufferedImage image;
    private int diameter;

    public RoundedImagePanel(String imagePath, int diameter) {
        this.diameter = diameter;
        try {
            image = ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Could not load image: " + imagePath);
        }
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false); // ensures background is fully transparent
    }

    public RoundedImagePanel(BufferedImage image, int diameter) {
        this.image = image;
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
        setOpaque(false);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null)
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Clip to circle
        Shape circle = new Ellipse2D.Float(0, 0, diameter, diameter);
        g2.setClip(circle);

        // Draw image inside the circle
        g2.drawImage(image, 0, 0, diameter, diameter, this);

        g2.dispose();
    }
}