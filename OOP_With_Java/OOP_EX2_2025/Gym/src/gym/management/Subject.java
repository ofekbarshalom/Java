package gym.management;

import gym.management.Sessions.*;

/**
 * Interface representing the Subject in the Observer design pattern.
 * Defines methods to notify observers with various types of updates.
 */
public interface Subject {

    /**
     * Notifies observers with a message.
     *
     * @param message The message to notify observers.
     */
    void notify(String message);

    /**
     * Notifies observers with a message specific to a particular day.
     *
     * @param day     The day related to the notification.
     * @param message The message to notify observers.
     */
    void notify(String day, String message);

    /**
     * Notifies observers with a message related to a specific session.
     *
     * @param session The session associated with the notification.
     * @param message The message to notify observers.
     */
    void notify(Session session, String message);
}
