import java.util.HashMap;
import java.util.Map;

public class EventTracker implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {
        return INSTANCE;
    }

    synchronized public void push(String message) {
        Integer count = tracker.getOrDefault(message, 0);
        tracker.put(message, count + 1);
    }

    synchronized public Boolean has(String message) {
        Integer count = tracker.getOrDefault(message, 0);
        return tracker.containsKey(message) && count > 0;
    }

    synchronized public void handle(String message, EventHandler e) {
        try {
            e.handle();
            this.tracker.put(message, this.tracker.get(message) - 1);
        } catch (NullPointerException n) {
            System.out.println("Currently untracked.");
        }
    }

    public Map<String, Integer> getTracker() {
        return this.tracker;
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}
