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
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class Instructions extends JPanel {
    private final ArrayList<String> instructions = new ArrayList<>();
    private int currentIndex = 0;
    private final JLabel textLabel;

    private Image backgroundImage;

    public Instructions(JFrame parentFrame) {
        setLayout(new BorderLayout());

        loadInstructions("assets/instructions/instructions.txt");
        backgroundImage = new ImageIcon("assets/background.png").getImage();

        textLabel = new JLabel("", SwingConstants.CENTER);
        textLabel.setFont(Main.AthensClassic30);
        textLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        updateText();
        add(textLabel, BorderLayout.CENTER);

        // create buttons
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton menuButton = new JButton("Back to Menu");

        prevButton.setFont(Main.AthensClassic24);
        nextButton.setFont(Main.AthensClassic24);
        menuButton.setFont(Main.AthensClassic24);

        // Previous button listener
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to previous instruction
                if (currentIndex == 0) {
                    currentIndex = 0;
                } else {
                    currentIndex--;
                }
                updateText();
            }
        });

        // Next button listener
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // go to next instruction
                if (currentIndex == instructions.size() - 1) {
                    currentIndex = instructions.size() - 1;
                } else {
                    currentIndex++;
                }
                updateText();
            }
        });

        // Back to Menu button listener
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // back to menu
                parentFrame.setContentPane(new Menu());
                parentFrame.revalidate();
            }
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(0,0,0,0)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0)); 
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadInstructions(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    instructions.add(line.trim());
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load instructions from `" + filename + "`:\n" + ex.getMessage(),
                "Load Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        if (instructions.isEmpty()) {
            instructions.add("No instructions available.");
        }
    }

    private void updateText() {
        String text = instructions.get(currentIndex);
        textLabel.setText("<html><div style='text-align: center;'>" + text + "</div></html>");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
