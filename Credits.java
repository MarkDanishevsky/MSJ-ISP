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
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Credits extends JPanel {

    public Credits(JFrame parentFrame) {
        setLayout(new BorderLayout());

        JLabel instructionsLabel = new JLabel(
            "<html><div style='text-align: center;'>"
            + "<b>Credits<b><br><br>"
            + "This game was made by MSJ Development Inc.<br>"
            + "Project Lead: Mark Danishevsky<br>"
            + "Members: Joseph Wang, Sebastian Wang<br><br>"
            + "Thank you to Ms. Krasteva for teaching the course."
            + "</div></html>",
            SwingConstants.CENTER
        );
        instructionsLabel.setFont(Main.AthensClassic30);
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
