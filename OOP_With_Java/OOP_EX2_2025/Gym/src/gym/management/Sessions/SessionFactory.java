package gym.management.Sessions;

import gym.management.*;

// Factory class to create different types of sessions based on input parameters.
public class SessionFactory {

    /**
     * Creates a session based on the specified session type, date, forum type, and instructor.
     *
     * @param sessionType The type of session to create (e.g., Ninja, MachinePilates, ThaiBoxing, Pilates).
     * @param date        The date and time for the session.
     * @param forumType   The forum type for the session (e.g., Age-based, Gender-based).
     * @param instructor  The instructor assigned to lead the session.
     * @return An instance of the appropriate session type, or null if the session type is invalid.
     */
    public static Session createSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) {
        if (sessionType == null) {
            return null; // Return null if no session type is provided
        }

        // Create and return the appropriate session based on the session type
        return switch (sessionType.getName()) {
            case "Ninja" -> new NinjaSession(date, forumType, instructor);
            case "MachinePilates" -> new MachinePilatesSession(date, forumType, instructor);
            case "ThaiBoxing" -> new ThaiBoxingSession(date, forumType, instructor);
            case "Pilates" -> new PilatesSession(date, forumType, instructor);
            default -> null; // Return null for unknown session types
        };
    }
}