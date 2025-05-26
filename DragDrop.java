/*
 * MSJ Development Inc. (2025)
 * ISP
 * Client: Ms. Krasteva (ICS4U1, S2)
 * Date: Friday May 23rd, 2025
 * 
 * This is a prototype of our drag and drop funcitonality. It will be used in our game 2.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DragDrop {
    public static void main(String[] args) {
        JFrame frame = new JFrame("DragDrop Demo");
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new RectanglesPanel());
        frame.setVisible(true);
    }

    static class RectanglesPanel extends JPanel implements MouseListener, MouseMotionListener {
        final int rectWidth = 300;
        final int rectHeight = 70;

        ArrayList<Rectangle> leftRects = new ArrayList<>();
        ArrayList<Point> leftRectsOriginalPos = new ArrayList<>();
        ArrayList<Rectangle> rightRects = new ArrayList<>();
        ArrayList<Rectangle> placedRects = new ArrayList<>();
        ArrayList<String[]> placedTexts = new ArrayList<>();

        ArrayList<String[]> leftTexts = new ArrayList<>();
        int currentSet = 0;

        final int COUNT_LEFT = 3;

        int draggedIndex = -1;
        Point dragOffset = new Point();

        JButton leftButton, rightButton;

        public RectanglesPanel() {
            int width = 1024;
            int height = 768;
            int leftWidth = width / 2;
            int rightWidth = width - leftWidth;

            int countLeft = 3;
            int spacing = 40;
            int totalHeight = countLeft * rectHeight + (countLeft - 1) * spacing;
            int topOffset = (height - totalHeight) / 2;

            for (int i = 0; i < countLeft; i++) {
                int x = (leftWidth - rectWidth) / 2;
                int y = topOffset + i * (rectHeight + spacing);
                Rectangle r = new Rectangle(x, y, rectWidth, rectHeight);
                leftRects.add(r);
                leftRectsOriginalPos.add(new Point(x, y));
                leftTexts.add(new String[0]);
            }

            ArrayList<String[]> allTextSets = new ArrayList<>();
            allTextSets.add(new String[] {
                    "First page option 1",
                    "First page option 2",
                    "First page option 3"
            });
            allTextSets.add(new String[] {
                    "Second page option 1",
                    "Second page option 2",
                    "Second page option 3"
            });
            allTextSets.add(new String[] {
                    "Third page option 1",
                    "Third page option 2",
                    "Third page option 3"
            });
            allTextSets.add(new String[] {
                    "Fourth page option 1",
                    "Fourth page option 2",
                    "Fourth page option 3"
            });

            leftTexts.clear();
            for (String text : allTextSets.get(0)) {
                leftTexts.add(wrapText(text, rectWidth - 20));
            }

            int countRight = 4;
            int spacingRight = 30;
            int totalHeightRight = countRight * rectHeight + (countRight - 1) * spacingRight;
            int topOffsetRight = (height - totalHeightRight) / 2;
            for (int i = 0; i < countRight; i++) {
                int x = leftWidth + (rightWidth - rectWidth) / 2;
                int y = topOffsetRight + i * (rectHeight + spacingRight);
                Rectangle r = new Rectangle(x, y, rectWidth, rectHeight);
                rightRects.add(r);
            }

            setLayout(null);

            leftButton = new JButton("<");
            rightButton = new JButton(">");
            int btnWidth = 50, btnHeight = 30;
            int btnY = topOffset + countLeft * (rectHeight + spacing) + 10;
            leftButton.setBounds((leftWidth - rectWidth) / 2, btnY, btnWidth, btnHeight);
            rightButton.setBounds((leftWidth - rectWidth) / 2 + rectWidth - btnWidth, btnY, btnWidth, btnHeight);

            add(leftButton);
            add(rightButton);

            leftButton.addActionListener(e -> {
                currentSet--;
                if (currentSet < 0)
                    currentSet = allTextSets.size() - 1;
                updateLeftTexts(allTextSets.get(currentSet));
            });

            rightButton.addActionListener(e -> {
                currentSet++;
                if (currentSet >= allTextSets.size())
                    currentSet = 0;
                updateLeftTexts(allTextSets.get(currentSet));
            });

            addMouseListener(this);
            addMouseMotionListener(this);
        }

        private void updateLeftTexts(String[] texts) {
            leftTexts.clear();
            for (String t : texts) {
                leftTexts.add(wrapText(t, rectWidth - 20));
            }
            

            for (int i = 0; i < COUNT_LEFT; i++) {
                Point p = leftRectsOriginalPos.get(i);
                leftRects.set(i, new Rectangle(p.x, p.y, rectWidth, rectHeight));
            }

            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.WHITE);
            for (int i = 0; i < leftRects.size(); i++) {
                Rectangle r = leftRects.get(i);
                if (r == null)
                    continue;
                g.drawRect(r.x, r.y, r.width, r.height);
                g.fillRect(r.x + 1, r.y + 1, r.width - 1, r.height - 1);

                g.setColor(Color.BLACK);
                drawWrappedText(g, leftTexts.get(i), r.x + 10, r.y + 20);
                g.setColor(Color.WHITE);
            }

            g.setColor(Color.BLACK);
            for (Rectangle r : rightRects) {
                g.drawRect(r.x, r.y, r.width, r.height);
            }

            g.setColor(Color.WHITE);
            for (int i = 0; i < placedRects.size(); i++) {
                Rectangle r = placedRects.get(i);
                g.drawRect(r.x, r.y, r.width, r.height);
                g.fillRect(r.x + 1, r.y + 1, r.width - 1, r.height - 1);

                g.setColor(Color.BLACK);
                drawWrappedText(g, placedTexts.get(i), r.x + 10, r.y + 20);
                g.setColor(Color.WHITE);
            }
        }

        private void drawWrappedText(Graphics g, String[] lines, int x, int y) {
            FontMetrics fm = g.getFontMetrics();
            int lineHeight = fm.getHeight();
            for (int i = 0; i < lines.length; i++) {
                g.drawString(lines[i], x, y + i * lineHeight);
            }
        }

        private String[] wrapText(String text, int maxWidth) {
            FontMetrics fm = getFontMetrics(getFont());
            String[] words = text.split(" ");
            ArrayList<String> lines = new ArrayList<>();
            StringBuilder line = new StringBuilder();

            for (String word : words) {
                String testLine = line.length() == 0 ? word : line + " " + word;
                int width = fm.stringWidth(testLine);
                if (width > maxWidth) {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                } else {
                    line = new StringBuilder(testLine);
                }
            }
            if (line.length() > 0){
                lines.add(line.toString());
            }

            return lines.toArray(new String[0]);
        }

        public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            for (int i = 0; i < leftRects.size(); i++) {
                Rectangle r = leftRects.get(i);
                if (r != null && r.contains(p)) {
                    draggedIndex = i;
                    dragOffset.x = p.x - r.x;
                    dragOffset.y = p.y - r.y;
                    break;
                }
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (draggedIndex != -1) {
                Point p = e.getPoint();
                Rectangle r = leftRects.get(draggedIndex);
                if (r == null)
                    return;
                r.setLocation(p.x - dragOffset.x, p.y - dragOffset.y);
                repaint();
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (draggedIndex != -1) {
                Rectangle draggedRect = leftRects.get(draggedIndex);
                boolean droppedOnRight = false;
                for (Rectangle rightRect : rightRects) {
                    if (rightRect.contains(draggedRect.getCenterX(), draggedRect.getCenterY())) {
                        Rectangle newRect = new Rectangle(rightRect);
                        placedRects.add(newRect);
                        placedTexts.add(leftTexts.get(draggedIndex));
                        leftRects.set(draggedIndex, null);
                        leftTexts.set(draggedIndex, null);
                        droppedOnRight = true;
                        break;
                    }
                }
                if (!droppedOnRight) {
                    Point original = leftRectsOriginalPos.get(draggedIndex);
                    draggedRect.setLocation(original);
                }
                draggedIndex = -1;
                repaint();
            }
        }
    }
}
