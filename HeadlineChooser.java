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

public class HeadlineChooser extends JPanel {
    /** The background image used for the main gameplay screen. */
    private Image backgroundImage = new ImageIcon("assets/maingame_background.png").getImage();

    /** Index of the currently selected main headline article (0–3), or -1 if none is selected. */
    static int selectedPreviewIndex = -1;

    /** Array of date strings used to display different fictional publication days. */
    String[] dates = {"21st", "22nd", "23rd"};

    /** Stores the player's selected headlines, one per news event. */
    final ArrayList<Headline> selectedHeadlines = new ArrayList<>();

    /** Tracks which news event the player is currently editing/selecting a headline for. */
    private int currentIndex = 0;

    /** Label for displaying the factual statement of the current event. */
    private final JLabel factLabel = new JLabel();

    /** Radio buttons for headline selection options. */
    private final JRadioButton[] options = new JRadioButton[3];

    /** Groups radio buttons together so only one can be selected at a time. */
    private final ButtonGroup group = new ButtonGroup();

    /** Panel that previews selected headlines and allows choosing the main article. */
    private final JPanel previewPanel = new JPanel();

    /** Displays the current page number (e.g., Page 2 of 4). */
    private final JLabel pageLabel = new JLabel();

    /** Displays the current number of readers in a bordered label. */
    private final JLabel readersLabel = new JLabel();

    /** Shows a status message for the preview section ("Select a headline article", etc.). */
    private final JLabel previewStatus = new JLabel();

