import java.util.ArrayList;

public class Event {
    String factualStatement;
    Headline[] headlineOptions;
    boolean used = false;

    Event(String factualStatement, Headline[] headlineOptions) {
        this.factualStatement = factualStatement;
        this.headlineOptions = headlineOptions;
    }
}