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
 * Date: Tuesday June 3rd, 2025
 * 
 * This is the leaderboard for our game. It includes selection sort and sequential search
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Leaderboard extends JPanel {
    private static final Font FONT = Main.AthensClassic18;
    private static final int MAX_ENTRIES = 8;
    private static final int VERTICAL_GAP = 33; // adjust spacing here
    private BufferedImage backgroundImage;
    private final ArrayList<Entry> entries;

    public Leaderboard(JFrame parentFrame) {
        setLayout(new BorderLayout());


        try {
            backgroundImage = ImageIO.read(new File("assets/table_background.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Font titleFont = FONT.deriveFont(36f);
        JLabel title = new JLabel("Most Valuable Employees", SwingConstants.CENTER);
        title.setFont(titleFont);
        title.setForeground(Color.BLACK);

        JLabel leftHeading = new JLabel("            Revenue", SwingConstants.LEFT);
        leftHeading.setFont(Main.AthensClassic26);
        leftHeading.setForeground(Color.BLACK);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(140, 35, 0, 50));
        headerPanel.add(leftHeading, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);

        entries = loadEntries("assets/scores.csv");
        sortData(entries);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);
        listPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        int count = Math.min(entries.size(), MAX_ENTRIES);
        for (int i = 0; i < count; i++) {
            Entry e = entries.get(i);
            JLabel label = new JLabel(String.format("%10d.     %-38d  %s", i+1, e.score, e.name));
            label.setFont(Main.AthensClassic26);
            label.setForeground(Color.BLACK);
            listPanel.add(label);
            listPanel.add(Box.createRigidArea(new Dimension(0, VERTICAL_GAP)));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JButton back = new JButton("Back to Menu");
        back.setFont(FONT);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setContentPane(new Menu());
                parentFrame.revalidate();
            }
        });

        JButton clear = new JButton("Clear Leaderboard");
        clear.setFont(FONT);
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    Leaderboard.this,
                    "Are you sure you want to clear the leaderboard?",
                    "Confirm Clear",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    try (PrintWriter pw = new PrintWriter(new FileWriter("assets/scores.csv"))) {
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                            Leaderboard.this,
                            "Error clearing scores: " + ex.getMessage(),
                            "File Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    parentFrame.setContentPane(new Leaderboard(parentFrame));
                    parentFrame.revalidate();
                }
            }
        });

        JTextField searchField = new JTextField(20);
        searchField.setFont(FONT);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(FONT);

        // individual listener for Search
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                for (Entry en : entries) {
                    if (en.name.equals(query)) {
                        JOptionPane.showMessageDialog(
                                Leaderboard.this,
                                en.name + "'s score: " + en.score,
                                "Search Result",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                JOptionPane.showMessageDialog(
                        Leaderboard.this,
                        "Worker not found.",
                        "Search Result",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        JLabel findUser = new JLabel("Find Worker:");
        findUser.setForeground(Color.WHITE);
        findUser.setFont(Main.AthensClassic24);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(back);
        buttonPanel.add(clear);
        buttonPanel.add(findUser);
        buttonPanel.add(searchField);
        buttonPanel.add(searchButton);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane,  BorderLayout.CENTER);

        // add to main layout
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel,     BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private ArrayList<Entry> loadEntries(String filePath) {
        ArrayList<Entry> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    // parts[0] is name, parts[1] is score
                    list.add(new Entry(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void sortData(ArrayList<Entry> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).score > list.get(maxIdx).score) {
                    maxIdx = j;
                }
            }
            Entry tmp = list.get(i);
            list.set(i, list.get(maxIdx));
            list.set(maxIdx, tmp);
        }
    }
}
