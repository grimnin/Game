package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpaceShipComponent extends JComponent implements ActionListener, KeyListener {

    private Ship spaceShip;
    private ImageIcon background;
    private List<Meteor> meteors;
    private final int meteorSpeed = 5;
    private int numberOfMeteors;
    private Interactions interactions; // Declare interactions field here
    private boolean isGameOver = false;

    public SpaceShipComponent() {
        initializeUI();
        setupGame();
        Sounds sounds = new Sounds();
    }

    private void initializeUI() {
        // Set the background image
        background = new ImageIcon(getClass().getResource("/pictures/kosmos.jpg"));

        // Add key listener
        addKeyListener(this);
        setFocusable(true);

        // Set up the timer for game refresh
        Timer timer = new Timer(10, this);
        timer.start();
    }

    private void setupGame() {
        // Initialize the list of meteors
        meteors = new ArrayList<>();

        // Random number of meteors from 3 to 5
        numberOfMeteors = (int) (Math.random() * 2) + 4;

        // Create meteor objects and add them to the list
        for (int i = 0; i < numberOfMeteors; i++) {
            meteors.add(new Meteor(i * 75, -i * 100, meteorSpeed));
        }

        // Initialize interactions here
        interactions = new Interactions(spaceShip, meteors);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

        // Initialize the spaceShip where the size of the component is known
        if (spaceShip == null) {
            spaceShip = new Ship(getWidth(), getHeight());
            // Set spaceShip in Interactions
            if (interactions != null) {
                interactions.setSpaceShip(spaceShip);
            }
        }

        // Draw the spaceship
        spaceShip.drawShip().paintIcon(this, g, spaceShip.getShipX(), spaceShip.getShipY());

        // Draw meteors
        for (Meteor meteor : meteors) {
            meteor.draw(g);
        }
    }

    private void moveMeteors() {
        for (Meteor meteor : meteors) {
            meteor.move();
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < meteors.size(); i++) {
            for (int j = 0; j < meteors.size(); j++) {
                if (i != j && meteors.get(i).getBounds().intersects(meteors.get(j).getBounds())) {
                    // Collision between meteors! Reset positions
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

    @Override
    public void actionPerformed(ActionEvent e) {
        moveShip();
        moveMeteors();

        // Check for collisions only if interactions is not null
        if (interactions != null) {
            interactions.checkCollisions();
        }

        checkCollisions();

        // Add new meteors if necessary
        for (Meteor meteor : meteors) {
            if (meteor.getBounds().getMaxY() >= getHeight()) {
                meteor.resetPosition();
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            // Move the spaceship up
            spaceShip.moveUp();
        } else if (key == KeyEvent.VK_S) {
            // Move the spaceship down
            spaceShip.moveDown(getHeight());
        } else if (key == KeyEvent.VK_A) {
            // Move the spaceship left
            spaceShip.moveLeft();
        } else if (key == KeyEvent.VK_D) {
            // Move the spaceship right
            spaceShip.moveRight(getWidth());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key release if needed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Game");
        SpaceShipComponent spaceShipComponent = new SpaceShipComponent();
        frame.add(spaceShipComponent);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
