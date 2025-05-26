/*
 * MSJ Development Inc. (2025)
 * ISP
 * Client: Ms. Krasteva (ICS4U1, S2)
 * Date: Friday May 23rd, 2025
 * 
 * This is the menu for our game.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {

    public Menu() {
        ImageIcon originalIcon = new ImageIcon("assets/background.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(1024, 768, Image.SCALE_SMOOTH);
        setContentPane(new JLabel(new ImageIcon(scaledImage)));
        getContentPane().setLayout(new BorderLayout());

        setTitle("Image Menu");
        setSize(1024, 794);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Use absolute positioning
        getContentPane().setLayout(null); // Use absolute positioning

        ImageIcon icon = new ImageIcon("assets/folderIcon.png");

        JButton button0 = createCustomButton("Instructions", icon, 100, 100);
        JButton button1 = createCustomButton("Storyline", icon, 300, 100);
        JButton button2 = createCustomButton("Game", icon, 500, 100);
        JButton button3 = createCustomButton("Minigame", icon, 100, 300);
        JButton button4 = createCustomButton("Credits", icon, 300, 300);

        getContentPane().add(button0);
        getContentPane().add(button1);
        getContentPane().add(button2);
        getContentPane().add(button3);
        getContentPane().add(button4);

        setVisible(true);
    }

    private JButton createCustomButton(String name, ImageIcon icon, int x, int y) {
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
                ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(
                    (int)(icon.getIconWidth() * 1.2), (int)(icon.getIconHeight() * 1.2), Image.SCALE_SMOOTH));
                button.setIcon(scaledIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setIcon(icon);
            }
        });

        button.addActionListener(e -> JOptionPane.showMessageDialog(Menu.this, name + " clicked"));

        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Menu::new);
    }
}