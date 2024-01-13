package spaceshootergame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ExplosionAnimation extends JPanel {

    private static final int FRAME_SIZE = 60;
    private static final int NUM_ROWS = 4;
    private static final int NUM_COLUMNS = 4;
    private static final int FRAME_WIDTH = 60;
    private static final int FRAME_HEIGHT = 60;
    private static final String IMAGE_PATH = "/pictures/explosion.png";

    private BufferedImage animationImage;
    private int currentFrame = 0;
    private Timer animationTimer;

    public ExplosionAnimation() {
        loadAnimationImage();
        setVisible(false);
    }

    private void loadAnimationImage() {
        try {
            animationImage = ImageIO.read(getClass().getResource(IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() {
        currentFrame = 0;
        setVisible(true);
        animationTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % (NUM_ROWS * NUM_COLUMNS);
                repaint();
            }
        });
        animationTimer.start();
    }

    public void stopAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            setVisible(false);
        }
    }

    public int getTotalFrames() {
        return NUM_ROWS * NUM_COLUMNS;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FRAME_SIZE, FRAME_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (animationImage != null) {
            int row = currentFrame / NUM_COLUMNS;
            int col = currentFrame % NUM_COLUMNS;

            int x = col * FRAME_WIDTH;
            int y = row * FRAME_HEIGHT;

            BufferedImage currentFrameImage = animationImage.getSubimage(x, y, FRAME_WIDTH, FRAME_HEIGHT);
            g.drawImage(currentFrameImage, 0, 0, FRAME_SIZE, FRAME_SIZE, null);
        }
    }
}
