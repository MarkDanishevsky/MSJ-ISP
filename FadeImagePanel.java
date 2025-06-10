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
 * This is the class that handles the fading image in the splash screen.
 */

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * {@code FadeImagePanel} is a custom Swing panel that displays an image
 * with a gradual fade-in effect using transparency.
 *
 * <p>
 * The image is centered and drawn at a fixed size.
 * The fade effect is achieved using a {@link Timer} that periodically
 * increases the alpha transparency value until the image is fully visible.
 */
class FadeImagePanel extends JPanel {

    /**
     * The image to be displayed and faded in.
     */
    private BufferedImage image;

    /**
     * The current alpha (transparency) level of the image.
     * Ranges from 0.0 (fully transparent) to 1.0 (fully visible).
     */
    private float alpha = 0f;

    /**
     * Timer responsible for incrementing the alpha value to create a fade-in
     * animation.
     */
    private final Timer fadeTimer;

    /**
     * Constructs a {@code FadeImagePanel} and initializes the fade animation timer.
     */
    public FadeImagePanel() {
        fadeTimer = new Timer(50, null);
        fadeTimer.addActionListener(e -> {
            alpha += 0.05f;
            if (alpha >= 1f) {
                alpha = 1f;
                fadeTimer.stop();
            }
            repaint();
        });
    }

    /**
     * Sets the image to be displayed from the specified file path and resets the
     * fade.
     *
     * @param path the file path to the image to display.
     * @throws IOException if the image file cannot be read.
     */
    public void setImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
        alpha = 0f;
        repaint();
    }

    /**
     * Starts or restarts the fade-in animation from fully transparent to fully
     * visible.
     */
    public void startFade() {
        fadeTimer.restart();
    }

    /**
     * Paints the component with the current image and alpha transparency.
     *
     * @param g the {@link Graphics} context in which to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null){
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        int fixedW = 505;
        int fixedH = 296;
        int x = (getWidth() - fixedW) / 2;
        int y = (getHeight() - fixedH) / 2;

        g2.drawImage(image, x, y, fixedW, fixedH, this);
        g2.dispose();
    }
}
