import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Minigame extends JPanel {
    private int slider1Value = 0;
    private int slider2Value = 0;
    private int slider3Value = 0;

    // Threshold variables: adjust these values as needed
    private final int threshold1 = 30;  // slider1 must be less than this
    private final int threshold2 = 40;  // slider2 must be less than this
    private final int threshold3 = 60;  // slider3 must be greater than this

    // Labels to display per-slider feedback
    private JLabel slider1ResultLabel;
    private JLabel slider2ResultLabel;
    private JLabel slider3ResultLabel;

    public Minigame() {
        // ── TIGHT SPACING CONSTANTS ────────────────────────────────────────
        int titleBottomPadding = 0;   // small gap under the title
        int gridPadding    = 0;       // minimal border around the grid
        int gridHGap       = 0;       // small horizontal gap between grid cells
        int gridVGap       = 0;       // small vertical gap between grid cells
        int extraTopPad    = 50;      // padding above the grid
        int extraBottomPad = 0;       // no extra padding at the bottom of grid
        int buttonTopPadding = 0;     // small gap above the button
        // ────────────────────────────────────────────────────────────────────

        setLayout(new BorderLayout());

        // --- Title at the top ---
        JLabel titleLabel = new JLabel("Header");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(200, 0, titleBottomPadding, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Create the 3×3 grid (text placeholders, sliders, result labels) ---
        JPanel gridPanel = new JPanel(new GridLayout(3, 3, gridHGap, gridVGap));
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

        // Second row: three sliders
        JSlider slider1 = new JSlider(0, 100, 50);
        JSlider slider2 = new JSlider(0, 100, 50);
        JSlider slider3 = new JSlider(0, 100, 50);

        // Optional: tick spacing if you want visible ticks/labels
        slider1.setMajorTickSpacing(25);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        slider2.setMajorTickSpacing(25);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);

        slider3.setMajorTickSpacing(25);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);

        // Update internal variables when sliders move
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slider1Value = slider1.getValue();
            }
        });
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slider2Value = slider2.getValue();
            }
        });
        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                slider3Value = slider3.getValue();
            }
        });

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

        // --- Bottom section: submit button centered with minimal gap above ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
            buttonTopPadding, 0, 300, 0
        ));

        JButton submitButton = new JButton("Submit");
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

        // Create a red outline (3px)…
        Border redLine = BorderFactory.createLineBorder(Color.RED, 3);

        // …and then an inner empty border to simulate padding:
        int top = 10, left = 20, bottom = 10, right = 20;
        Border padding = BorderFactory.createEmptyBorder(top, left, bottom, right);

        // Combine them (outer = red line, inner = padding)
        submitButton.setBorder(BorderFactory.createCompoundBorder(redLine, padding));
        

        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {    
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Minigame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new Minigame());
            frame.setSize(1024, 768);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
