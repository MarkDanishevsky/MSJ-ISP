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
 * Date: Friday May 23rd, 2025
 * 
 * This is the driver class for our program
 * 
 * Cube made using: https://1j01.github.io/ascii-hypercube/
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Main {
    static Font AthensClassic;
    public static void main(String[] args) throws FontFormatException, IOException {
        AthensClassic = Font.createFont(Font.TRUETYPE_FONT, new java.io.File("assets/fonts/AthensClassic.ttf")).deriveFont(24f);
        new Menu();
    }
}