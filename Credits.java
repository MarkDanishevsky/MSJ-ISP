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
 * This is the credits page for our game.
 * It shows the project lead, team members, and a thank you note to the course instructor.
 * It extends InfoScreen, loading its content from a text file and rendering a background image. 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Credits extends InfoScreen {

    /**
     * Constructs the credits screen panel.
     *
     * @param parentFrame the main application window, used to switch views
     */
    public Credits(JFrame parentFrame) {
        super("assets/credits.txt", "assets/background.png");

        // Create and configure the back button
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(Main.AthensClassic24);
        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.setContentPane(new Menu());
            parentFrame.revalidate();
        });

        // Wrap the button in a transparent panel and add it to the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}