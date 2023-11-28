/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spaceshootergame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author PC
 */
public class Sounds {
    private Clip backgroundMusicClip;
    private boolean isMusicPlaying = false;
    
    public Sounds(){
        initSounds();
    toggleBackgroundMusic();
    
    }
    
    private void initSounds() {
    try {
        // Muzyka w tle
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/daft.wav"));
        if (audioInputStream == null) {
            throw new RuntimeException("Unable to load audio file");
        }

        backgroundMusicClip = AudioSystem.getClip();
        backgroundMusicClip.open(audioInputStream);

    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

    
    private void toggleBackgroundMusic() {
        if (!isMusicPlaying) {
            try {
                // Muzyka w tle
                backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
                isMusicPlaying = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            backgroundMusicClip.stop();
            isMusicPlaying = false;
        }
    }
    public static void main(String[] args) {
        Sounds sounds=new Sounds();
        
    }
}
