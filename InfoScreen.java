import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InfoScreen extends JPanel {
    protected final ArrayList<String> messages = new ArrayList<>();
    protected int currentIndex = 0;
    protected final JLabel textLabel = new JLabel("", SwingConstants.CENTER);
    protected Image backgroundImage;

    public InfoScreen(String filePath, String backgroundPath) {
        setLayout(new BorderLayout());

        loadMessages(filePath);
        backgroundImage = new ImageIcon(backgroundPath).getImage();

        textLabel.setFont(Main.AthensClassic30);
        textLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        updateText();
        add(textLabel, BorderLayout.CENTER);
    }

    protected void loadMessages(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    messages.add(line.trim());
                }
            }
        } catch (IOException ex) {
            messages.add("Error loading content.");
        }

        if (messages.isEmpty()) {
            messages.add("No content available.");
        }
    }

    protected void updateText() {
        String text = messages.get(currentIndex);
        textLabel.setText("<html><div style='text-align: center; padding-left: 50px; padding-right: 50px;'>" + text
                + "</div></html>");
        textLabel.setForeground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
