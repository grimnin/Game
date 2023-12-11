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

    /**
     * @param args the command line arguments
     */
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
