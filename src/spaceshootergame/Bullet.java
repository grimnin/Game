/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceshootergame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class Bullet {
    private int x, y;
    private int speed;
    private Shape bulletShape;
    private int width,height;
    private boolean collisionDetected;
    
    


    public Bullet(int startX, int startY, int speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.width=10;
        this.height=30;
        this.bulletShape = new Ellipse2D.Double(x, y, width, height);
        this.collisionDetected = false;
    }

   public void move(List<Meteor> meteors) {
    y -= speed;
    bulletShape = new Ellipse2D.Double(x, y, width, height);
    
    // Check if bullet is out of bounds to mark collision
    if (y < 0) {
        collisionDetected = true;
    }

    // Check for collision with meteors and mark collision if detected
    for (Meteor meteor : meteors) {
        Rectangle meteorBounds = meteor.getBounds();
        if (meteorBounds.intersects(getBounds())) {
            collisionDetected = true;
            break;  // No need to check further if collision is detected
        }
    }

    // Check if any meteor had a collision and mark collision if detected
    if (collisionDetected) {
        for (Meteor meteor : meteors) {
            Rectangle meteorBounds = meteor.getBounds();
            if (meteorBounds.intersects(getBounds())) {
                meteor.resetPosition();
                break;  // Reset only one meteor per bullet collision
            }
        }
    }
}




    public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.RED);
    g2d.fill(bulletShape);
}

    public int getY() {
        return y;
    }
    
    public Rectangle getBounds() {
        // Zwróć prostokąt ograniczający obszar pocisku
        return new Rectangle(x, y, width, height);
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
}
