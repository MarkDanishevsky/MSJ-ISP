/*
 *  +-------------+
 *  | \      M    | \
 *  |   \         |   \
 *  |    +--------------+
 *  |    |       |      |
 *  | S  |       |      |
 *  |    |      J|      |
 *  + - -| - - - +      |
 *   \   |         \    |
 *     \ |           \  |
 *       +--------------+
 * 
 * MSJ Development Inc. (2025)
 * ISP
 * Client: Ms. Krasteva (ICS4U1, S2)
 * Date: Friday May 30rd, 2025
 * 
 * This a prototype of our gameplay.
 * 
 * Cube made using: https://1j01.github.io/ascii-hypercube/
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class HeadlineChooser extends JPanel {
    private int selectedPreviewIndex = -1;
    String[] dates = {"21st", "22nd", "23rd"};
    int dateNum = 0;

    Event[] events = {
        new Event(
            "The local lake reached record high levels.",
            new ArrayList<>(Arrays.asList(
                new Headline("Lake floods nearby park", 1),
                new Headline("Record-breaking rainfall hits region", 2),
                new Headline("Nothing unusual in lake levels, say officials", 2)
            ))
        ),
        new Event(
            "A new school policy was introduced.",
            new ArrayList<>(Arrays.asList(
                new Headline("Students protest new school rules", 1),
                new Headline("School board implements changes", 2),
                new Headline("Strict new policy raises eyebrows", 3)
            ))
        ),
        new Event(
            "Recycling rates increased by 15%.",
            new ArrayList<>(Arrays.asList(
                new Headline("City applauds recycling success", 1),
                new Headline("More residents sorting waste correctly", 2),
                new Headline("Critics say increase is 'exaggerated'", 3)
            ))
        ),
        new Event(
            "The mayor announced a new housing plan.",
            new ArrayList<>(Arrays.asList(
                new Headline("Mayor promises affordable housing", 1),
                new Headline("Bold new plan to tackle homelessness", 2),
                new Headline("Some call housing plan unrealistic", 3)
            ))
        )
    };

    private final String[] facts = {
        "The local lake reached record high levels.",
        "A new school policy was introduced.",
        "Recycling rates increased by 15%.",
        "The mayor announced a new housing plan."
    };

    private final String[][] headlineOptions = {
        {
            "Lake floods nearby park",
            "Record-breaking rainfall hits region",
            "Nothing unusual in lake levels, say officials"
        },
        {
            "Students protest new school rules",
            "School board implements changes",
            "Strict new policy raises eyebrows"
        },
        {
            "City applauds recycling success",
            "More residents sorting waste correctly",
            "Critics say increase is 'exaggerated'"
        },
        {
            "Mayor promises affordable housing",
            "Bold new plan to tackle homelessness",
            "Some call housing plan unrealistic"
        }
    };

    final ArrayList<String> selectedHeadlines = new ArrayList<>();
    private int currentIndex = 0;

    private final JLabel factLabel = new JLabel();
    private final JRadioButton[] options = new JRadioButton[3];
    private final ButtonGroup group = new ButtonGroup();
    private final JPanel previewPanel = new JPanel();
    private final JLabel pageLabel = new JLabel();

    public HeadlineChooser() {
        ImageIcon unselectedIcon = new ImageIcon("assets/bird/pixil-frame-0.png");
        ImageIcon selectedIcon = new ImageIcon("assets/bird/pixil-frame-5.png");
        JLabel title = new JLabel("The Oceania Times", SwingConstants.CENTER);
        title.setFont(Main.AthensClassic30);
        title.setBounds(450, 20, 500, 30);
        add(title);

        JLabel date = new JLabel("June " + dates[dateNum] + ", 1984", SwingConstants.CENTER);
        dateNum++;
        date.setFont(Main.AthensClassic24);
        date.setBounds(450, 50, 500, 20);
        add(date);

        setLayout(null); // manual positioning

        // Fact Label
        factLabel.setBounds(30, 60, 400, 40);
        factLabel.setFont(Main.AthensClassic24);
        add(factLabel);

        // Headline options
        for (int i = 0; i < 3; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(30, 120 + i * 40, 400, 30);
            options[i].setFont(Main.AthensClassic24);
            options[i].setIcon(unselectedIcon);
            options[i].setSelectedIcon(selectedIcon);
            options[i].setOpaque(false);
            options[i].setFocusPainted(false);
            options[i].setContentAreaFilled(false);
            options[i].setBorderPainted(false);
            int finalI = i;
            options[i].addActionListener(e -> {
                while (selectedHeadlines.size() <= currentIndex) {
                    selectedHeadlines.add("");
                }
                selectedHeadlines.set(currentIndex, headlineOptions[currentIndex][finalI]);
                updatePreview();
            });
            group.add(options[i]);
            add(options[i]);
        }

        // Arrows
        JButton leftArrow = new JButton("<");
        leftArrow.setBounds(30, 260, 50, 30);
        leftArrow.setFont(Main.AthensClassic24);
        leftArrow.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateLeftPanel();
            }
        });
        add(leftArrow);

        

        JButton rightArrow = new JButton(">");
        rightArrow.setBounds(327, 260, 50, 30);
        rightArrow.setFont(Main.AthensClassic24);
        rightArrow.addActionListener(e -> {
            if (currentIndex < facts.length - 1) {
                currentIndex++;
                updateLeftPanel();
            }
        });
        add(rightArrow);

        pageLabel.setBounds(130, 260, 150, 30);
        pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pageLabel.setFont(Main.AthensClassic24);
        add(pageLabel);

        // Preview rectangles
        previewPanel.setBounds(450, 120, 500, 400);
        previewPanel.setLayout(null);
        add(previewPanel);

        // Submit button at bottom right
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(800, 430, 120, 30);
        submitButton.setFont(Main.AthensClassic24);
        submitButton.addActionListener(e -> {
            
            // Validation: all events have a headline, no empty, preview index valid
            if (selectedHeadlines.size() < facts.length || selectedHeadlines.contains("") || selectedPreviewIndex < 0) {
                JOptionPane.showMessageDialog(this, "Please select a headline for all events and choose a main article.", "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            System.out.println("Submitted Headlines:");
            for (int i = 0; i < selectedHeadlines.size(); i++) {
                System.out.println("Event " + (i + 1) + ": " + selectedHeadlines.get(i));
            }
            System.out.println("Chosen headline article: " + selectedHeadlines.get(selectedPreviewIndex));
        });
        add(submitButton);

        updateLeftPanel();
        updatePreview();
    }

    private void updateLeftPanel() {
        factLabel.setText(facts[currentIndex]);
        group.clearSelection();
        for (int i = 0; i < 3; i++) {
            options[i].setText(headlineOptions[currentIndex][i]);
        }
        if (currentIndex < selectedHeadlines.size()) {
            String selected = selectedHeadlines.get(currentIndex);
            for (int i = 0; i < 3; i++) {
                if (headlineOptions[currentIndex][i].equals(selected)) {
                    options[i].setSelected(true);
                }
            }
        }
        pageLabel.setText("Page " + (currentIndex + 1) + " of " + facts.length);
    }

    private void updatePreview() {
        previewPanel.removeAll();
        for (int i = 0; i < 4; i++) {
            JPanel rect = new JPanel();
            rect.setLayout(new BorderLayout());
            rect.setBounds(20, i * 90 + 10, 460, 80);
            rect.setBackground(Color.LIGHT_GRAY);

            final int index = i;
            rect.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedPreviewIndex = index;
                    updatePreview();
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    rect.setBackground(new Color(140, 27, 50));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    rect.setBackground(Color.LIGHT_GRAY);
                }
            });

            if (i == selectedPreviewIndex) {
                rect.setBorder(BorderFactory.createLineBorder(new Color(140, 27, 50), 3));
            } else {
                rect.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            }

            if (i < selectedHeadlines.size()) {
                JLabel label = new JLabel(selectedHeadlines.get(i));
                label.setFont(Main.AthensClassic24);
                rect.add(label, BorderLayout.CENTER);
            }

            previewPanel.add(rect);
        }
        previewPanel.repaint();
        previewPanel.revalidate();
    }

    // To run standalone for testing:
    public static void main(String[] args) {
        JFrame frame = new JFrame("Headline Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setContentPane(new HeadlineChooser());
        frame.setVisible(true);
    }
}