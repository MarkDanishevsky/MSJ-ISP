import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

class CustomSlider extends BasicSliderUI {
    private Image pointerImage;

    public CustomSlider(JSlider slider) {
        super(slider);
        try {
            BufferedImage raw = ImageIO.read(new File("assets/pointer.png"));
            pointerImage = raw.getScaledInstance(8 * 3, 10 * 3, Image.SCALE_SMOOTH); // Adjust size as needed
            System.out.println("Custom pointer loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading pointer.jpg: " + e.getMessage());
        }
    }

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