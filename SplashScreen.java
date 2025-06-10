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
 * This is the splash screen for our game.
 */
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The {@code SplashScreen} class is a custom Swing {@link JPanel} that displays
 * a fading image effect and an animated bird graphic at the bottom.
 * 
 * <p>
 * This panel can be used as a startup screen or intro screen in a GUI
 * application.
 * It combines two components:
 * <ul>
 * <li>{@code FadeImagePanel} – for showing a fade-in image effect</li>
 * <li>{@code ImageAnimatorPanel} – for looping through animation frames</li>
 * </ul>
 * 
 * <p>
 * Call {@link #displayImages(String, String)} to begin the animation and
 * fade-in sequence.
 */
public class SplashScreen extends JPanel {

    /**
     * Panel responsible for displaying a fade-in image.
     */
    private final FadeImagePanel imgPanel;

    /**
     * Panel responsible for displaying an animated image sequence.
     */
    private final ImageAnimatorPanel animationPanel;

    /**
     * Constructs a {@code SplashScreen} panel with a center fade image
     * and an animated graphic aligned at the bottom.
     */
    public SplashScreen() {
        setLayout(new BorderLayout());

        imgPanel = new FadeImagePanel();

        String[] birdFrames = new String[10];
        for (int i = 0; i < 10; i++) {
            birdFrames[i] = "assets/bird/pixil-frame-" + i + ".png";
        }

        animationPanel = new ImageAnimatorPanel(birdFrames, 100);

        JPanel imagesContainer = new JPanel(new GridLayout(1, 1));
        imagesContainer.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        imagesContainer.add(imgPanel);

        JPanel animationWrapper = new JPanel(new BorderLayout());
        animationWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));
        animationWrapper.add(animationPanel, BorderLayout.CENTER);

        add(imagesContainer, BorderLayout.CENTER);
        add(animationWrapper, BorderLayout.SOUTH);
    }

    /**
     * Displays two images by setting the first image for fade-in and starting the
     * animation.
     *
     * @param path1 The path to the first image file to display with a fade-in
     *              effect.
     * @param path2 The path to the second image file (optional, may be used in fade
     *              logic).
     * @throws IOException if any image file fails to load.
     */
    public void displayImages(String path1, String path2) throws IOException {
        imgPanel.setImage(path1);
        imgPanel.startFade();
        animationPanel.startAnimation();
    }
}
