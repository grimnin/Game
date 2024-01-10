package spaceshootergame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sounds {
    private Clip backgroundMusicClip;
    private Clip blasterSoundClip;
    private static Clip explosionSoundClip;
    private boolean isMusicPlaying = false;

    public Sounds() {
        initSounds();
        toggleBackgroundMusic();
    }

    private void initSounds() {
        try {
            // Muzyka w tle
            AudioInputStream backgroundMusicInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/daft.wav"));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(backgroundMusicInputStream);

            // Dźwięk blastera
            AudioInputStream blasterSoundInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/blaster.wav"));
            blasterSoundClip = AudioSystem.getClip();
            blasterSoundClip.open(blasterSoundInputStream);
            
            
            AudioInputStream explosionSoundInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/explosion.wav"));
            explosionSoundClip = AudioSystem.getClip();
            explosionSoundClip.open(explosionSoundInputStream);
            
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

   public void playBlasterSound() {
    try {
        if (blasterSoundClip.isRunning()) {
            blasterSoundClip.stop();
        }
        blasterSoundClip.setFramePosition(0);
        blasterSoundClip.start();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
   public static void playCrashSound(){
   try {
        if (explosionSoundClip.isRunning()) {
            explosionSoundClip.stop();
        }
        explosionSoundClip.setFramePosition(0);
       explosionSoundClip.start();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
   }


    public static void main(String[] args) {
        Sounds sounds = new Sounds();
        // Odtwórz dźwięk blastera
        sounds.playBlasterSound();
    }
}
