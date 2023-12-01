package spaceshootergame;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Ship {
    private ImageIcon ship;
    private int shipX, shipY;
    private int shipWidth, shipHeight;

    public Ship(int screenWidth, int screenHeight) {
        shipWidth = 100;
        shipHeight = 100;
        ship = new ImageIcon(getClass().getResource("/pictures/statek.png"));
        ship.setImage(ship.getImage().getScaledInstance(shipWidth, shipHeight, Image.SCALE_DEFAULT));
        shipX = screenWidth / 2 - shipWidth / 2;
        shipY = screenHeight - shipHeight;
    }

    public ImageIcon drawShip() {
        return ship;
    }

    public void move(int screenWidth, int screenHeight) {
        // Kod z moveShip(), dostosowany do nowych zmiennych
        if (shipX < 0) {
            shipX = 0;
        } else if (shipX > screenWidth - shipWidth) {
            shipX = screenWidth - shipWidth;
        }

        if (shipY < 0) {
            shipY = 0;
        } else if (shipY > screenHeight - shipHeight) {
            shipY = screenHeight - shipHeight;
        }
    }

    public void moveUp() {
        shipY -= 40;
    }

    public void moveDown(int screenHeight) {
        shipY += 40;
        if (shipY > screenHeight - shipHeight) {
            shipY = screenHeight - shipHeight;
        }
    }

    public void moveLeft() {
        shipX -= 40;
    }

    public void moveRight(int screenWidth) {
        shipX += 40;
        if (shipX > screenWidth - shipWidth) {
            shipX = screenWidth - shipWidth;
        }
    }

    public int getShipX() {
        return shipX;
    }

    public int getShipY() {
        return shipY;
    }

    public ImageIcon getShip() {
        return ship;
    }

    public void setShip(ImageIcon ship) {
        this.ship = ship;
    }

    public int getShipWidth() {
        return shipWidth;
    }

    public void setShipWidth(int shipWidth) {
        this.shipWidth = shipWidth;
    }

    public int getShipHeight() {
        return shipHeight;
    }

    public void setShipHeight(int shipHeight) {
        this.shipHeight = shipHeight;
    }
    
}