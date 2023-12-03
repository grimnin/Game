package spaceshootergame;

import java.awt.Rectangle;
import java.util.List;

public class Interactions {

    private Ship spaceShip;
    private List<Meteor> meteors;
    private int life=0;
    private Fuel fuel;
    private boolean endOfTheGame=false;
    private List<Bullet> bullets;

    public Interactions(Ship spaceShip, List<Meteor> meteors,Fuel fuel,List<Bullet> bullets) {
        this.spaceShip = spaceShip;
        this.meteors = meteors;
        this.fuel=fuel;
        this.bullets=bullets;
    }

    public void setSpaceShip(Ship spaceShip) {
        this.spaceShip = spaceShip;
    }

    public void checkCollisions() {
    if (spaceShip == null) {
        return;
    }

    Rectangle shipBounds = new Rectangle(spaceShip.getShipX(), spaceShip.getShipY(), spaceShip.getShipWidth(), spaceShip.getShipHeight());

    for (Meteor meteor : meteors) {
        

        // Check collision between ship and meteor
        if (!meteor.isCollisionDetected() && shipBounds.intersects(meteor.getBounds())) {
            handleCollision(meteor);
        }

        // Check collision between bullets and meteor
        
        for (Bullet bullet : bullets) {
            Rectangle bulletBounds = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
            if (!bullet.isCollisionDetected() && bulletBounds.intersects(bullet.getBounds())) {
                handleCollision(bullet, meteor);
            }
        }
    }

    // Check collision between ship and fuel
    if (!fuel.isCollisionDetected() && shipBounds.intersects(fuel.getBounds())) {
        handleCollision(fuel);
    }
}



    private void handleCollision(Meteor meteor) {
        // Handle collision logic here
        // For example, you can set a flag for game over or take other actions
        meteor.setCollisionDetected(true);  // Mark collision as detected for this meteor
        System.out.println("Collision detected with meteor!");
        life++;
        System.out.println(life);
        meteor.resetPosition();
        if (life==3){
            endOfTheGame=true;
            System.out.println("Koniec gry");
        }
    }
    
    private void handleCollision(Bullet bullet, Meteor meteor) {
    // Handle collision logic here
    // For example, you can set a flag for game over or take other actions
    System.out.println("Projectile hit the meteor!");
    bullet.setCollisionDetected(true);  // Mark collision as detected for this bullet
    meteor.resetPosition();
}

    
    private void handleCollision(Fuel fuel) {
    // Handle collision logic here
    // For example, you can set a flag for game over or take other actions
        fuel.setCollisionDetected(true);  // Mark collision as detected for this meteor
        System.out.println("Fuel gathered!");
        fuel.addfuel();
        System.out.println(fuel.getCapacity());
        fuel.setFuelGenerated(true);  // Set isFuelGenerated to true after handling the collision
        fuel.resetPosition(800, 600);
}

    public boolean isEndOfTheGame() {
        return endOfTheGame;
    }

    
}