    /**
     * Constructs the headline chooser panel where players choose headlines for four events
     * and designate one as the main article.
     *
     * @param eventsToBeUsed the list of 4 events to display
     * @param dateNum the index of the current date to display
     */
    public HeadlineChooser(Event[] eventsToBeUsed, int dateNum) {
        ImageIcon unselectedIcon = new ImageIcon("assets/bird/pixil-frame-0.png");
        ImageIcon selectedIcon = new ImageIcon("assets/bird/pixil-frame-5.png");
        JLabel title = new JLabel("The Oceania Times", SwingConstants.CENTER);
        title.setFont(Main.AthensClassic.deriveFont(45f));
        title.setBounds(250, 40, 500, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title);

        JLabel date = new JLabel("June " + dates[dateNum] + ", 1984", SwingConstants.CENTER);
        dateNum++;
        date.setFont(Main.AthensClassic24);
        date.setBounds(250, 80, 500, 20);
        date.setHorizontalAlignment(SwingConstants.CENTER);
        add(date);

        setLayout(null); // manual positioning

        // Fact Label
        factLabel.setBounds(30, 150, 400, 40);
        factLabel.setFont(Main.AthensClassic24);
        add(factLabel);

        JLabel headlineOptionsLabel = new JLabel("Possible Headline Options");
        headlineOptionsLabel.setBounds(30, 190, 400, 30);
        headlineOptionsLabel.setFont(Main.AthensClassic24);
        add(headlineOptionsLabel);

        // Headline options
        for (int i = 0; i < 3; i++) {
            options[i] = new JRadioButton();
            options[i].setVerticalAlignment(SwingConstants.TOP);
            options[i].setBounds(30, 220 + i * 60, 400, 50);
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
                    selectedHeadlines.add(null);
                }
                selectedHeadlines.set(currentIndex, eventsToBeUsed[currentIndex].headlineOptions[finalI]);
                for (int j = 0; j < 3; j++) {
                    options[j].setForeground(j == finalI ? new Color(140, 27, 50) : Color.BLACK);
                }
                updatePreview();
            });
            group.add(options[i]);
            add(options[i]);
        }

        JLabel previewTitle = new JLabel("Newspaper Render");
        previewTitle.setBounds(450, 145, 500, 30);
        previewTitle.setFont(Main.AthensClassic24);
        add(previewTitle);

        previewStatus.setBounds(450, 190, 500, 20);
        previewStatus.setFont(Main.AthensClassic18);
        add(previewStatus);

        // Arrows
        JButton leftArrow = new JButton("<");
        leftArrow.setBounds(30, 460, 50, 30);
        leftArrow.setFont(Main.AthensClassic24);
        leftArrow.addActionListener(e -> {
            if (currentIndex > 0) {
                currentIndex--;
                updateLeftPanel(eventsToBeUsed);
            }
        });
        add(leftArrow);

        JButton rightArrow = new JButton(">");
        rightArrow.setBounds(327, 460, 50, 30);
        rightArrow.setFont(Main.AthensClassic24);
        rightArrow.addActionListener(e -> {
            if (currentIndex < eventsToBeUsed.length - 1) {
                currentIndex++;
                updateLeftPanel(eventsToBeUsed);
            }
        });
        add(rightArrow);

        pageLabel.setBounds(130, 460, 150, 30);
        pageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pageLabel.setFont(Main.AthensClassic24);
        add(pageLabel);

        // Preview rectangles
        previewPanel.setBounds(450, 210, 500, 400);
        previewPanel.setLayout(null);
        previewPanel.setOpaque(false);
        add(previewPanel);

        updateLeftPanel(eventsToBeUsed);
        updatePreview();
        // Mark the text red immediately when selected
        if (currentIndex < selectedHeadlines.size()) {
            String selected = selectedHeadlines.get(currentIndex).content;
            if (selected != null && !selected.isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    if (eventsToBeUsed[currentIndex].headlineOptions[i].content.equals(selected)) {
                        options[i].setForeground(new Color(140, 27, 50));
                    } else {
                        options[i].setForeground(Color.BLACK);
                    }
                }
            }
        }

        // Readers label setup
        readersLabel.setBounds(800, 620, 160, 30);
        readersLabel.setFont(Main.AthensClassic24);
        readersLabel.setOpaque(true);
        readersLabel.setBackground(Color.WHITE);
        readersLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        readersLabel.setHorizontalAlignment(SwingConstants.CENTER);
        readersLabel.setText("Readers: " + MainGame.readers);
        add(readersLabel);
    }

    /**
     * Updates the left panel with a new factual statement and its associated headline options.
     *
     * @param eventsToBeUsed the full list of events used in this session
     */
    private void updateLeftPanel(Event[] eventsToBeUsed) {
        factLabel.setText("FACT: " + eventsToBeUsed[currentIndex].factualStatement);
        group.clearSelection();
        // Ensure radio buttons are reset and redrawn
        for (int i = 0; i < 3; i++) {
            options[i].setSelected(false);
            options[i].setForeground(Color.BLACK);
            options[i].setText("<html><body style='width:360px'>" + eventsToBeUsed[currentIndex].headlineOptions[i].content + "</body></html>");
            options[i].revalidate();
            options[i].repaint();
        }
        if (currentIndex < selectedHeadlines.size()) {
            String selected = selectedHeadlines.get(currentIndex).content;
            if (selected != null && !selected.isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    if (eventsToBeUsed[currentIndex].headlineOptions[i].content.equals(selected)) {
                        options[i].setSelected(true);
                        options[i].setForeground(new Color(140, 27, 50));
                    } else {
                        options[i].setForeground(Color.BLACK);
                    }
                }
            }
        }
        
        pageLabel.setText("Page " + (currentIndex + 1) + " of 4");
    }

    /**
     * Redraws the right preview panel showing the four selected headlines.
     * Allows the user to visually pick which one will be the main headline article.
     */
    private void updatePreview() {
        previewPanel.removeAll();
        for (int i = 0; i < 4; i++) {
            JPanel rect = new JPanel();
            rect.setLayout(new BorderLayout());
            rect.setBounds(20, i * 90 + 10, 460, 80);
            rect.setBackground(new Color(211, 211, 211, 180)); // light gray with some transparency
            rect.setOpaque(false);

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
                    rect.setBackground(new Color(211, 211, 211, 180));
                }
            });

            if (i == selectedPreviewIndex) {
                rect.setBorder(BorderFactory.createLineBorder(new Color(140, 27, 50), 3));
            } else {
                rect.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
            }

            if (i < selectedHeadlines.size()) {
                JLabel label = new JLabel(selectedHeadlines.get(i).content);
                label.setFont(Main.AthensClassic24);
                rect.add(label, BorderLayout.CENTER);
            }

            previewPanel.add(rect);
        }
        previewStatus.setText(selectedPreviewIndex >= 0 ? "Headline article outlined" : "Select a headline article");
        previewPanel.repaint();
        previewPanel.revalidate();
    }

    /**
     * Updates the reader count label at the bottom of the screen.
     */
    public void updateReadersCount() {
        readersLabel.setText("Readers: " + MainGame.readers);
    }

    /**
     * Attaches logic to the submit button ensuring that all four headlines and one main article are selected.
     *
     * @param submitButton the button the user clicks to proceed
     * @param eventsToBeUsed the list of events for validation
     */
    public void addSubmitLogicTo(JButton submitButton, Event[] eventsToBeUsed) {
        submitButton.addActionListener(e -> {
            // Ensure that selectedHeadlines has exactly 4 non-empty, non-null entries
            if (selectedHeadlines.size() < 4) {
                JOptionPane.showMessageDialog(this,
                    "Please select one headline for each of the 4 events.",
                    "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (selectedHeadlines.get(i) == null || selectedHeadlines.get(i).content.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Please select one headline for each of the 4 events.",
                        "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            if (selectedPreviewIndex < 0 || selectedPreviewIndex >= selectedHeadlines.size()) {
                JOptionPane.showMessageDialog(this,
                    "Please select a main article.",
                    "Incomplete Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }

            System.out.println("Submitted Headlines:");
            for (int i = 0; i < selectedHeadlines.size(); i++) {
                System.out.println("Event " + (i + 1) + ": " + selectedHeadlines.get(i));
            }
            System.out.println("Chosen headline article: " + selectedHeadlines.get(selectedPreviewIndex));
        });
    }

    /**
     * Draws the background image behind the panel contents.
     *
     * @param g the Graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}