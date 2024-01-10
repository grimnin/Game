package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class SpaceShipComponent extends JComponent implements ActionListener, KeyListener {

    private Ship spaceShip;
    
    private ImageIcon background;
    private List<Meteor> meteors;
    private final int meteorSpeed = 5;
    private int numberOfMeteors;
    private Interactions interactions;
    private Fuel fuel;
    private boolean isGameOver = false;
    private List<Bullet> bullets;
    private long lastShotTime;
    private final int shotDelay = 500; // Odstęp czasowy w milisekundach (np. 500 ms)
    private Timer timer;
    private int backgroundY = 0;
    private int backgroundSpeed = 3;  // Adjust the speed as needed
    private Meteor meteor;
    private UI ui;
    private Sounds sounds;
    
    
    
    
    public SpaceShipComponent() {
    initializeUI();
    setupGame();
     sounds = new Sounds();
    this.bullets = new ArrayList<>(); // Initialize class field bullets here
}



    private void initializeUI() {
        background = new ImageIcon(getClass().getResource("/pictures/kosmos.jpg"));

        addKeyListener(this);
        setFocusable(true);

         timer = new Timer(10, this);
        timer.start();
        
    }

    private void setupGame() {
        meteors = new ArrayList<>();
        numberOfMeteors = (int) (Math.random() * 2) + 4;

        for (int i = 0; i < numberOfMeteors; i++) {
            meteors.add(meteor=new Meteor(i * 75, -i * 100, meteorSpeed));
        }

        // Generate only one fuel object
        if (fuel == null || !fuel.isFuelGenerated()) {
            fuel = new Fuel(getWidth(), getHeight(), meteorSpeed);
        }
        interactions = new Interactions(spaceShip, meteors, fuel,bullets);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Move the background based on the backgroundSpeed
        backgroundY += backgroundSpeed;
        if (backgroundY >= getHeight()) {
            backgroundY = 0;
        }

        // Draw the background
        g.drawImage(background.getImage(), 0, backgroundY, getWidth(), getHeight() , this);
        g.drawImage(background.getImage(), 0,  backgroundY-getHeight() , getWidth(), getHeight() , this);

        if (spaceShip == null) {
            spaceShip = new Ship(getWidth(), getHeight());
            if (interactions != null) {
                interactions.setSpaceShip(spaceShip);
                
            }
        }

        spaceShip.drawShip().paintIcon(this, g, spaceShip.getShipX(), spaceShip.getShipY());

        for (Meteor meteor : meteors) {
            meteor.draw(g);
        }

        // Draw fuel
        if (fuel != null) {
            fuel.draw(g);
        }
        for (Bullet bullet : bullets) {
        bullet.draw(g);
    }
        ui=new UI(spaceShip,interactions,fuel);
        ui.draw(g);
    }

    private void moveMeteors() {
        for (Meteor meteor : meteors) {
            meteor.move();
        }
    }

    private void moveFuel() {
        if (fuel != null) {
            fuel.move(getWidth(), getHeight());

            // Check for collisions between fuel and meteors
            for (Meteor meteor : meteors) {
                Rectangle meteorBounds = meteor.getBounds();
                if (fuel.getBounds().intersects(meteorBounds)) {
                    resetFuelPositionIfCollision(meteor);
                }
            }
        }
    }

    private void resetFuelPositionIfCollision(Meteor meteor) {
        Rectangle meteorBounds = meteor.getBounds();
        while (fuel.getBounds().intersects(meteorBounds)) {
            fuel.resetPosition();
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < meteors.size(); i++) {
            for (int j = 0; j < meteors.size(); j++) {
                if (i != j && meteors.get(i).getBounds().intersects(meteors.get(j).getBounds())) {
                    meteors.get(i).resetPosition();
                    meteors.get(j).resetPosition();
                    
                }
            }
        }
    }

    private void moveShip() {
        if (spaceShip == null) {
            return;
        }

        spaceShip.move(getWidth(), getHeight());
        repaint();
    }
    
    private void createBullet() {
    if (spaceShip != null && System.currentTimeMillis() - lastShotTime > shotDelay) {
        int bulletX = spaceShip.getShipX() + spaceShip.getShipWidth() / 2;
        int bulletY = spaceShip.getShipY();
        Bullet bullet = new Bullet(bulletX, bulletY, 10); // Dostosuj prędkość według potrzeb
        bullets.add(bullet);
        sounds.playBlasterSound();
        lastShotTime = System.currentTimeMillis(); // Aktualizuj czas ostatniego strzału
    }
}

    

    @Override
public void actionPerformed(ActionEvent e) {
    moveShip();
    moveMeteors();
    moveFuel();

    // Check if bullets is null and initialize it if necessary
    if (bullets == null) {
        bullets = new ArrayList<>();
    }

    // Iterate over bullets only if it is not null
    if (bullets != null) {
    List<Bullet> bulletsToRemove = new ArrayList<>();
    for (Bullet bullet : bullets) {
        bullet.move(meteors);  // Pass the list of meteors to the move method
        if (bullet.isCollisionDetected()) {
            bulletsToRemove.add(bullet);
        }
    }
    bullets.removeAll(bulletsToRemove);  // Remove bullets with collision detected
}

    if (interactions != null) {
        interactions.checkCollisions();
        interactions.addScore();
        endGame();
    }

    checkCollisions();

    repaint();
}

public void endGame(){
if(interactions.isEndOfTheGame()||fuel.getCapacity()==0){
    
    timer.stop();
    }
}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            spaceShip.moveUp();
        }  if (key == KeyEvent.VK_S) {
            spaceShip.moveDown(getHeight());
        }  if (key == KeyEvent.VK_A) {
            spaceShip.moveLeft();
        }  if (key == KeyEvent.VK_D) {
            spaceShip.moveRight(getWidth());
        }
        
        if (key == KeyEvent.VK_SPACE) {
        createBullet();
        
        
    }
      if (key == KeyEvent.VK_ENTER&&!timer.isRunning()) {
         
          fuel.resetPosition();
          fuel.renewCapacity();
          for(Bullet bullet:bullets){
          bullet.setCollisionDetected(true);
          }
          spaceShip.resetCoordinates(getWidth(), getHeight());
          for(Meteor meteor:meteors){
          meteor.resetPosition();
          
          }
          
          interactions.ResetGame();
        timer.restart();
    }  
    } 

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Game");
        SpaceShipComponent spaceShipComponent = new SpaceShipComponent();
        frame.add(spaceShipComponent);
        frame.setSize(SpaceMain.getScreenWidth(), SpaceMain.getScreenHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
