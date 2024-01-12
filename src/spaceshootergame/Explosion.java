package spaceshootergame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Explosion {

    private static final int NUM_FRAMES = 16;
    private static final int FRAME_WIDTH = 60;
    private static final int FRAME_HEIGHT = 60;
    private static final String IMAGE_PATH = "/pictures/explosion.png";

    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int x, y;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        loadFrames();
    }

    private void loadFrames() {
        try {
            BufferedImage explosionSheet = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
            frames = new BufferedImage[NUM_FRAMES];

            for (int i = 0; i < NUM_FRAMES; i++) {
                frames[i] = explosionSheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(frames[currentFrame], x, y, FRAME_WIDTH, FRAME_HEIGHT, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public boolean isAnimationFinished() {
        return currentFrame == NUM_FRAMES - 1;
    }

    public void nextFrame() {
        if (!isAnimationFinished()) {
            currentFrame++;
        }
    }
}
