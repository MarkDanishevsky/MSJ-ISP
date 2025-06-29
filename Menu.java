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
 * This is the menu for our game.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class represents the main menu screen for the game.
 * It displays clickable buttons for navigating to other sections such as Instructions, Storyline, Game, Minigame, Credits, and Leaderboard.
 */
public class Menu extends JPanel {

    /**
     * Constructs a new Menu panel.
     * Loads and scales the background image, initializes button icons, and configures button actions and layout.
     */
    public Menu() {
        ImageIcon originalIcon = new ImageIcon("assets/background.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(1024, 768, Image.SCALE_SMOOTH);
        setLayout(null); // Use absolute positioning

        // Load and set custom font for buttons
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Main.AthensClassic24);
            UIManager.put("Button.font", Main.AthensClassic24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageIcon icon = new ImageIcon("assets/folder/folderClosed.png");

        JButton button0 = createCustomButton("Instructions", icon, 100, 230, Main.frame);
        JButton button1 = createCustomButton("Storyline", icon, 300, 230, Main.frame);
        JButton button2 = createCustomButton("Game", icon, 500, 230, Main.frame);
        JButton button3 = createCustomButton("Minigame", icon, 100, 430, Main.frame);
        JButton button4 = createCustomButton("Credits", icon, 300, 430, Main.frame);
        JButton button5 = createCustomButton("Leaderboard", icon, 500, 430, Main.frame);

        add(button0);
        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);

        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setBounds(0, 0, 1024, 768);
        add(backgroundLabel);
    }

    /**
     * Creates and configures a custom JButton with the given label and icon, places it at the specified coordinates,
     * and sets the button's action to switch to the appropriate panel depending on its name.
     *
     * @param name the label for the button and the name of the section it navigates to
     * @param icon the default icon to use on the button
     * @param x the horizontal position of the button
     * @param y the vertical position of the button
     * @param parentFrame the main application window used to switch content
     * @return a customized JButton with hover and click functionality
     */
    private JButton createCustomButton(String name, ImageIcon icon, int x, int y, JFrame parentFrame) {
        JButton button = new JButton(name, icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setBounds(x, y, icon.getIconWidth() + 20, icon.getIconHeight() + 40);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon scaledIcon = new ImageIcon("assets/folder/folderOpen.png");
                button.setIcon(scaledIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon);
            }
        });
        
        if (name.equals("Instructions")) {
            button.addActionListener(e -> {
                Instructions instructions = new Instructions(parentFrame);
                parentFrame.setContentPane(instructions);
                parentFrame.revalidate();
            });
        } else if (name.equals("Storyline")) {
            button.addActionListener(e -> {
                Storyline story = new Storyline(parentFrame);
                parentFrame.setContentPane(story);
                parentFrame.revalidate();
            });
        } else if (name.equals("Credits")) {
            button.addActionListener(e -> {
                Credits credits = new Credits(parentFrame);
                parentFrame.setContentPane(credits);
                parentFrame.revalidate();
            });
        } else if (name.equals("Game")) {
            button.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> {
                    MainGame game = new MainGame();
                    parentFrame.setContentPane(game);
                    parentFrame.setVisible(true);
                });
            });
        } else if (name.equals("Minigame")) {
            button.addActionListener(e -> {
                Minigame minigame = new Minigame(parentFrame);
                parentFrame.setContentPane(minigame);
                parentFrame.revalidate();
            });
        }  else if (name.equals("Leaderboard")) {
            button.addActionListener(e -> {
                Leaderboard lb = new Leaderboard(parentFrame);
                parentFrame.setContentPane(lb);
                parentFrame.revalidate();
            });
        }else {
            button.addActionListener(e -> JOptionPane.showMessageDialog(Menu.this, name + " clicked"));
        }

        return button;
    }
}
