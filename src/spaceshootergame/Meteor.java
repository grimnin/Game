package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Meteor {
    private ImageIcon meteorIcon;
    private int x, y;
    private int speed;
    private boolean collisionDetected;  // New field to track collision detection
    private int width,height;
    private final int screenWidth=784;
    
    

    public Meteor(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.collisionDetected = false;

        // Load the image explicitly
        Image meteorImage = new ImageIcon(getClass().getResource("/pictures/meteor.png")).getImage();

        // Set the scaled instance of the loaded image
        width=125;
        height=125;
        this.meteorIcon = new ImageIcon(meteorImage.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }

    public void draw(Graphics g) {
        meteorIcon.paintIcon(null, g, x, y);
    }

    public void move() {
        y += speed;
            // Dodaj finalnie zmienna getHeight
        if (y > SpaceMain.getScreenHeight()) {
            resetPosition();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, meteorIcon.getIconWidth(), meteorIcon.getIconHeight());
    }
    
    public float getRadius(){
    return meteorIcon.getIconWidth()/2;
    }
    public float getMiddleX(){
    return x+width/2;
    }
    
    public float getMiddleY(){
    return y+height/2;
    }
    
    public void resetPosition() {
    Random random = new Random();
    x = random.nextInt(screenWidth - meteorIcon.getIconWidth());
    y = -random.nextInt(SpaceMain.getScreenHeight()) - 2*meteorIcon.getIconHeight();
    collisionDetected = false;

    // Remove the following line to avoid modifying the list during iteration
    // meteors.remove(this);
}



    public boolean isCollisionDetected() {
        return collisionDetected;
    }

    public void setCollisionDetected(boolean collisionDetected) {
        this.collisionDetected = collisionDetected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    
    
}