package gym.customers;

/**
 * This interface defines the Observer in the Observer design pattern.
 * Classes implementing this interface will act as observers, receiving updates from a subject.
 */
public interface Observer {
    /**
     * Method to notify the observer of a specific event or message.
     *
     * @param message The message or information being sent to the observer.
     */
    void notify(String message);
}

