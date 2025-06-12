
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
 * Date: Friday May 30th, 2025
 * 
 * This is the menu instuctions page for our game.
 * It extends InfoScreen, loading its content from a multi-line text file
 * and displaying each line as a separate page. 
 */
import java.awt.*;
import javax.swing.*;

public class Instructions extends InfoScreen {

    /**
     * Constructs the instructions screen panel.
     *
     * @param parentFrame the main application window, used to switch views
     */
    public Instructions(JFrame parentFrame) {
        super("assets/instructions/instructions.txt", "assets/instructions/instructions-bg.png");

        // Create navigation buttons
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton menuButton = new JButton("Back to Menu");

        // Set button fonts
        prevButton.setFont(Main.AthensClassic24);
        nextButton.setFont(Main.AthensClassic24);
        menuButton.setFont(Main.AthensClassic24);

        // Handle previous button action
        prevButton.addActionListener(e -> {
            if (currentIndex > 0)
                currentIndex--;
            updateText();
        });

        // Handle next button action
        nextButton.addActionListener(e -> {
            if (currentIndex < messages.size() - 1)
                currentIndex++;
            updateText();
        });

        // Handle menu button action
        menuButton.addActionListener(e -> {
            parentFrame.setContentPane(new Menu());
            parentFrame.revalidate();
        });

        // Arrange buttons in a panel and add to the bottom of the layout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }
}