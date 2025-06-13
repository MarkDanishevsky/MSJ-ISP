import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainGame extends JPanel {
    private int round = 0;
    private final int TOTAL_ROUNDS = 4;
    static int readers = 100;
    final static ArrayList<Event> events = loadEventsFromCSV("assets/headlines.csv");

    static int change = 0;

    private CardLayout cardLayout = new CardLayout();
    private JPanel screens = new JPanel(cardLayout);

    private JLabel introLabel = new JLabel("<html><center>Choose headlines wisely with the sole goal of boosting readership.</center></html>", SwingConstants.CENTER);
    private JButton startButton = new JButton("Start Game");

    private HeadlineChooser chooser = new HeadlineChooser(getThreeEvents(), round);
    private IndicatorPanel indicatorPanel = new IndicatorPanel();
    private JButton nextButton = new JButton("Next");

    private static BufferedImage loyaltyMeter;

    public static void run() {
        MainGame gamePanel = new MainGame();
        Main.frame.setContentPane(gamePanel);
        Main.frame.setVisible(true);
    }

    private static Event[] getThreeEvents() {
        ArrayList<Event> available = new ArrayList<>();
        for (Event e : events) {
            if (!e.isUsed()) {
                available.add(e);
            }
        }

        Collections.shuffle(available);

        int count = Math.min(4, available.size());
        Event[] selected = new Event[count];
        for (int i = 0; i < count; i++) {
            selected[i] = available.get(i);
            selected[i].used();
        }

        return selected;
    }

    public MainGame() {
        try {
            BufferedImage raw = ImageIO.read(new File("assets/loyalty_meter.png"));
            loyaltyMeter = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = loyaltyMeter.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g.drawImage(raw, 0, 0, 200, 200, null);
            g.dispose();
        } catch (IOException ex) {
            System.out.println("No file assets/loyalty_meter.png");
        }

        setLayout(new BorderLayout());

        // Intro screen
        JPanel introScreen = new JPanel(new BorderLayout());
        introLabel.setFont(Main.AthensClassic24);
        introScreen.add(introLabel, BorderLayout.CENTER);
        startButton.setFont(Main.AthensClassic24);
        startButton.addActionListener(e -> showChooser());
        introScreen.add(startButton, BorderLayout.SOUTH);

        // Chooser screen
        JPanel chooserScreen = new JPanel(new BorderLayout());
        chooserScreen.add(chooser, BorderLayout.CENTER);
        JButton submitButton = new JButton("Submit Headlines");
        submitButton.setFont(Main.AthensClassic24);
        submitButton.addActionListener(e -> showResult());
        chooserScreen.add(submitButton, BorderLayout.SOUTH);

        // Result screen
        JPanel resultScreen = new JPanel(new BorderLayout());
        resultScreen.add(indicatorPanel, BorderLayout.CENTER);
        nextButton.setFont(Main.AthensClassic24);
        nextButton.addActionListener(e -> {
            round++;
            if (round < TOTAL_ROUNDS) {
                chooser = new HeadlineChooser(getThreeEvents(), round); // reset chooser
                screens.add(makeChooserScreen(), "chooser" + round);
                showChooser();
            } else {
                showEnd();
                
            }
        });
        resultScreen.add(nextButton, BorderLayout.SOUTH);

        // Add all screens
        screens.add(introScreen, "intro");
        screens.add(chooserScreen, "chooser0");
        screens.add(resultScreen, "result");

        add(screens, BorderLayout.CENTER);
        cardLayout.show(screens, "intro");
    }

    private JPanel makeChooserScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chooser, BorderLayout.CENTER);
        JButton submitButton = new JButton("Submit Headlines");
        submitButton.setFont(Main.AthensClassic18);
        submitButton.addActionListener(e -> showResult());
        panel.add(submitButton, BorderLayout.SOUTH);
        return panel;
    }

    private void showChooser() {
        cardLayout.show(screens, "chooser" + round);
    }

    private void showResult() {
        int score = calculateScore();
        int oldReaders = readers;
        readers *= score;

        if (readers - oldReaders < 100) {
            showDisconnected();
        } else {
            indicatorPanel.setValue(score);
            cardLayout.show(screens, "result");
        }
    }

    private void showDisconnected() {
        JPanel disconnectScreen = new JPanel(new BorderLayout());
        JLabel disconnectLabel = new JLabel("<html><center>You have been terminated.<br>Readership did not increase sufficiently.<br>Final Readership: " + readers + "<br><br><i>" + getRandomQuote() + "</i></center></html>", SwingConstants.CENTER);
        disconnectLabel.setFont(Main.AthensClassic24);
        disconnectScreen.add(disconnectLabel, BorderLayout.CENTER);

        JButton returnButton = new JButton("Return to Menu");
        returnButton.setFont(Main.AthensClassic24);
        returnButton.addActionListener(e -> {
            Menu menu = new Menu();
            Main.frame.setContentPane(menu);
            Main.frame.revalidate();
        });
        disconnectScreen.add(returnButton, BorderLayout.SOUTH);

        screens.add(disconnectScreen, "disconnect");
        cardLayout.show(screens, "disconnect");
    }

    private void showEnd() {
        String name = JOptionPane.showInputDialog(this, "Enter your name to record your score:", "Enter Name", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            try {
                java.io.FileWriter writer = new java.io.FileWriter("assets/scores.csv", true);
                writer.write(name + "," + readers + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JPanel endScreen = new JPanel(new BorderLayout());
        JLabel endLabel = new JLabel("<html><center>Thanks for playing!<br>We hope you enjoyed influencing public opinion!</center></html>", SwingConstants.CENTER);
        endLabel.setFont(Main.AthensClassic24);
        JLabel finalLabel = new JLabel("<html><center>Final Readership: " + readers + "<br><br><i>" + getRandomQuote() + "</i></center></html>", SwingConstants.CENTER);
        finalLabel.setFont(Main.AthensClassic24);
        JPanel endCenter = new JPanel(new GridLayout(2, 1));
        endCenter.add(endLabel);
        endCenter.add(finalLabel);
        endScreen.add(endCenter, BorderLayout.CENTER);

        JButton returnButton = new JButton("Return to Menu");
        returnButton.setFont(Main.AthensClassic24);
        returnButton.addActionListener(e -> {
            Menu menu = new Menu();
            Main.frame.setContentPane(menu);
            Main.frame.revalidate();
        });
        endScreen.add(returnButton, BorderLayout.SOUTH);

        screens.add(endScreen, "end");
        cardLayout.show(screens, "end");
    }

    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < chooser.selectedHeadlines.size(); i++) {
            int adjusted = chooser.selectedHeadlines.get(i).populatiry - 2;
            score += HeadlineChooser.selectedPreviewIndex != i ? adjusted : 3 * adjusted;
        }
        return Math.max(1, score);
    }

    private static String getRandomQuote() {
        ArrayList<String> quotes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("assets/quotes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    quotes.add(line.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (quotes.isEmpty()) {
            return "No quotes available.";
        }

        Collections.shuffle(quotes);
        return quotes.get(0);
    }

    // Inner class for the indicator screen
    private static class IndicatorPanel extends JPanel {
        private int value = 0;
        private final JLabel readersLabel = new JLabel();
        private final JLabel changeLabel = new JLabel();

        public IndicatorPanel() {
            setLayout(null);
            readersLabel.setFont(Main.AthensClassic24);
            readersLabel.setBounds(10, 10, 300, 30);
            add(readersLabel);
            changeLabel.setFont(Main.AthensClassic18);
            changeLabel.setBounds(10, 50, 400, 30);
            add(changeLabel);
            updateReaders();
        }

        public void setValue(int value) {
            this.value = value;
            updateReaders();
            changeLabel.setText("Change in readership: +" + (readers - (readers / value)) + " — Keep going!");
            repaint();
        }

        public void updateReaders() {
            readersLabel.setText("Readership: " + MainGame.readers);
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

            g2.drawImage(loyaltyMeter, Main.frame.getWidth() / 2 - 100 + 3, Main.frame.getHeight() / 2 - 100 - 18, this);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = 70;
            int angle = -value * 3;

            g2.setColor(Color.LIGHT_GRAY);

            if (value != 0) {
                g2.setColor(value > 0 ? Color.GREEN : Color.RED);
                g2.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 90, angle);

                // Draw lines at arc start and end
                double angleStartRad = Math.toRadians(90);
                double angleEndRad = Math.toRadians(90 + angle);
                int x1 = centerX + (int)(radius * Math.cos(angleStartRad));
                int y1 = centerY - (int)(radius * Math.sin(angleStartRad));
                int x2 = centerX + (int)(radius * Math.cos(angleEndRad));
                int y2 = centerY - (int)(radius * Math.sin(angleEndRad));
                g2.setColor(Color.BLACK);
                g2.drawLine(centerX, centerY, x1, y1);
                g2.drawLine(centerX, centerY, x2, y2);
            }

            double radians = Math.toRadians(90 + angle);
            int endX = centerX + (int)(radius * Math.cos(radians));
            int endY = centerY - (int)(radius * Math.sin(radians));
            g2.setColor(Color.BLACK);
            g2.drawLine(centerX, centerY, endX, endY);
        }
    }

    private static ArrayList<Event> loadEventsFromCSV(String filename) {
        ArrayList<Event> eventList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<String> fields = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                boolean inQuotes = false;
                for (char c : line.toCharArray()) {
                    if (c == '\"') {
                        inQuotes = !inQuotes;
                    } else if (c == ',' && !inQuotes) {
                        fields.add(sb.toString().trim());
                        sb.setLength(0);
                    } else {
                        sb.append(c);
                    }
                }
                fields.add(sb.toString().trim());

                if (fields.size() >= 7) {
                    String fact = fields.get(0);
                    ArrayList<Headline> headlineList = new ArrayList<>(Arrays.asList(
                        new Headline(fields.get(1), Integer.parseInt(fields.get(2))),
                        new Headline(fields.get(3), Integer.parseInt(fields.get(4))),
                        new Headline(fields.get(5), Integer.parseInt(fields.get(6)))
                    ));
                    Collections.shuffle(headlineList);
                    Headline[] headlines = headlineList.toArray(new Headline[0]);

                    eventList.add(new Event(fact, headlines));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventList;
    }
}