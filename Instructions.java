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
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Instructions extends JPanel {

    public Instructions(JFrame parentFrame) {
        
        setLayout(new BorderLayout());

        JLabel instructionsLabel = new JLabel(
            "<html><div style='text-align: center;'>"
            + "For each event, select one headline for the article. Once you have gone through each of the events, choose one article to be the headline article.",
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
