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
     * The panel responsible for handling a fade-in image effect during the splash screen.
     */
    private final FadeImagePanel imgPanel;

    /**
     * The panel that displays a looping bird animation at the bottom of the splash screen.
     */
    private final ImageAnimatorPanel animationPanel;

    /**
     * Constructs the SplashScreen panel.
     * Sets up the layout with a centered image that fades in and a looping bird animation at the bottom.
     */
    public SplashScreen() {
        setLayout(new BorderLayout());

        imgPanel = new FadeImagePanel();

        JLabel bylineLabel = new JLabel("A game made by:", SwingConstants.CENTER);
        bylineLabel.setFont(Main.AthensClassic30);

        String[] birdFrames = new String[10];
        for (int i = 0; i < 10; i++) {
            birdFrames[i] = "assets/bird/pixil-frame-" + i + ".png";
        }

        animationPanel = new ImageAnimatorPanel(birdFrames, 100);

        JPanel imagesContainer = new JPanel(new BorderLayout());
        imagesContainer.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        imagesContainer.add(bylineLabel, BorderLayout.NORTH);
        imagesContainer.add(imgPanel, BorderLayout.CENTER);

        JPanel animationWrapper = new JPanel(new BorderLayout());
        animationWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));
        animationWrapper.add(animationPanel, BorderLayout.CENTER);

        add(imagesContainer, BorderLayout.CENTER);
        add(animationWrapper, BorderLayout.SOUTH);
    }

    /**
     * Begins the splash screen sequence by loading the fade-in image and starting the animation.
     *
     * @param path1 the path to the main image to fade in
     * @param path2 the path to a second image (currently unused, but may be part of fade logic)
     * @throws IOException if either image cannot be loaded
     */
    public void displayImages(String path1, String path2) throws IOException {
        imgPanel.setImage(path1);
        imgPanel.startFade();
        animationPanel.startAnimation();
    }
}
