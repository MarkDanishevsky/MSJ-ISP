import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainGame extends JPanel {
    private int round = 0;
    private final int TOTAL_ROUNDS = 3;

    private CardLayout cardLayout = new CardLayout();
    private JPanel screens = new JPanel(cardLayout);

    private JLabel introLabel = new JLabel("<html><center>Welcome to the Headline Influence Game!<br>Choose headlines wisely to boost your readership.</center></html>", SwingConstants.CENTER);
    private JButton startButton = new JButton("Start Game");

    private HeadlineChooser chooser = new HeadlineChooser();
    private IndicatorPanel indicatorPanel = new IndicatorPanel();
    private JButton nextButton = new JButton("Next");

    private JLabel endLabel = new JLabel("<html><center>Thanks for playing!<br>We hope you enjoyed influencing public opinion!</center></html>", SwingConstants.CENTER);

    public static void run() {
        MainGame gamePanel = new MainGame();
        Main.frame.setContentPane(gamePanel);
        Main.frame.setVisible(true);
    }

    public MainGame() {
        setLayout(new BorderLayout());

        // Intro screen
        JPanel introScreen = new JPanel(new BorderLayout());
        introLabel.setFont(Main.AthensClassic.deriveFont(24f));
        introScreen.add(introLabel, BorderLayout.CENTER);
        startButton.setFont(Main.AthensClassic);
        startButton.addActionListener(e -> showChooser());
        introScreen.add(startButton, BorderLayout.SOUTH);

        // Chooser screen
        JPanel chooserScreen = new JPanel(new BorderLayout());
        chooserScreen.add(chooser, BorderLayout.CENTER);
        JButton submitButton = new JButton("Submit Headlines");
        submitButton.setFont(Main.AthensClassic);
        submitButton.addActionListener(e -> showResult());
        chooserScreen.add(submitButton, BorderLayout.SOUTH);

        // Result screen
        JPanel resultScreen = new JPanel(new BorderLayout());
        resultScreen.add(indicatorPanel, BorderLayout.CENTER);
        nextButton.setFont(Main.AthensClassic);
        nextButton.addActionListener(e -> {
            round++;
            if (round < TOTAL_ROUNDS) {
                chooser = new HeadlineChooser(); // reset chooser
                screens.add(makeChooserScreen(), "chooser" + round);
                showChooser();
            } else {
                showEnd();
            }
        });
        resultScreen.add(nextButton, BorderLayout.SOUTH);

        // End screen
        JPanel endScreen = new JPanel(new BorderLayout());
        endLabel.setFont(Main.AthensClassic.deriveFont(24f));
        endScreen.add(endLabel, BorderLayout.CENTER);

        // Add all screens
        screens.add(introScreen, "intro");
        screens.add(chooserScreen, "chooser0");
        screens.add(resultScreen, "result");
        screens.add(endScreen, "end");

        add(screens, BorderLayout.CENTER);
        cardLayout.show(screens, "intro");
    }

    private JPanel makeChooserScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chooser, BorderLayout.CENTER);
        JButton submitButton = new JButton("Submit Headlines");
        submitButton.setFont(Main.AthensClassic);
        submitButton.addActionListener(e -> showResult());
        panel.add(submitButton, BorderLayout.SOUTH);
        return panel;
    }

    private void showChooser() {
        cardLayout.show(screens, "chooser" + round);
    }

    private void showResult() {
        int score = calculateScore();
        indicatorPanel.setValue(score);
        cardLayout.show(screens, "result");
    }

    private void showEnd() {
        cardLayout.show(screens, "end");
    }

    private int calculateScore() {
        // Rough scoring example: give +5 or -5 for each selected headline sentiment (simplified)
        int score = 0;
        for (String hl : chooser.selectedHeadlines) {
            if (hl.contains("applauds") || hl.contains("success") || hl.contains("promises")) {
                score += 5;
            } else if (hl.contains("protest") || hl.contains("unrealistic") || hl.contains("critics")) {
                score -= 5;
            }
        }
        return score;
    }

    // Inner class for the indicator screen
    private static class IndicatorPanel extends JPanel {
        private int value = 0;

        public void setValue(int value) {
            this.value = value;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = 100;
            int angle = -value;

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

            if (value != 0) {
                g2.setColor(value > 0 ? Color.GREEN : Color.RED);
                g2.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 90, angle);
            }

            double radians = Math.toRadians(90 + angle);
            int endX = centerX + (int)(radius * Math.cos(radians));
            int endY = centerY - (int)(radius * Math.sin(radians));
            g2.setColor(Color.BLACK);
            g2.drawLine(centerX, centerY, endX, endY);
        }
    }
}