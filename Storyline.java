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
 * Date: Friday May 30th, 2025
 * 
 * This is the story level for our game.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Storyline extends JPanel {

    private Image office;
    private Image winston;
    private Image AIwinston;
    private Image meursault;
    private Image ampleforth;
    private Image computer;
    private Image eyezoom;

    private int wx;
    private int wy;

    private int mx;
    private int my;
    private int ax;
    private int ay;

    private boolean aimode;
    private boolean showEyeZoom;

    private static final int STATE_WINSTON_AT_DESK = 0;
    private static final int STATE_WINSTON_LEAVE = 1;
    private static final int STATE_EYE_ZOOM = 2;
    private static final int STATE_COLLEAGUES_IN = 3;
    private static final int STATE_WINSTON_IN = 4;
    private static final int STATE_COLLEAGUES_OUT = 5;
    private int currentState = STATE_WINSTON_AT_DESK;
    private int delayCounter = 0;

    private Scanner s;

    public Storyline(JFrame parentFrame) {

        setLayout(new BorderLayout());
        s = new Scanner(System.in);
        office = new ImageIcon("assets/office.png").getImage();
        winston = new ImageIcon("assets/winston.png").getImage();
        AIwinston = new ImageIcon("assets/AI_winston.png").getImage();
        meursault = new ImageIcon("assets/meursault.png").getImage();
        ampleforth = new ImageIcon("assets/ampleforth.png").getImage();
        computer = new ImageIcon("assets/computer.png").getImage();
        eyezoom = new ImageIcon("assets/eyezoom.png").getImage();
        aimode = false;
        showEyeZoom = false;

        wx = 250;
        wy = 360;
        mx = 1200;
        my = 340;
        ax = 1100;
        ay = 340;

        Timer masterTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (currentState) {
                    case STATE_WINSTON_AT_DESK:
                        wx = 250;  // Set Winston at desk
                        wy = 360;
                        if (delayCounter++ >= 30) {
                            currentState = STATE_WINSTON_LEAVE;
                            delayCounter = 0;
                        }
                        break;

                    case STATE_WINSTON_LEAVE:
                        wx -= 5;
                        if (wx <= -300) {
                            showEyeZoom = true;
                            currentState = STATE_EYE_ZOOM;
                            delayCounter = 0;
                        }
                        break;

                    case STATE_EYE_ZOOM:
                        if (delayCounter++ >= 100) {
                            showEyeZoom = false;
                            delayCounter = 0;
                            currentState = STATE_COLLEAGUES_IN;
                        }
                        break;

                    case STATE_COLLEAGUES_IN:
                        mx -= 3;
                        ax -= 3;
                        if (ax <= 700) {
                            aimode = true;
                            wx = 1300;
                            currentState = STATE_WINSTON_IN;
                        }
                        break;

                    case STATE_WINSTON_IN:
                        wx -= 3;
                        if (wx <= 250) {
                            delayCounter = 0;
                            currentState = STATE_COLLEAGUES_OUT;
                        }
                        break;

                    case STATE_COLLEAGUES_OUT:
                        if (delayCounter++ >= 45) {
                            mx += 4;
                            ax += 4;
                            if (ax >= 1200) {
                                ((Timer)e.getSource()).stop();
                            }
                        }
                        break;
                }
                repaint();
            }
        });
        masterTimer.start();


        JLabel instructionsLabel = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Story",
                SwingConstants.CENTER
        );
        instructionsLabel.setFont(Main.AthensClassic18);
        add(instructionsLabel, BorderLayout.CENTER);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(office, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(winston, wx, wy, 88, 272, this);
        g.drawImage(meursault, mx, my, 128, 272, this);
        g.drawImage(ampleforth, ax, ay, 128, 272, this);
        if (aimode) {
            g.drawImage(AIwinston, wx, wy, 88, 272, this);
        }
        g.drawImage(computer, 0, 50, getWidth(), getHeight(), this);
        if (showEyeZoom) {
            g.drawImage(eyezoom, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
