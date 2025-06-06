import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageAnimatorPanel extends JPanel implements Runnable {
    private final String[] framePaths;
    private final BufferedImage[] frames;
    private final int frameDelay;
    private int currentFrame = 0;
    private boolean running = false;

    private static final int TARGET_WIDTH = 234;
    private static final int TARGET_HEIGHT = 130;

    public ImageAnimatorPanel(String[] framePaths, int frameDelay) {
        this.framePaths = framePaths;
        this.frameDelay = frameDelay;
        this.frames = new BufferedImage[framePaths.length];
        setPreferredSize(new Dimension(1024, 200));

        for (int i = 0; i < framePaths.length; i++) {
            try {
                BufferedImage original = ImageIO.read(new File(framePaths[i]));
                Image scaled = original.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_SMOOTH);
                BufferedImage bufferedScaled = new BufferedImage(TARGET_WIDTH, TARGET_HEIGHT,
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedScaled.createGraphics();
                g2.drawImage(scaled, 0, 0, null);
                g2.dispose();
                frames[i] = bufferedScaled;
            } catch (IOException e) {
                System.err.println("Failed to load frame: " + framePaths[i]);
            }
        }
    }

    public void startAnimation() {
        if (!running) {
            running = true;
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        while (running) {
            repaint();
            try {
                Thread.sleep(frameDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage frame = frames[currentFrame];
        if (frame != null) {
            int x = (getWidth() - frame.getWidth()) / 2;
            int y = (getHeight() - frame.getHeight()) / 2;
            g.drawImage(frame, x, y, this);
        }
    }
}
