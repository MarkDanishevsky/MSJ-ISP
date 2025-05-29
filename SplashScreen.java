import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SplashScreen extends JPanel {
    private final FadeImagePanel leftPanel;
    private final FadeImagePanel rightPanel;
    private final ImageAnimatorPanel animationPanel;

    public SplashScreen() {

        leftPanel = new FadeImagePanel();
        rightPanel = new FadeImagePanel();
        animationPanel = new ImageAnimatorPanel(
            new String[]{"assets/bird/pixil-frame-0.png", "assets/bird/pixil-frame-2.png", "assets/bird/pixil-frame-3.png"},
            300 // ms per frame
        );

        JPanel mainContainer = new JPanel(new BorderLayout());

        JPanel imagesContainer = new JPanel(new GridLayout(1, 2));
        imagesContainer.add(leftPanel);
        imagesContainer.add(rightPanel);

        mainContainer.add(imagesContainer, BorderLayout.CENTER);
        mainContainer.add(animationPanel, BorderLayout.SOUTH);

        Main.frame.setContentPane(mainContainer);
    }

    public void displayImages(String path1, String path2) throws IOException {
        leftPanel.setImage(path1);
        rightPanel.setImage(path2);
        setVisible(true);
        leftPanel.startFade();
        rightPanel.startFade();
        animationPanel.startAnimation(); // Start animation in parallel
    }
}