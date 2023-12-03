package spaceshootergame;

import java.awt.Rectangle;
import java.util.List;

public class Interactions {

    private Ship spaceShip;
    private List<Meteor> meteors;
    private int life=0;
    private Fuel fuel;
    private boolean endOfTheGame=false;

    public Interactions(Ship spaceShip, List<Meteor> meteors,Fuel fuel) {
        this.spaceShip = spaceShip;
        this.meteors = meteors;
        this.fuel=fuel;
    }

    public void setSpaceShip(Ship spaceShip) {
        this.spaceShip = spaceShip;
    }

    public void checkCollisions() {
        if (spaceShip == null) {
            return; // Ensure spaceShip is not null before accessing its properties
        }

        Rectangle shipBounds = new Rectangle(spaceShip.getShipX(), spaceShip.getShipY(), spaceShip.getShipWidth(), spaceShip.getShipHeight());

        for (Meteor meteor : meteors) {
            if (!meteor.isCollisionDetected() && shipBounds.intersects(meteor.getBounds())) {
                handleCollision(meteor);
            }
        }
        
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