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
 * Date: Monday June 11th, 2025
 * 
 * This is the driver class for our program
 * 
 * Cube made using: https://1j01.github.io/ascii-hypercube/
 */

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Main {
    /** The base font loaded from assets. */
    static Font AthensClassic;

    /** Derived AthensClassic font at 24-point size. */
    static Font AthensClassic24;

    /** Derived AthensClassic font at 18-point size. */
    static Font AthensClassic18;

    /** Derived AthensClassic font at 26-point size. */
    static Font AthensClassic26;

    /** Derived AthensClassic font at 30-point size. */
    static Font AthensClassic30;

    /** The main application window frame. */
    static JFrame frame;
    
    /**
     * The main entry point of the application.
     * Loads custom fonts, displays the splash screen, and sets a timer to show the main menu.
     *
     * @param args command-line arguments (not used)
     * @throws FontFormatException if the font format is invalid
     * @throws IOException if the font file cannot be read
     */
    public static void main(String[] args) throws FontFormatException, IOException {
        AthensClassic = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf"));
        AthensClassic24 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(24f);
        AthensClassic18 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(18f);
        AthensClassic26 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(26f);
        AthensClassic30 = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(30f);

        frame = new JFrame("1984 Headline AI - MSJ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1024, 768 + 28);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SplashScreen splash = new SplashScreen();
        frame.setContentPane(splash);
        frame.setVisible(true); // Show the SplashScreen

        splash.displayImages("assets/MSJ_Logo.png", "assets/MSJ_Logo.png");

        // switch to the Menu after 3 s
        javax.swing.Timer timer = new javax.swing.Timer(3000, evt -> {
            Menu menu = new Menu();
            frame.setContentPane(menu);
            frame.revalidate();
        });
        timer.setRepeats(false);
        timer.start();
    }
}