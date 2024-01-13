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
    private final int shotDelay = 500;
    private Timer timer;
    private int backgroundY = 0;
    private int backgroundSpeed = 3;
    private Meteor meteor;
    private UI ui;
    private Sounds sounds;
    private ExplosionAnimation explosionAnimation;
    private boolean gameResetting = false;
    private boolean restartingAnimation = false;

    public SpaceShipComponent() {
        initializeUI();
        sounds = new Sounds();
        this.bullets = new ArrayList<>();
        this.explosionAnimation = new ExplosionAnimation();
        add(explosionAnimation);
        explosionAnimation.setVisible(false);
        setupGame();
        this.ui = new UI(spaceShip, interactions, fuel);
        timer = new Timer(10, this);
        timer.start();
    }

    private void initializeUI() {
        background = new ImageIcon(getClass().getResource("/pictures/kosmos.jpg"));
        addKeyListener(this);
        setFocusable(true);
    }

    private void setupGame() {
        meteors = new ArrayList<>();
        numberOfMeteors = (int) (Math.random() * 2) + 4;

        for (int i = 0; i < numberOfMeteors; i++) {
            meteors.add(meteor = new Meteor(i * 75, -i * 100, meteorSpeed));
        }

        if (fuel == null || !fuel.isFuelGenerated()) {
            fuel = new Fuel(getWidth(), getHeight(), meteorSpeed);
        }
        interactions = new Interactions(spaceShip, meteors, fuel, bullets, explosionAnimation);
    }

    private void resetExplosionAnimation() {
        explosionAnimation.stopAnimation();
        explosionAnimation.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        backgroundY += backgroundSpeed;
        if (backgroundY >= getHeight()) {
            backgroundY = 0;
        }

        g.drawImage(background.getImage(), 0, backgroundY, getWidth(), getHeight(), this);
        g.drawImage(background.getImage(), 0, backgroundY - getHeight(), getWidth(), getHeight(), this);

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

        if (fuel != null) {
            fuel.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        ui.draw(g);

        if (explosionAnimation.isVisible()) {
            explosionAnimation.paintComponent(g);
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
            Bullet bullet = new Bullet(bulletX, bulletY, 10);
            bullets.add(bullet);
            sounds.playBlasterSound();
            lastShotTime = System.currentTimeMillis();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveShip();
        moveMeteors();
        moveFuel();

        if (bullets == null) {
            bullets = new ArrayList<>();
        }

        if (bullets != null) {
            List<Bullet> bulletsToRemove = new ArrayList<>();
            for (Bullet bullet : bullets) {
                bullet.move(meteors);
                if (bullet.isCollisionDetected()) {
                    bulletsToRemove.add(bullet);
                }
            }
            bullets.removeAll(bulletsToRemove);
        }

        if (interactions != null) {
            interactions.checkCollisions();
            interactions.addScore();
            endGame();
        }

        checkCollisions();

        repaint();

        if (gameResetting && !explosionAnimation.isVisible() && !restartingAnimation) {
            restartAnimation();
        }
    }

    private void restartAnimation() {
        restartingAnimation = true;
        explosionAnimation.stopAnimation();
        explosionAnimation.setVisible(false);
        restartingAnimation = false;
        gameResetting = false;
    }

    public void endGame() {
        if (interactions.isEndOfTheGame() || fuel.getCapacity() == 0) {
            resetExplosionAnimation();
            timer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                spaceShip.moveUp();
                break;
            case KeyEvent.VK_S:
                spaceShip.moveDown(getHeight());
                break;
            case KeyEvent.VK_A:
                spaceShip.moveLeft();
                break;
            case KeyEvent.VK_D:
                spaceShip.moveRight(getWidth());
                break;
            case KeyEvent.VK_SPACE:
                if (timer.isRunning()) {
                    createBullet();
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if (!timer.isRunning()) {
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_ENTER:
                if (!timer.isRunning()) {
                    fuel.resetPosition();
                    fuel.renewCapacity();
                    for (Bullet bullet : bullets) {
                        bullet.setCollisionDetected(true);
                    }
                    spaceShip.resetCoordinates(getWidth(), getHeight());
                    for (Meteor meteor : meteors) {
                        meteor.resetPosition();
                    }

                    interactions.ResetGame();
                    gameResetting = true;
                    timer.restart();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
