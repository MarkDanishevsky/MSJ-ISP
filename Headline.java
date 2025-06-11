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
 * This is the headline class for our game.
 * It represents a headline with content and its popularity score.
 */

public class Headline {

    /**
     * The text content of the headline.
     */
    String content;

    /**
     * The popularity score of the headline.
     */
    int populatiry;

    /**
     * Constructs a Headline with the specified content and popularity.
     *
     * @param content    the text content of the headline
     * @param populatiry the popularity score of the headline
     */
    Headline(String content, int populatiry) {
        this.content = content;
        this.populatiry = populatiry;
    }
}
