package spaceshootergame;

import java.awt.Rectangle;
import java.util.List;

public class Interactions {

    private Ship spaceShip;
    private List<Meteor> meteors;
    private int life=3;
    private Fuel fuel;
    private boolean endOfTheGame=false;
    private List<Bullet> bullets;
    private int score=0;

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
    
    float shipMiddleX=spaceShip.getShipX()+spaceShip.getShipWidth()/2;
    float shipMiddleY=spaceShip.getShipY()+spaceShip.getShipHeight()/2;
    float shipRadius=spaceShip.getShipHeight()/2;
    
    
    
    for (Meteor meteor : meteors) {
        
        double deltaX = shipMiddleX - meteor.getMiddleX();
        double deltaY = shipMiddleY - meteor.getMiddleY();

        // Wykorzystanie wzoru na odległość między punktami w dwuwymiarowej przestrzeni
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        
    

        // Check collision between ship and meteor
        if (!meteor.isCollisionDetected() && distance<(meteor.getRadius()+shipRadius)) {
            Sounds.playCrashSound();
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
        
        life--;
        System.out.println(life);
        meteor.resetPosition();
        if (life==0){
            endOfTheGame=true;
            
        }
    }
    
    private void handleCollision(Bullet bullet, Meteor meteor) {
    // Handle collision logic here
    // For example, you can set a flag for game over or take other actions
    
    bullet.setCollisionDetected(true);  // Mark collision as detected for this bullet
    meteor.resetPosition();
}

    
    private void handleCollision(Fuel fuel) {
    // Handle collision logic here
    // For example, you can set a flag for game over or take other actions
        fuel.setCollisionDetected(true);  // Mark collision as detected for this meteor

        fuel.addfuel();
        
        fuel.setFuelGenerated(true);  // Set isFuelGenerated to true after handling the collision
        fuel.resetPosition();
}

    public boolean isEndOfTheGame() {
        return endOfTheGame;
    }

    public void ResetGame(){
    life=3;
    endOfTheGame=false;
    score=0;
    
    }

    public int getLife() {
        return life;
    }
    
    public void addScore(){
    score+=10;
    }

    public int getScore() {
        return score;
    }
    
    
}

