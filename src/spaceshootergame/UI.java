package spaceshootergame;

import javax.swing.*;
import java.awt.*;

public class UI {

    private Ship ship;
    private Interactions interactions;
    private Fuel fuel;

    public UI(Ship ship, Interactions interactions, Fuel fuel) {
        this.ship = ship;
        this.interactions = interactions;
        this.fuel = fuel;
    }

    public void draw(Graphics g) {
        // Draw ship image in the top left corner
        ImageIcon shipIcon = new ImageIcon(getClass().getResource("/pictures/statek.png"));
        shipIcon.setImage(shipIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        int shipX = 0;
        int shipY = 0;
        g.drawImage(shipIcon.getImage(), shipX, shipY, null);

        // Draw the number of lives
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.YELLOW);
        
        Font font = new Font("Arial", Font.PLAIN, 22);
        g2d.setFont(font);
        g2d.drawString("X" + interactions.getLife(), 50, 35);

        // Draw fuel capacity
        int capacityX = 0;
        int capacityY = 50;
        int capacityWidth = 150;
        int capacityHeight = 20;

        // Draw the outline of the capacity rectangle
        g2d.setColor(Color.ORANGE);
        g2d.drawRect(capacityX, capacityY, capacityWidth, capacityHeight);

        // Calculate the fill percentage based on the fuel capacity
        double fillPercentage = (double) fuel.getCapacity() / 1000;

        // Fill the rectangle based on the fill percentage
        int fillWidth = (int) (capacityWidth * fillPercentage);
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(capacityX, capacityY, fillWidth, capacityHeight);

        // Zmiana wielkości czcionki i rodzaju czcionki
        
        font = new Font("Arial", Font.PLAIN, 18);
        g2d.setFont(font);

        g2d.setColor(Color.WHITE);
        g2d.drawString("Capacity: " + fuel.getCapacity(), capacityX + 10, capacityY + 15);
        g2d.drawString("Controls" , 675, capacityY + 15);
        g2d.drawString("wsad - Move " , 650, capacityY + 40);
        g2d.drawString("Space - Shoot " , 650, capacityY + 65);
        g2d.setColor(Color.GREEN);
        g2d.drawString("Score: " + interactions.getScore(), 325, 50);
        
        if (interactions.isEndOfTheGame()||fuel.getCapacity() == 0) {
            // Jeśli koniec gry, wyświetl animowany tekst
            drawGameOverText(g);}
        
        
    }
    private void drawGameOverText(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.RED);

        String gameOverText = "You died. Press enter to play again or esc to quit." + 
                "";

        // Pobierz wymiary tekstu
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(gameOverText);
        int textHeight = fontMetrics.getHeight();

        // Oblicz pozycję tekstu na środku ekranu
        int x = (SpaceMain.getScreenWidth() - textWidth) / 2;
        int y = (SpaceMain.getScreenHeight() - textHeight) / 2;

        // Narysuj tekst
        g2d.drawString(gameOverText, x, y);
    }
    
}
