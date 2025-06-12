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
    private Image speech;
    private Image wspeak;
    private Image mspeak;
    private Image aspeak;

    private int wx;
    private int wy;
    private int mx;
    private int my;
    private int ax;
    private int ay;

    private boolean aimode;
    private boolean showEyeZoom;
    private boolean speaking;
    private boolean wspeaking;
    private boolean aspeaking;
    private boolean mspeaking;
    private boolean isAnimating;
    private int eyeZoomDelay;

    private static final int STATE_WINSTON_AT_DESK = 0;
    private static final int STATE_WINSTON_FINAL_WORDS = 1;
    private static final int STATE_WINSTON_LEAVE = 2;
    private static final int STATE_EYE_ZOOM = 3;
    private static final int STATE_COLLEAGUES_IN = 4;
    private static final int STATE_AMPLEFORTH_QUESTION = 5;
    private static final int STATE_MEURSAULT_WHAT = 6;
    private static final int STATE_AMPLEFORTH_OHNO = 7;
    private static final int STATE_AI_RETURN = 8;
    private static final int STATE_MEURSAULT_BACK = 9;
    private static final int STATE_AMPLE_WRONG = 10;
    private static final int STATE_DONE = 11;

    private int currentState = STATE_WINSTON_AT_DESK;

    private Scanner s;
    private JButton nextButton;
    private JButton menuButton;
    private Timer animationTimer;

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
        speech = new ImageIcon("assets/speech_box.png").getImage();
        wspeak = new ImageIcon("assets/winston_speaking.png").getImage();
        aspeak = new ImageIcon("assets/ampleforth_speaking.png").getImage();
        mspeak = new ImageIcon("assets/meursault_speaking.png").getImage();

        aimode = false;
        showEyeZoom = false;
        isAnimating = false;
        speaking = false;
        wspeaking = false;
        aspeaking = false;
        mspeaking = false;
        eyeZoomDelay = 0;

        wx = 250;
        wy = 360;
        mx = 1200;
        my = 340;
        ax = 1100;
        ay = 340;

        animationTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isAnimating) return;

                switch (currentState) {
                    case STATE_WINSTON_AT_DESK:

                        break;

                    case STATE_WINSTON_FINAL_WORDS:
                        speaking = true;
                        wspeaking = true;
                        nextButton.setEnabled(true);
                        break;

                    case STATE_WINSTON_LEAVE:
                        speaking = false;
                        wspeaking = false;
                        wx -= 5;
                        if (wx <= -400) {
                            currentState = STATE_EYE_ZOOM;
                        }
                        break;

                    case STATE_EYE_ZOOM:
                        showEyeZoom = true;
                        nextButton.setEnabled(true);
                        break;

                    case STATE_COLLEAGUES_IN:
                        showEyeZoom = false;
                        mx -= 3;
                        ax -= 3;
                        if (ax <= 800) {
                            mx += 3;
                            ax += 3;
                            nextButton.setEnabled(true);
                        }
                        break;

                    case STATE_AMPLEFORTH_QUESTION:
                        speaking = true;
                        aspeaking = true;
                        nextButton.setEnabled(true);
                        break;

                    case STATE_MEURSAULT_WHAT:
                        aspeaking = false;
                        mspeaking = true;
                        nextButton.setEnabled(true);
                        break;

                    case STATE_AMPLEFORTH_OHNO:
                        mspeaking = false;
                        aspeaking = true;
                        nextButton.setEnabled(true);
                        wx = 1100;
                        break;

                    case STATE_AI_RETURN:
                        aimode = true;
                        aspeaking = false;
                        speaking = false;
                        wx -= 5;
                        if (wx <= 250) {
                            wx = 250;
                            nextButton.setEnabled(true);
                        }
                        break;

                    case STATE_MEURSAULT_BACK:
                        mspeaking = true;
                        speaking = true;
                        nextButton.setEnabled(true);
                        break;

                    case STATE_AMPLE_WRONG:
                        aspeaking = true;
                        mspeaking = false;
                        nextButton.setEnabled(true);
                        break;
                    case STATE_DONE:
                        parentFrame.setContentPane(new Menu());
                        parentFrame.revalidate();
                        break;
                }
                repaint();
            }
        });

        nextButton = new JButton("Next");
        menuButton = new JButton("Menu");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 20, 10));
        buttonPanel.add(nextButton);
        buttonPanel.add(menuButton);
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(buttonPanel, BorderLayout.SOUTH);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                advanceStory();
            }
        });
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentFrame.setContentPane(new Menu());
                parentFrame.revalidate();
            }
        });
    }

    private void advanceStory() {
        nextButton.setEnabled(false); // Disable button during animation
        isAnimating = true;

        currentState++;

        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
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
        if (speaking) {
            g.drawImage(speech, 0, 0, getWidth(), getHeight(), this);
        }
        if (wspeaking) {
            g.drawImage(wspeak, 0, 0, getWidth(), getHeight(), this);
        }
        if (aspeaking) {
            g.drawImage(aspeak, 0, 0, getWidth(), getHeight(), this);
        }
        if (mspeaking) {
            g.drawImage(mspeak, 0, 0, getWidth(), getHeight(), this);
        }
        if (speaking) {
            g.drawImage(speech, 0, 0, getWidth(), getHeight(), this);
        }
        g.setColor(Color.BLACK);
        g.setFont(Main.AthensClassic30);
        String message = "";
        if (currentState == STATE_WINSTON_FINAL_WORDS) {
            message = "I must be wary. There are microphones and cameras everywhere.";
            g.drawString("I cannot let them discover my allegiance to the rebellion.", 40, getHeight() - 40);
        }
        if (currentState == STATE_AMPLEFORTH_QUESTION) {
            message = "Where has Winston been? He hasn’t shown up in a few days.";
        }
        if (currentState == STATE_MEURSAULT_WHAT) {
            message = "What if he’s…";
        }
        if (currentState == STATE_AMPLEFORTH_OHNO) {
            message = "You don’t mean…";
        }
        if (currentState == STATE_MEURSAULT_BACK) {
            message = "Oh! He’s back!";
        }
        if (currentState == STATE_AMPLE_WRONG ) {
            message = "But something’s wrong… he looks different!";
        }
        g.drawString(message, 40, getHeight() - 70);
    }
}
