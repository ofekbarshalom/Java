package gym.management.Sessions;

import gym.customers.Client;
import gym.management.Instructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Machine Pilates session with specific details, including
 * session type, date, instructor, forum type, and trainees.
 */
public class MachinePilatesSession implements Session{
    private SessionType sessionType; // Type of the session
    private String date; // The date and time of the session in "dd-MM-yyyy HH:mm" format
    private ForumType forumType; // The forum type of the session
    private Instructor instructor; // The instructor conducting the session
    private List<Client> trainees; // List of clients registered for the session

    private final int MAX_PARTICIPANTS = 10; // Maximum allowed participants
    private final int PRICE = 80; // Price per participant

    /**
     * Constructs a machine pilates session.
     *
     * @param date The date and time of the session in "dd-MM-yyyy HH:mm" format.
     * @param forumType The forum type of the session.
     * @param instructor The instructor conducting the session.
     */
    public MachinePilatesSession(String date, ForumType forumType, Instructor instructor) {
        this.sessionType = SessionType.MachinePilates;
        this.date = date;
        this.forumType = forumType;
        this.instructor = instructor;
        this.trainees = new ArrayList<>();
    }

    @Override
    public int getMaxParticipants() {return MAX_PARTICIPANTS;}

    @Override
    public int getPrice() {return PRICE;}

    @Override
    public SessionType getSessionType() {
        return sessionType;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public ForumType getForumType() {
        return forumType;
    }

    @Override
    public Instructor getInstructor() {
        return instructor;
    }

    @Override
    public List<Client> getTrainees() {
        return trainees;
    }

    @Override
    public boolean hasDatePassed() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime sessionDateTime = LocalDateTime.parse(this.date, formatter); // Parse session date
        LocalDateTime comparisonDate = LocalDateTime.parse("03-12-2024 00:00", formatter); // Fixed comparison date
        return sessionDateTime.isAfter(comparisonDate); // Check if session date is after comparison date
    }

    @Override
    public String getDay() {
        return date.substring(0, 10);
    }

    @Override
    public void addTrainee(Client c) {
        this.trainees.add(c);
    }

    @Override
    public String toString() {
        return "Session Type: " + sessionType +
                " | Date: " + date +
                " | Forum: " + forumType +
                " | Instructor: " + instructor.getName() +
                " | Participants: " + trainees.size() + "/" + MAX_PARTICIPANTS;
    }
}
