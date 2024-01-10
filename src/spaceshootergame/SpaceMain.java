/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package spaceshootergame;

import javax.swing.JFrame;

/**
 *
 * @author PC
 */
public class SpaceMain {
private final static int screenWidth=800;
private final static int screenHeight=600;
    public static void main(String[] args) {
            
        JFrame frame = new JFrame("Space Game");
    SpaceShipComponent spaceShipComponent = new SpaceShipComponent();
    frame.add(spaceShipComponent);
    frame.setSize(screenWidth, screenHeight); // Ustawiamy rozmiar okna gry
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
    
}
