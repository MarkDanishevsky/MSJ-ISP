/*
 *  +-------------+
 *  | \      M    | \
 *  |   \         |   \
 *  |    +--------------+
 *  |    |       |      |
 *  | S  |       |      |
 *  |    |      J|      |
 *  + - -| - - - +      |
 *   \   |         \    |
 *     \ |           \  |
 *       +--------------+
 * 
 * MSJ Development Inc. (2025)
 * ISP
 * Client: Ms. Krasteva (ICS4U1, S2)
 * Date: Friday May 30th, 2025
 * 
 * This is the driver class for our program
 * 
 * Cube made using: https://1j01.github.io/ascii-hypercube/
 */

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Main {
    static Font AthensClassic24;
    static Font AthensClassic18;
    static Font AthensClassic26;

    static JFrame frame;
    public static void main(String[] args) throws FontFormatException, IOException {
        AthensClassic24 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(24f);
        AthensClassic18 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(18f);
        AthensClassic18 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(26f);

        frame = new JFrame("1984 AI - MSJ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768 + 28);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SplashScreen splash = new SplashScreen();
        frame.setContentPane(splash);
        frame.setVisible(true); // Show the fully built SplashScreen

        splash.displayImages("assets/MSJ_Logo.png", "assets/MSJ_Logo.png");

        // switch to the Menu after 3 s
        javax.swing.Timer timer = new javax.swing.Timer(1, evt -> {
            Menu menu = new Menu();
            frame.setContentPane(menu);
            frame.revalidate();
        });
        timer.setRepeats(false);
        timer.start();
    }
}