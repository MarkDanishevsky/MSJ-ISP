/*
 *  +-------------+
 *  | \      M    | \
 *  |   \         |   \
 *  |    +--------------+
 *  |    |       |      |
 *  | S  |       |      |
 *  |    |      J|      |
 *  + - -| - - - -      |
 *   \   |         \    |
 *     \ |           \  |
 *       +--------------+
 * 
 * MSJ Development Inc. (2025)
 * ISP
 * Client: Ms. Krasteva (ICS4U1, S2)
 * Date: Tuesday June 3rd, 2025
 * 
 * This is the class that handles the animation in the splash screen.
 */
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A custom Swing panel that animates a sequence of images.
 * Each image is loaded from disk, scaled to a fixed size, and displayed
 * in a loop with a specified delay between frames.
 * 
 * To use this class, embed it in a JFrame and call {@link #startAnimation()}.
 */
class ImageAnimatorPanel extends JPanel implements Runnable {

    /** Paths to the image files used as animation frames. */
    private final String[] framePaths;

    /** Array of scaled and buffered image frames. */
    private final BufferedImage[] frames;

    /** Delay between frames in milliseconds. */
    private final int frameDelay;

    /** Index of the currently displayed frame. */
    private int currentFrame = 0;

    /** Whether the animation is currently running. */
    private boolean running = false;

    /** Target width for scaled images. */
    private static final int TARGET_WIDTH = 234;

    /** Target height for scaled images. */
    private static final int TARGET_HEIGHT = 130;

    /**
     * Constructs an ImageAnimatorPanel with given frame file paths and delay.
     *
     * @param framePaths An array of file paths to image frames.
     * @param frameDelay Delay between frame transitions in milliseconds.
     */
    public ImageAnimatorPanel(String[] framePaths, int frameDelay) {
        this.framePaths = framePaths;
        this.frameDelay = frameDelay;
        this.frames = new BufferedImage[framePaths.length];

        setPreferredSize(new Dimension(1024, 200));

        for (int i = 0; i < framePaths.length; i++) {
            try {
                BufferedImage original = ImageIO.read(new File(framePaths[i]));
                Image scaled = original.getScaledInstance(TARGET_WIDTH, TARGET_HEIGHT, Image.SCALE_SMOOTH);
                BufferedImage bufferedScaled = new BufferedImage(
                        TARGET_WIDTH, TARGET_HEIGHT, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedScaled.createGraphics();
                g2.drawImage(scaled, 0, 0, null);
                g2.dispose();
                frames[i] = bufferedScaled;
            } catch (IOException e) {
                System.err.println("Failed to load frame: " + framePaths[i]);
            }
        }
    }

    /**
     * Starts the animation loop in a separate thread.
     * Does nothing if the animation is already running.
     */
    public void startAnimation() {
        if (!running) {
            running = true;
            new Thread(this).start();
        }
    }

    /**
     * Runs the animation loop, repainting the panel at the specified delay
     * and cycling through the image frames.
     */
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

    /**
     * Paints the current image frame centered in the panel.
     *
     * @param g The Graphics context to draw on.
     */
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
