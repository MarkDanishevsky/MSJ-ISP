import java.awt.*;
import javax.swing.*;

public class Minigame extends JPanel {
    private int slider1Value = 0;
    private int slider2Value = 0;
    private int slider3Value = 0;

    public Minigame() {
        // ── TIGHT SPACING CONSTANTS ────────────────────────────────────────
        int titleBottomPadding = 0;   // small gap under the title
        int gridPadding    = 0;       // minimal border around the grid
        int gridHGap       = 0;       // small horizontal gap between grid cells
        int gridVGap       = 0;       // small vertical gap between grid cells
        int buttonTopPadding = 0;     // small gap above the button
        int extraBottomPad = 50;      // extra padding at the bottom
        int extraTopPad = 50;         // extra padding at the top
        // ────────────────────────────────────────────────────────────────────

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Header");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(200, 0, titleBottomPadding, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- Center section: a 2×3 grid with tighter gaps ---
        JPanel gridPanel = new JPanel(new GridLayout(2, 3, gridHGap, gridVGap));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(
            gridPadding + extraTopPad,  // top
            gridPadding,  // left
            gridPadding + extraBottomPad,  // bottom
            gridPadding   // right
        ));

        // First row: placeholders for text—replace with your own labels later
        gridPanel.add(new JLabel("Text 1", SwingConstants.CENTER));
        gridPanel.add(new JLabel("Text 2", SwingConstants.CENTER));
        gridPanel.add(new JLabel("Text 3", SwingConstants.CENTER));

        // Second row: three sliders
        JSlider slider1 = new JSlider(0, 100, 50);
        JSlider slider2 = new JSlider(0, 100, 50);
        JSlider slider3 = new JSlider(0, 100, 50);

        // (Optional) Smaller tick spacing if you still want ticks
        slider1.setMajorTickSpacing(25);
        slider1.setPaintTicks(true);
        slider1.setPaintLabels(true);

        slider2.setMajorTickSpacing(25);
        slider2.setPaintTicks(true);
        slider2.setPaintLabels(true);

        slider3.setMajorTickSpacing(25);
        slider3.setPaintTicks(true);
        slider3.setPaintLabels(true);

        // Update variables when sliders move
        slider1.addChangeListener(e -> slider1Value = slider1.getValue());
        slider2.addChangeListener(e -> slider2Value = slider2.getValue());
        slider3.addChangeListener(e -> slider3Value = slider3.getValue());

        gridPanel.add(slider1);
        gridPanel.add(slider2);
        gridPanel.add(slider3);

        add(gridPanel, BorderLayout.CENTER);

        // --- Bottom section: submit button centered with minimal gap above ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
            buttonTopPadding, 0, 300, 0
        ));

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            // Process slider values on click
            System.out.println("Slider 1: " + slider1Value);
            System.out.println("Slider 2: " + slider2Value);
            System.out.println("Slider 3: " + slider3Value);
            // TODO: your submission logic here
        });

        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Minigame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Minigame());
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
