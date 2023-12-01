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

    public SpaceShipComponent() {
        initializeUI();
        setupGame();
        Sounds sounds = new Sounds();
    }

    private void initializeUI() {
        background = new ImageIcon(getClass().getResource("/pictures/kosmos.jpg"));

        addKeyListener(this);
        setFocusable(true);

        Timer timer = new Timer(10, this);
        timer.start();
    }

    private void setupGame() {
        meteors = new ArrayList<>();
        numberOfMeteors = (int) (Math.random() * 2) + 4;

        for (int i = 0; i < numberOfMeteors; i++) {
            meteors.add(new Meteor(i * 75, -i * 100, meteorSpeed));
        }

        

        // Generate only one fuel object
        if (fuel == null || !fuel.isFuelGenerated()) {
            fuel = new Fuel(getWidth(), getHeight(), meteorSpeed);
        }
        interactions = new Interactions(spaceShip, meteors,fuel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);

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
            fuel.resetPosition(getWidth(), getHeight());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        moveShip();
        moveMeteors();
        moveFuel();

        if (interactions != null) {
            interactions.checkCollisions();
        }

        checkCollisions();

        // Create a copy of the meteors list to avoid ConcurrentModificationException
        List<Meteor> meteorsCopy = new ArrayList<>(meteors);
        for (Meteor meteor : meteorsCopy) {
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
            spaceShip.moveUp();
        } else if (key == KeyEvent.VK_S) {
            spaceShip.moveDown(getHeight());
        } else if (key == KeyEvent.VK_A) {
            spaceShip.moveLeft();
        } else if (key == KeyEvent.VK_D) {
            spaceShip.moveRight(getWidth());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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