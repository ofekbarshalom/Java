package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Instructor;

import java.util.List;

// Represents a gym session with core functionalities.
public interface Session {

    /**
     * Gets the price of the session.
     * @return the price of the session
     */
    public int getPrice();

    /**
     * Gets the maximum number of participants allowed in the session.
     * @return the maximum number of participants
     */
    public int getMaxParticipants();


    /**
     * Getter for the session type.
     * @return The type of session.
     */
    public SessionType getSessionType();

    /**
     * Getter for the full session date.
     * @return The full date and time of the session.
     */
    public String getDate();

    /**
     * Getter for the forum type of the session.
     * @return The forum type of the session.
     */
    public ForumType getForumType();

    /**
     * Getter for the instructor conducting the session.
     * @return The instructor of the session.
     */
    public Instructor getInstructor();

    /**
     * Getter for the list of trainees registered for the session.
     * @return A list of clients registered for the session.
     */
    public List<Client> getTrainees();

    /**
     * Checks if the session date has passed compared to a fixed date ("03-12-2024 00:00").
     * @return True if the session date is after the comparison date, otherwise false.
     */
    public boolean hasDatePassed();

    /**
     * Extracts and returns the day from the session date.
     * @return The day part of the session date in "dd-MM-yyyy" format.
     */
    public String getDay();

    /**
     * Adds a trainee to the session.
     * @param c The client to add as a trainee.
     */
    public void addTrainee(Client c);
}
