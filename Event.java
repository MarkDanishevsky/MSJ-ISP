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
 * This is our event class for our game.
 * Each event contains a factual statement and an array of possible headlines related to that fact.
 * It also tracks whether the event has been used.
 */

public class Event {

    /**
     * The factual statement describing the event.
     */
    String factualStatement;

    /**
     * The array of headline options associated with the event.
     */
    Headline[] headlineOptions;

    /**
     * Indicates whether the event has already been used.
     */
    boolean used = false;

    /**
     * Constructs a new Event with the specified factual statement and headline
     * options.
     *
     * @param factualStatement the factual statement of the event
     * @param headlineOptions  the array of headline options for this event
     */
    Event(String factualStatement, Headline[] headlineOptions) {
        this.factualStatement = factualStatement;
        this.headlineOptions = headlineOptions;
    }

    public void used() {
        used = false;
    }

    public boolean isUsed() {
        return used;
    }
}