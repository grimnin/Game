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
    private int capacity;
    private int can;
    private boolean collisionDetected;

    public Fuel(int screenWidth, int screenHeight, int speed) {
        this.speed = speed;

        // Load the image explicitly
        Image fuelImage = new ImageIcon(getClass().getResource("/pictures/fuel.png")).getImage();

        // Set the scaled instance of the loaded image
        int fuelIconWidth = 50;  // Set a positive value for the width
        int fuelIconHeight = 50; // Set a positive value for the height

        this.fuelIcon = new ImageIcon(fuelImage.getScaledInstance(fuelIconWidth, fuelIconHeight, Image.SCALE_DEFAULT));

        Random random = new Random();
        x = random.nextInt(screenWidth - 2*fuelIconWidth,fuelIconWidth) ; // Ensure screenWidth > fuelIconWidth
        y = -random.nextInt(screenHeight - fuelIconHeight,1) - fuelIconHeight; // Ensure a valid initial position
        rotationAngle = 0;  // Initial rotation angle
        isFuelGenerated = true;  // Initial setting to true
        capacity=1000;
        can=250;
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

    public void decreaseCapacity() {
        // Adjust the amount to decrease the capacity by in each iteration
        int decreaseAmount = 1; // You can change this value as needed
        if (capacity > 0) {
            capacity -= decreaseAmount;
        }
    }
    
    public void move(int width,int heigth) {
        y += speed;
        rotationAngle += 5;  // Increment the rotation angle

        if (y >600) {
            resetPosition(width,heigth);
            System.out.println("Pojemność: "+capacity);
        }

        decreaseCapacity(); // Call the method to decrease the capacity
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, fuelIcon.getIconWidth(), fuelIcon.getIconHeight());
    }

    public void resetPosition(int width,int heigth) {
    Random random = new Random();
        
    x = random.nextInt(width - 2*fuelIcon.getIconWidth())+fuelIcon.getIconWidth() ; // Adjust as needed
    y = -random.nextInt(heigth) - fuelIcon.getIconHeight();
    rotationAngle = 0;  // Reset rotation angle
    isFuelGenerated = true;  // Set isFuelGenerated to true
    collisionDetected = false;  // Reset collision detection
}


    public boolean isFuelGenerated() {
        return isFuelGenerated;
    }

    public void setFuelGenerated(boolean fuelGenerated) {
        isFuelGenerated = fuelGenerated;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public void addfuel(){capacity+=can;}
    
    
    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }
    
}