package spaceshootergame;

import java.awt.Rectangle;
import java.util.List;

public class Interactions {

    private Ship spaceShip;
    private List<Meteor> meteors;
    private int life = 3;
    private Fuel fuel;
    private boolean endOfTheGame = false;
    private List<Bullet> bullets;
    private int score = 0;
    private ExplosionAnimation explosionAnimation;
    private boolean explosionAnimationRunning = false;
    private boolean meteorCollisionHandled = false;

    public Interactions(Ship spaceShip, List<Meteor> meteors, Fuel fuel, List<Bullet> bullets, ExplosionAnimation explosionAnimation) {
        this.spaceShip = spaceShip;
        this.meteors = meteors;
        this.fuel = fuel;
        this.bullets = bullets;
        this.explosionAnimation = explosionAnimation;
    }

    public void setSpaceShip(Ship spaceShip) {
        this.spaceShip = spaceShip;
    }

    public void checkCollisions() {
        if (spaceShip == null) {
            return;
        }

        Rectangle shipBounds = new Rectangle(spaceShip.getShipX(), spaceShip.getShipY(), spaceShip.getShipWidth(), spaceShip.getShipHeight());

        float shipMiddleX = spaceShip.getShipX() + spaceShip.getShipWidth() / 2;
        float shipMiddleY = spaceShip.getShipY() + spaceShip.getShipHeight() / 2;
        float shipRadius = spaceShip.getShipHeight() / 2;

        for (Meteor meteor : meteors) {
            if (!meteor.isCollisionDetected()) {
                double deltaX = shipMiddleX - meteor.getMiddleX();
                double deltaY = shipMiddleY - meteor.getMiddleY();

                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                if (distance < (meteor.getRadius() + shipRadius)) {
                    Sounds.playCrashSound();
                    handleCollision(meteor);

                    if (!explosionAnimationRunning && !meteorCollisionHandled) {
                        explosionAnimation.startAnimation();
                        explosionAnimationRunning = true;
                        meteorCollisionHandled = true;
                    }
                }
            }

            for (Bullet bullet : bullets) {
                Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
                if (!bullet.isCollisionDetected() && bulletBounds.intersects(meteor.getBounds())) {
                    handleCollision(bullet, meteor);
                }
            }
        }

        if (!fuel.isCollisionDetected() && shipBounds.intersects(fuel.getBounds())) {
            handleCollision(fuel);
        }

        // Dodaj następujący fragment kodu, aby zresetować animację po zakończeniu:
        if (explosionAnimationRunning && explosionAnimation.getCurrentFrame() == explosionAnimation.getTotalFrames() - 1) {
            explosionAnimation.stopAnimation();
            explosionAnimationRunning = false;
            meteorCollisionHandled = false;
        }
    }

    private void handleCollision(Meteor meteor) {
        if (meteor.isCollisionDetected()) {
            return;
        }

        meteor.setCollisionDetected(true);
        life--;

        if (spaceShip != null) {
            float shipMiddleX = spaceShip.getShipX() + spaceShip.getShipWidth() / 2;
            float shipMiddleY = spaceShip.getShipY() + spaceShip.getShipHeight() / 2;
            float shipRadius = spaceShip.getShipHeight() / 2;

            double deltaX = shipMiddleX - meteor.getMiddleX();
            double deltaY = shipMiddleY - meteor.getMiddleY();

            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            if (distance < (meteor.getRadius() + shipRadius)) {
                if (!explosionAnimationRunning) {
                    explosionAnimation.startAnimation();
                    explosionAnimationRunning = true;
                }

                meteorCollisionHandled = false;
                meteor.setCollisionDetected(false);
            }
        }

        meteor.resetPosition();
        if (life == 0) {
            endOfTheGame = true;
        }
        
        // Dodaj następujące linie, aby zresetować flagi po utracie wszystkich żyć:
        if (endOfTheGame) {
            explosionAnimationRunning = false;
            meteorCollisionHandled = false;
        }
    }

    private void handleCollision(Bullet bullet, Meteor meteor) {
        bullet.setCollisionDetected(true);
        meteor.resetPosition();
    }

    private void handleCollision(Fuel fuel) {
        fuel.setCollisionDetected(true);
        fuel.addfuel();
        fuel.setFuelGenerated(true);
        fuel.resetPosition();
    }

    public boolean isEndOfTheGame() {
        return endOfTheGame;
    }

    public void ResetGame() {
        life = 3;
        endOfTheGame = false;
        score = 0;
        explosionAnimationRunning = false;
        meteorCollisionHandled = false;
    }

    public int getLife() {
        return life;
    }

    public void addScore() {
        score += 10;
    }

    public int getScore() {
        return score;
    }
}
