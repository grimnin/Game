package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Meteor {
    private ImageIcon meteorIcon;
    private int x, y;
    private int speed;
    private boolean collisionDetected;  // New field to track collision detection

    public Meteor(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.collisionDetected = false;

        // Load the image explicitly
        Image meteorImage = new ImageIcon(getClass().getResource("/pictures/meteor.png")).getImage();

        // Set the scaled instance of the loaded image
        this.meteorIcon = new ImageIcon(meteorImage.getScaledInstance(125, 125, Image.SCALE_DEFAULT));
    }

    public void draw(Graphics g) {
        meteorIcon.paintIcon(null, g, x, y);
    }

    public void move() {
        y += speed;

        if (y > 600) {
            resetPosition();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, meteorIcon.getIconWidth(), meteorIcon.getIconHeight());
    }

    public void resetPosition() {
        Random random = new Random();
        x = random.nextInt(800 - meteorIcon.getIconWidth()); // Adjust as needed
        y = -random.nextInt(500) - meteorIcon.getIconHeight(); // Adjust as needed
        collisionDetected = false;  // Reset collision detection for the next round
    }

    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }
}
