package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Fuel {
    private ImageIcon fuelIcon;
    private int x, y;
    private int speed;
    private double rotationAngle;
    private boolean isFuelGenerated;

    public Fuel(int screenWidth, int screenHeight, int speed) {
        this.speed = speed;

        // Load the image explicitly
        Image fuelImage = new ImageIcon(getClass().getResource("/pictures/fuel.png")).getImage();

        // Set the scaled instance of the loaded image
        int fuelIconWidth = 50;  // Set a positive value for the width
        int fuelIconHeight = 50; // Set a positive value for the height

        this.fuelIcon = new ImageIcon(fuelImage.getScaledInstance(fuelIconWidth, fuelIconHeight, Image.SCALE_DEFAULT));

        Random random = new Random();
        x = random.nextInt(screenWidth - fuelIconWidth,1) + 40; // Ensure screenWidth > fuelIconWidth
        y = -random.nextInt(screenHeight - fuelIconHeight,1) - fuelIconHeight; // Ensure a valid initial position
        rotationAngle = 0;  // Initial rotation angle
        isFuelGenerated = true;  // Initial setting to true
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Save the current transformation to avoid affecting other drawings
        AffineTransform originalTransform = g2d.getTransform();

        // Apply rotation
        g2d.rotate(Math.toRadians(rotationAngle), x + fuelIcon.getIconWidth() / 2, y + fuelIcon.getIconHeight() / 2);
        fuelIcon.paintIcon(null, g, x, y);

        // Restore the original transformation
        g2d.setTransform(originalTransform);
    }

    public void move() {
        y += speed;
        rotationAngle += 5;  // Increment the rotation angle

        if (y > 600) {
            resetPosition();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, fuelIcon.getIconWidth(), fuelIcon.getIconHeight());
    }

    public void resetPosition() {
        Random random = new Random();
        x = random.nextInt(800 - fuelIcon.getIconWidth()) + 40; // Adjust as needed
        y = -random.nextInt(600) - fuelIcon.getIconHeight();   // Adjust as needed
        rotationAngle = 0;  // Reset rotation angle
        isFuelGenerated = false;  // Allow fuel to be generated again
    }

    public boolean isFuelGenerated() {
        return isFuelGenerated;
    }

    public void setFuelGenerated(boolean fuelGenerated) {
        isFuelGenerated = fuelGenerated;
    }
}
