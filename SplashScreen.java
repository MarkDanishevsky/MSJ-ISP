import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SplashScreen extends JPanel {
    private final FadeImagePanel imgPanel;
    private final ImageAnimatorPanel animationPanel;

    public SplashScreen() {
        setLayout(new BorderLayout());

        imgPanel = new FadeImagePanel();

        String[] birdFrames = new String[10]; 
        for (int i = 0; i < 10; i++) {
            birdFrames[i] = "assets/bird/pixil-frame-" + i + ".png";
        }

        animationPanel = new ImageAnimatorPanel(birdFrames, 50); 

        JPanel imagesContainer = new JPanel(new GridLayout(1, 1));
        imagesContainer.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // top padding
        imagesContainer.add(imgPanel);

        JPanel animationWrapper = new JPanel(new BorderLayout());
        animationWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0)); // top margin + bottom spacing
        animationWrapper.add(animationPanel, BorderLayout.CENTER);

        add(imagesContainer, BorderLayout.CENTER);
        add(animationWrapper, BorderLayout.SOUTH);
    }

    public void displayImages(String path1, String path2) throws IOException {
        imgPanel.setImage(path1);
        imgPanel.startFade();
        animationPanel.startAnimation(); 
    }
}
