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
 * This is the entry class for our leaderboard.
 * This class stores the player's name and their score for leaderboard ranking.
 */

class Entry {

    /**
     * The name of the player.
     */
    String name;

    /**
     * The player's score.
     */
    int score;

    /**
     * Constructs a new Entry with the specified player name and score.
     *
     * @param n the name of the player
     * @param s the player's score
     */
    Entry(String n, int s) {
        name = n;
        score = s;
    }
}