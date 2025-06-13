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
 * This is the custom slider class for our game.
 * It loads a custom pointer image and displays it on the slider thumb.
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * CustomSlider is a UI delegate that provides a custom thumb image
 * for a JSlider component.
 * It loads a pointer image from the assets and scales it for use as the thumb.
 */
class CustomSlider extends BasicSliderUI {

    /**
     * The custom pointer image used to draw the slider thumb.
     */
    private Image pointerImage;

    /**
     * Constructs a CustomSlider UI for the specified JSlider.
     * Attempts to load and scale the custom pointer image.
     *
     * @param slider the JSlider this UI is associated with
     */
    public CustomSlider(JSlider slider) {
        super(slider);
        try {
            BufferedImage raw = ImageIO.read(new File("assets/pointer.png"));
            pointerImage = raw.getScaledInstance(8 * 3, 10 * 3, Image.SCALE_SMOOTH); // Adjust size as needed
        } catch (IOException e) {
            System.err.println("Error loading pointer.png: " + e.getMessage());
        }
    }

    /**
     * Paints the slider thumb using the custom pointer image if available.
     * If the image is not loaded, falls back to the default thumb painting.
     *
     * @param g the Graphics context to paint on
     */
    @Override
    public void paintThumb(Graphics g) {
        if (pointerImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            int x = thumbRect.x + (thumbRect.width - pointerImage.getWidth(null)) / 2;
            int y = thumbRect.y + (thumbRect.height - pointerImage.getHeight(null)) / 2;
            g2d.drawImage(pointerImage, x, y, null);
        } else {
            super.paintThumb(g);
            System.out.println("Fallback: using default thumb");
        }
    }
}