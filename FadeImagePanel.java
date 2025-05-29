import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class FadeImagePanel extends JPanel {
    private BufferedImage image;
    private float alpha = 0f;
    private final Timer fadeTimer;

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

    public void setImage(String path) throws IOException {
        image = ImageIO.read(new File(path));
        alpha = 0f; // reset fade
        repaint();
    }

    public void startFade() {
        fadeTimer.restart();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha
        ));

        int fixedW = 505;
        int fixedH = 296;
        int x = (getWidth() - fixedW) / 2;
        int y = (getHeight() - fixedH) / 2;

        g2.drawImage(image, x, y, fixedW, fixedH, this);
        g2.dispose();
    }
}