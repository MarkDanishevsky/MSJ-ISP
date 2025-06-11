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
 import java.awt.image.BufferedImage;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.ArrayList;
 import java.util.List;
 import javax.imageio.ImageIO;
 import javax.swing.*;
 
 public class Leaderboard extends JPanel {
     private static final Font FONT = Main.AthensClassic18;
     private static final int MAX_ENTRIES = 8;
     private static final int VERTICAL_GAP = 33; // adjust spacing here
     private BufferedImage backgroundImage;
 
     public Leaderboard(JFrame parentFrame) {
         // Load background image
         try {
             backgroundImage = ImageIO.read(new File("assets/table_background.png"));
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         setLayout(new BorderLayout());
 
         // Prepare title + left header
         int titleTopPadding = 146; 
         Font titleFont = FONT.deriveFont(42f);
         JLabel title = new JLabel("Most Valuable Employees", SwingConstants.CENTER);
         title.setFont(titleFont);
         title.setForeground(Color.BLACK);
 
         JLabel leftHeading = new JLabel("             Revenue", SwingConstants.LEFT);
         leftHeading.setFont(Main.AthensClassic26);
         leftHeading.setForeground(Color.BLACK);
 
         JPanel headerPanel = new JPanel(new BorderLayout());
         headerPanel.setOpaque(false);
         headerPanel.setBorder(BorderFactory.createEmptyBorder(titleTopPadding, 40, 0, 50));
         headerPanel.add(leftHeading, BorderLayout.WEST);
         headerPanel.add(title, BorderLayout.CENTER);
         add(headerPanel, BorderLayout.NORTH);
 
         // Read and sort entries
         List<Entry> entries = loadEntries("assets/scores.csv");
         selectionSortDescending(entries);
 
         // Panel for manual labels
         JPanel listPanel = new JPanel();
         listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
         listPanel.setOpaque(false);
         listPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 30, 50));
 
         int count = Math.min(entries.size(), MAX_ENTRIES);
         for (int i = 0; i < count; i++) {
             Entry e = entries.get(i);
             JLabel label = new JLabel(
                 String.format("               %-50s  %s", e.score, e.name)
             );
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
         add(scrollPane, BorderLayout.CENTER);
 
         // Buttons panel
         JButton back = new JButton("Back to Menu");
         back.setBackground(Color.WHITE);
         back.setFont(FONT);
         back.addActionListener(e -> {
             parentFrame.setContentPane(new Menu());
             parentFrame.revalidate();
         });
 
         JButton clear = new JButton("Clear Leaderboard");
         clear.setBackground(Color.WHITE);
         clear.setFont(FONT);
         clear.addActionListener(e -> {
             int choice = JOptionPane.showConfirmDialog(
                 this,
                 "Are you sure you want to clear the leaderboard?",
                 "Confirm Clear",
                 JOptionPane.YES_NO_OPTION,
                 JOptionPane.WARNING_MESSAGE
             );
             if (choice == JOptionPane.YES_OPTION) {
                 // Wipe the scores file
                 try (PrintWriter pw = new PrintWriter(new FileWriter("assets/scores.csv"))) {
                     // empty on purpose
                 } catch (IOException ex) {
                     JOptionPane.showMessageDialog(
                         this,
                         "Error clearing scores: " + ex.getMessage(),
                         "File Error",
                         JOptionPane.ERROR_MESSAGE
                     );
                     return;
                 }
                 // Refresh UI
                 parentFrame.setContentPane(new Leaderboard(parentFrame));
                 parentFrame.revalidate();
             }
         });
 
         JPanel btnPanel = new JPanel();
         btnPanel.add(back);
         btnPanel.add(clear);
         add(btnPanel, BorderLayout.SOUTH);
     }
 
     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         if (backgroundImage != null) {
             g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
         }
     }
 
     private List<Entry> loadEntries(String filePath) {
         List<Entry> list = new ArrayList<>();
         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String[] parts = line.split(",");
                 if (parts.length >= 2) {
                     list.add(new Entry(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                 }
             }
         } catch (IOException | NumberFormatException ex) {
             JOptionPane.showMessageDialog(
                 this,
                 "Error reading scores: " + ex.getMessage(),
                 "File Error",
                 JOptionPane.ERROR_MESSAGE
             );
         }
         return list;
     }
 
     // Selection sort descending by score
     private void selectionSortDescending(List<Entry> list) {
         for (int i = 0; i < list.size() - 1; i++) {
             int maxIdx = i;
             for (int j = i + 1; j < list.size(); j++) {
                 if (list.get(j).score > list.get(maxIdx).score) {
                     maxIdx = j;
                 }
             }
             Entry temp = list.get(i);
             list.set(i, list.get(maxIdx));
             list.set(maxIdx, temp);
         }
     }
 }
 
