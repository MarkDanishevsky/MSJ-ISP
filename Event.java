import java.util.ArrayList;

public class Event {
    String factualStatement;
    ArrayList<Headline> headlineOptions;
    boolean used = false;

    Event(String factualStatement, ArrayList<Headline> headlineOptions) {
        this.factualStatement = factualStatement;
        this.headlineOptions = headlineOptions;
    }
}