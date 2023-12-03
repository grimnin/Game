/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceshootergame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Bullet {
    private int x, y;
    private int speed;
    private Shape bulletShape;
    
    


    public Bullet(int startX, int startY, int speed) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.bulletShape = new Ellipse2D.Double(x, y, 10, 10);
    }

   public void move() {
    y -= speed;
    bulletShape = new Ellipse2D.Double(x, y, 10, 30); // Aktualizacja pozycji kształtu
}

    public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.RED);
    g2d.fill(bulletShape);
}

    public int getY() {
        return y;
    }
}
