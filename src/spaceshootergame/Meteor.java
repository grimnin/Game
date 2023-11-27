package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Meteor {
    private ImageIcon meteorIcon;
    private int x, y;
    private int speed;

    public Meteor(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

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
    x = random.nextInt(800 - 125); // Adjust as needed
    y = -random.nextInt(500) - meteorIcon.getIconHeight(); // Adjust as needed
}

}
