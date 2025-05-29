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

    public ImageAnimatorPanel(String[] framePaths, int frameDelay) {
        this.framePaths = framePaths;
        this.frameDelay = frameDelay;
        this.frames = new BufferedImage[3];
        setPreferredSize(new Dimension(1024, 200)); // Height for animation area

        // Preload frames
        for (int i = 0; i < framePaths.length; i++) {
            try {
                frames[i] = ImageIO.read(new File(framePaths[i]));
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