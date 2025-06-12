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
 * Date: Wednesday June 11th, 2025
 * 
 * This is class is a reusable JPanel that displays scrolling or static 
 * informational texts loaded from text files with a background image. 
 * Instructions and Credits inherit this class
 */

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InfoScreen extends JPanel {

    /**
     * List of messages to be displayed on the screen.
     */
    protected final ArrayList<String> messages = new ArrayList<>();

    /**
     * Index of the current message being displayed.
     */
    protected int currentIndex = 0;

    /**
     * Label used to render the current message in the center of the panel.
     */
    protected final JLabel textLabel = new JLabel("", SwingConstants.CENTER);

    /**
     * The background image for this panel.
     */
    protected Image backgroundImage;

    /**
     * Constructs an InfoScreen panel.
     *
     * @param filePath       the path to the text file containing messages
     * @param backgroundPath the path to the background image file
     */
    public InfoScreen(String filePath, String backgroundPath) {
        setLayout(new BorderLayout());

        loadMessages(filePath);
        backgroundImage = new ImageIcon(backgroundPath).getImage();

        textLabel.setFont(Main.AthensClassic30);
        textLabel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        updateText();
        add(textLabel, BorderLayout.CENTER);
    }

    /**
     * Loads non-empty lines from a file into the messages list.
     * If the file is empty or unreadable, fallback messages are added.
     *
     * @param filename the path to the text file containing messages
     */
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

    /**
     * Updates the textLabel to display the current message in HTML format.
     */
    protected void updateText() {
        String text = messages.get(currentIndex);
        textLabel.setText("<html><div style='text-align: center; padding-left: 50px; padding-right: 50px;'>" + text
                + "</div></html>");
        textLabel.setForeground(Color.BLACK);
    }

    /**
     * Paints the background image stretched to fit the panel.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}