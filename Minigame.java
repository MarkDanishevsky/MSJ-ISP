import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Minigame extends JPanel {
    private static final Font TITLE_FONT = Main.AthensClassic24;
    private static final Font HEADLINE_FONT = Main.AthensClassic24;
    private static final Font RESULT_FONT = Main.AthensClassic18;

    private int slider1Value = 1;
    private int slider2Value = 1;
    private int slider3Value = 1;

    private JLabel titleLabel;
    private JLabel headlineLabel0;
    private JLabel headlineLabel1;
    private JLabel headlineLabel2;
    private JLabel slider1ResultLabel;
    private JLabel slider2ResultLabel;
    private JLabel slider3ResultLabel;
    private JButton submitButton;

    private Image backgroundBackdrop;
    private Image backgroundImage;

    private ArrayList<String[]> scenarios;
    private int currentRound = 0;
    private ArrayList<Integer> correctAnswers = new ArrayList<>(Arrays.asList(0, 1, 2)); // bias, fair, fake

    public Minigame(JFrame parentFrame) {
        backgroundBackdrop = new ImageIcon("assets/background.png").getImage();
        backgroundImage = new ImageIcon("assets/minigame_background.png").getImage();

        loadAllScenarios("assets/facts.csv");

        setOpaque(false);
        setLayout(new BorderLayout());

        initHeader();
        initGrid();
        initButtons(parentFrame);

        showScenario(currentRound);

        parentFrame.setContentPane(this);
        parentFrame.revalidate();
    }

    private void loadAllScenarios(String filename) {
        scenarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line.trim());
                }
            }
            for (int i = 0; i + 3 < lines.size(); i += 4) {
                scenarios.add(new String[]{
                    lines.get(i),
                    lines.get(i + 1),
                    lines.get(i + 2),
                    lines.get(i + 3)
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            scenarios = new ArrayList<>();
            scenarios.add(new String[]{
                "No fact found.",
                "Missing headline",
                "Missing headline",
                "Missing headline"
            });
        }
    }

    private void initHeader() {
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(187, 0, 25, 0));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void initGrid() {
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 50, 0));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, -20, 100));

        headlineLabel0 = new JLabel("", SwingConstants.CENTER);
        headlineLabel1 = new JLabel("", SwingConstants.CENTER);
        headlineLabel2 = new JLabel("", SwingConstants.CENTER);
        headlineLabel0.setFont(HEADLINE_FONT);
        headlineLabel1.setFont(HEADLINE_FONT);
        headlineLabel2.setFont(HEADLINE_FONT);

        gridPanel.add(headlineLabel0);
        gridPanel.add(headlineLabel1);
        gridPanel.add(headlineLabel2);

        JSlider slider1 = createThreeChoiceSlider();
        JSlider slider2 = createThreeChoiceSlider();
        JSlider slider3 = createThreeChoiceSlider();
        slider1.addChangeListener(e -> slider1Value = slider1.getValue());
        slider2.addChangeListener(e -> slider2Value = slider2.getValue());
        slider3.addChangeListener(e -> slider3Value = slider3.getValue());

        gridPanel.add(slider1);
        gridPanel.add(slider2);
        gridPanel.add(slider3);

        slider1ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider2ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider3ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider1ResultLabel.setFont(RESULT_FONT);
        slider2ResultLabel.setFont(RESULT_FONT);
        slider3ResultLabel.setFont(RESULT_FONT);

        gridPanel.add(slider1ResultLabel);
        gridPanel.add(slider2ResultLabel);
        gridPanel.add(slider3ResultLabel);

        add(gridPanel, BorderLayout.CENTER);
    }

    private void initButtons(JFrame parentFrame) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(25, 0, 100, 0));

        Color baseColor = Color.decode("#464646");
        Color hoverColor = Color.decode("#990030");
        Border padding = BorderFactory.createEmptyBorder(10, 20, 10, 20);

        submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setFont(Main.AthensClassic18);
        submitButton.setOpaque(true);
        submitButton.setBackground(baseColor);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(baseColor, 3), padding));
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) {
                submitButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(hoverColor, 3), padding));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) {
                submitButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(baseColor, 3), padding));
            }
        });
        submitButton.addActionListener(e -> handleSubmit(parentFrame));

        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.setFont(Main.AthensClassic18);
        closeButton.setOpaque(true);
        closeButton.setBackground(baseColor);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(baseColor, 3), padding));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(hoverColor, 3), padding));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(baseColor, 3), padding));
            }
        });
        closeButton.addActionListener(e -> {
            parentFrame.setContentPane(new Menu());
            parentFrame.revalidate();
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JSlider createThreeChoiceSlider() {
        JSlider slider = new JSlider(0, 2, 1);
        slider.setUI(new CustomSlider(slider));
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Bias"));
        labelTable.put(1, new JLabel("Fair"));
        labelTable.put(2, new JLabel("Fake"));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);
        slider.setOpaque(false);
        return slider;
    }

    private void handleSubmit(JFrame parentFrame) {
        int[] userChoices = { slider1Value, slider2Value, slider3Value };
        JLabel[] resultLabels = { slider1ResultLabel, slider2ResultLabel, slider3ResultLabel };
        String[] categories = { "bias", "fair", "fake" };

        boolean allCorrect = true;
        for (int i = 0; i < 3; i++) {
            if (userChoices[i] == correctAnswers.get(i)) {
                resultLabels[i].setText("Correct (" + categories[correctAnswers.get(i)] + ")");
                resultLabels[i].setForeground(new Color(0, 128, 0));
            } else {
                resultLabels[i].setText("Incorrect. Please try again");
                resultLabels[i].setForeground(Color.RED);
                allCorrect = false;
            }
        }

        if (allCorrect) {
            currentRound++;
            submitButton.setEnabled(false);
            new javax.swing.Timer(1000, evt -> {
                if (currentRound < scenarios.size()) {
                    showScenario(currentRound);
                    submitButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "You’ve completed all " + scenarios.size() + " rounds. Press close to return to the menu.");
                }
            }) {{ setRepeats(false); start(); }};
        }
    }

    private void showScenario(int idx) {
        // shuffle answer order each round
        Collections.shuffle(correctAnswers);

        String[] s = scenarios.get(idx);
        titleLabel.setText(String.format(
            "<html><div style='text-align:center;'>For your given fact, identify the correct category for each headline. There are three rounds.<br>Your fact is: <b>%s</b></div></html>", s[0]
        ));
        // display based on shuffled correctAnswers order
        headlineLabel0.setText(html(s[ correctAnswers.get(0) + 1 ]));
        headlineLabel1.setText(html(s[ correctAnswers.get(1) + 1 ]));
        headlineLabel2.setText(html(s[ correctAnswers.get(2) + 1 ]));

        // reset sliders & results
        slider1ResultLabel.setText(" ");
        slider2ResultLabel.setText(" ");
        slider3ResultLabel.setText(" ");
    }

    private String html(String text) {
        return String.format("<html><div style='text-align:center;'>%s</div></html>", text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundBackdrop, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
