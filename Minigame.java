import java.awt.*;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.border.Border;

public class Minigame extends JPanel {
    private int slider1Value = 0;
    private int slider2Value = 0;
    private int slider3Value = 0;

    // Threshold variables: adjust these values as needed
    private final int threshold1 = 30;  // slider1 must be less than this
    private final int threshold2 = 40;  // slider2 must be less than this
    private final int threshold3 = 60;  // slider3 must be greater than this

    private JLabel slider1ResultLabel;
    private JLabel slider2ResultLabel;
    private JLabel slider3ResultLabel;

    // Background image
    private Image backgroundBackdrop;
    private Image backgroundImage;

    /**
     * Now accepts a parentFrame so that this panel can be swapped into it.
     */
    public Minigame(JFrame parentFrame) {
        // Load the background image (adjust extension if necessary)
        backgroundBackdrop = new ImageIcon("assets/background.png").getImage();
        backgroundImage = new ImageIcon("assets/minigame_background.png").getImage();

        setOpaque(false);
        setLayout(new BorderLayout());

        // ── TIGHT SPACING CONSTANTS ────────────────────────────────────────
        int titleBottomPadding = 0;   // small gap under the title
        int gridPadding    = 100;     // border around the grid
        int gridHGap       = 50;      // horizontal gap between grid cells
        int gridVGap       = 0;       // vertical gap between grid cells
        int extraTopPad    = 50;      // padding above the grid
        int extraBottomPad = 0;       // no extra padding at the bottom of grid
        int buttonTopPadding = 0;     // small gap above the button panel
        // ────────────────────────────────────────────────────────────────────

        // --- Title at the top ---
        JLabel titleLabel = new JLabel(
            "For your given fact, identify the correct category for each headline. Your fact is: ."
        );
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(200, 0, titleBottomPadding, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Create the 3×3 grid (text placeholders, sliders, result labels) ---
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, gridHGap, gridVGap));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(
            extraTopPad,      // top
            gridPadding,      // left
            extraBottomPad,   // bottom
            gridPadding       // right
        ));

        // First row: placeholders for text—replace with your own labels as needed
        gridPanel.add(new JLabel("Text 1", SwingConstants.CENTER));
        gridPanel.add(new JLabel("Text 2", SwingConstants.CENTER));
        gridPanel.add(new JLabel("Text 3", SwingConstants.CENTER));

        JSlider slider1 = createThreeChoiceSlider();
        JSlider slider2 = createThreeChoiceSlider();
        JSlider slider3 = createThreeChoiceSlider();

        // Update internal variables when sliders move
        slider1.addChangeListener(e -> slider1Value = slider1.getValue());
        slider2.addChangeListener(e -> slider2Value = slider2.getValue());
        slider3.addChangeListener(e -> slider3Value = slider3.getValue());

        gridPanel.add(slider1);
        gridPanel.add(slider2);
        gridPanel.add(slider3);

        // Third row: result labels for each slider
        slider1ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider1ResultLabel.setFont(slider1ResultLabel.getFont().deriveFont(Font.PLAIN, 16f));
        slider1ResultLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        slider2ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider2ResultLabel.setFont(slider2ResultLabel.getFont().deriveFont(Font.PLAIN, 16f));
        slider2ResultLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        slider3ResultLabel = new JLabel(" ", SwingConstants.CENTER);
        slider3ResultLabel.setFont(slider3ResultLabel.getFont().deriveFont(Font.PLAIN, 16f));
        slider3ResultLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        gridPanel.add(slider1ResultLabel);
        gridPanel.add(slider2ResultLabel);
        gridPanel.add(slider3ResultLabel);

        add(gridPanel, BorderLayout.CENTER);

        // --- Bottom section: vertical box for Submit and Close buttons ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
            buttonTopPadding, 0, 150, 0
        ));

        // 1) Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> {
            // Debug printing (optional)
            System.out.println("Slider 1: " + slider1Value + " (Threshold: " + threshold1 + ")");
            System.out.println("Slider 2: " + slider2Value + " (Threshold: " + threshold2 + ")");
            System.out.println("Slider 3: " + slider3Value + " (Threshold: " + threshold3 + ")");

            // Evaluate slider1: must be < threshold1
            if (slider1Value < threshold1) {
                slider1ResultLabel.setText("Correct");
                slider1ResultLabel.setForeground(new Color(0, 128, 0)); // dark green
            } else if (slider1Value > threshold1) {
                slider1ResultLabel.setText("Too high (need < " + threshold1 + ")");
                slider1ResultLabel.setForeground(Color.RED);
            } else { // slider1Value == threshold1
                slider1ResultLabel.setText("Equals limit (need < " + threshold1 + ")");
                slider1ResultLabel.setForeground(Color.RED);
            }

            // Evaluate slider2: must be < threshold2
            if (slider2Value < threshold2) {
                slider2ResultLabel.setText("Correct");
                slider2ResultLabel.setForeground(new Color(0, 128, 0));
            } else if (slider2Value > threshold2) {
                slider2ResultLabel.setText("Too high (need < " + threshold2 + ")");
                slider2ResultLabel.setForeground(Color.RED);
            } else { // slider2Value == threshold2
                slider2ResultLabel.setText("Equals limit (need < " + threshold2 + ")");
                slider2ResultLabel.setForeground(Color.RED);
            }

            // Evaluate slider3: must be > threshold3
            if (slider3Value > threshold3) {
                slider3ResultLabel.setText("Correct");
                slider3ResultLabel.setForeground(new Color(0, 128, 0));
            } else if (slider3Value < threshold3) {
                slider3ResultLabel.setText("Too low (need > " + threshold3 + ")");
                slider3ResultLabel.setForeground(Color.RED);
            } else { // slider3Value == threshold3
                slider3ResultLabel.setText("Equals limit (need > " + threshold3 + ")");
                slider3ResultLabel.setForeground(Color.RED);
            }
        });
        submitButton.setOpaque(true);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        Border redLine = BorderFactory.createLineBorder(Color.RED, 3);
        Border padding = BorderFactory.createEmptyBorder(10, 20, 10, 20);
        submitButton.setBorder(BorderFactory.createCompoundBorder(redLine, padding));

        buttonPanel.add(submitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // small gap below Submit

        // 2) Close button
        JButton closeButton = new JButton("Close");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> {
            parentFrame.setContentPane(new Menu()); // Switch back to the menu panel
            parentFrame.revalidate();
        });
        closeButton.setOpaque(true);
        closeButton.setBackground(Color.DARK_GRAY);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        Border closeBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        closeButton.setBorder(BorderFactory.createCompoundBorder(closeBorder, padding));

        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // ── 3) Finally, set this panel on the parent frame ─────────────────
        parentFrame.setContentPane(this);
        parentFrame.revalidate();
    }

    private JSlider createThreeChoiceSlider() {
        JSlider slider = new JSlider(0, 2, 1);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(0, new JLabel("Left"));
        labelTable.put(1, new JLabel("Middle"));
        labelTable.put(2, new JLabel("<html><div style='margin-right:10px;'>Right</div></html>"));
        slider.setLabelTable(labelTable);
        slider.setPaintLabels(true);

        slider.setOpaque(false);
        return slider;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundBackdrop, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
