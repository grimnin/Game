package spaceshootergame;

import java.awt.Rectangle;
import java.util.List;

public class Interactions {

    private Ship spaceShip;
    private List<Meteor> meteors;
    private int counter=0;

    public Interactions(Ship spaceShip, List<Meteor> meteors) {
        this.spaceShip = spaceShip;
        this.meteors = meteors;
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
    }

    private void handleCollision(Meteor meteor) {
        // Handle collision logic here
        // For example, you can set a flag for game over or take other actions
        meteor.setCollisionDetected(true);  // Mark collision as detected for this meteor
        System.out.println("Collision detected with meteor!");
        counter++;
        System.out.println(counter);
    }
}
