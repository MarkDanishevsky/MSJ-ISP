import java.util.ArrayList;

public class Event {
    String factualStatement;
    ArrayList<Headline> headlineOptions;

    Event(String factualStatement, ArrayList<Headline> headlineOptions) {
        this.factualStatement = factualStatement;
        this.headlineOptions = headlineOptions;
    }
}