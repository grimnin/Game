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

    public ExplosionAnimation() {
        loadAnimationImage();
        startAnimation();
    }

    private void loadAnimationImage() {
        try {
            animationImage = ImageIO.read(getClass().getResourceAsStream(IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() {
        Timer timer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % (NUM_ROWS * NUM_COLUMNS);
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(FRAME_SIZE, FRAME_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int row = currentFrame / NUM_COLUMNS;
        int col = currentFrame % NUM_COLUMNS;

        int x = col * FRAME_WIDTH;
        int y = row * FRAME_HEIGHT;

        if (x + FRAME_WIDTH <= animationImage.getWidth() && y + FRAME_HEIGHT <= animationImage.getHeight()) {
            BufferedImage currentFrameImage = animationImage.getSubimage(x, y, FRAME_WIDTH, FRAME_HEIGHT);
            g.drawImage(currentFrameImage, 0, 0, FRAME_SIZE, FRAME_SIZE, null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Explosion Animation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ExplosionAnimation());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    public void startAnimationAt(int x, int y) {
    // Ustaw pozycję animacji eksplozji
    setBounds(x, y, 50, 50);

    // Uruchom animację
    startAnimation();
}
    
    public void resetAnimation() {
    currentFrame = 0;
    repaint();
}

}