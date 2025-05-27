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
 * Date: Friday May 23rd, 2025
 * 
 * This is the menu instuctions page for our game.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Instructions extends JPanel {

    public Instructions(JFrame parentFrame) {
        setLayout(new BorderLayout());

        JLabel instructionsLabel = new JLabel(
            "Put instructions here Joe in HTML format",
            SwingConstants.CENTER
        );
        instructionsLabel.setFont(Main.AthensClassic);
        add(instructionsLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.addActionListener((ActionEvent e) -> {
            parentFrame.setContentPane(new Menu()); // Switch back to the menu panel
            parentFrame.revalidate();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
