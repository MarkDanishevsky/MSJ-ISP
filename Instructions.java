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
 * It displays a list of instructions and allows the user to navigate between them.
 * The instructions are loaded from a CSV file and displayed as HTML labels.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Instructions extends InfoScreen {

    public Instructions(JFrame parentFrame) {
        super("assets/instructions/instructions.txt", "assets/instructions/instructions-bg.png");

        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton menuButton = new JButton("Back to Menu");

        prevButton.setFont(Main.AthensClassic24);
        nextButton.setFont(Main.AthensClassic24);
        menuButton.setFont(Main.AthensClassic24);

        prevButton.addActionListener(e -> {
            if (currentIndex > 0)
                currentIndex--;
            updateText();
        });

        nextButton.addActionListener(e -> {
            if (currentIndex < messages.size() - 1)
                currentIndex++;
            updateText();
        });

        menuButton.addActionListener(e -> {
            parentFrame.setContentPane(new Menu());
            parentFrame.revalidate();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
