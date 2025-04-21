package gym.management;

import gym.Exception.*;
import gym.customers.*;
import gym.management.Sessions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents a Secretary in the gym management system.
 * The Secretary manages client registrations, instructor hiring, session scheduling,
 * and notifications. It also handles administrative tasks such as paying salaries
 * and recording gym actions in the history.
 * Implements the Subject interface to send notifications to clients and sessions.
 */
public class Secretary extends Person implements Subject {
    private int salary; // The salary of the secretary

    /**
     * Constructor to initialize a Secretary object.
     * @param person The Person object representing the secretary.
     * @param salary The salary of the secretary.
     */
    public Secretary(Person person, int salary) {
        super(person);
        this.salary = salary;
        Gym gym = Gym.getInstance();
        gym.addAction("A new secretary has started working at the gym: " + person.getName());
    }

    /**
     * Checks if the current Secretary is the one registered with the Gym.
     * @throws NullPointerException If the Secretary is not the registered one.
     */
    public void isGymSecretary() throws NullPointerException {
        Gym gym = Gym.getInstance();
        if (!gym.getSecretary().equals(this)) {
            throw new NullPointerException();
        }
    }

    /**
     * Formats a date with time into ISO8601 format.
     * @param date The input date in "dd-MM-yyyy HH:mm" format.
     * @return The formatted date in ISO8601 format.
     */
    public static String formatToISO8601WithTime(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime sessionDateTime = LocalDateTime.parse(date, inputFormatter);
        return sessionDateTime.format(outputFormatter);
    }

    /**
     * Formats a date into ISO8601 format without time.
     * @param date The input date in "dd-MM-yyyy" format.
     * @return The formatted date in ISO8601 format.
     */
    public static String formatDateToISO8601(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sessionDate = LocalDate.parse(date, inputFormatter);
        return sessionDate.format(outputFormatter);
    }

    /**
     * Registers a new client at the gym.
     * @param p The Person object representing the client.
     * @return The newly registered Client object.
     * @throws InvalidAgeException If the client is under 18 years old.
     * @throws DuplicateClientException If the client is already registered.
     */
    public Client registerClient(Person p) throws InvalidAgeException, DuplicateClientException {
        // Ensure the secretary performing this action is the registered one for the gym.
        isGymSecretary();

        // Get the singleton instance of the Gym and retrieve the list of clients.
        Gym gym = Gym.getInstance();
        List<Client> clients = gym.getClients();

        if (p.getExactAge() < 18) {
            throw new InvalidAgeException("Error: Client must be at least 18 years old to register");
        }

        // Check if the client is already registered in the gym's client list.
        for (Client client : clients) {
            if (p.isTheSamePerson(client)) {
                throw new DuplicateClientException("Error: The client is already registered");
            }
        }

        Client newClient = PersonFactory.createClient(p); // Use Factory pattern to create the client
        clients.add(newClient);

        // Record the action in the gym's history.
        gym.addAction("Registered new client: " + p.getName());
        return newClient;
    }

    /**
     * Unregisters a client from the gym.
     * @param c The Client object to unregister.
     * @throws ClientNotRegisteredException If the client is not found in the gym.
     */
    public void unregisterClient(Client c) throws ClientNotRegisteredException {
        // Ensure the secretary performing this action is the registered one for the gym.
        isGymSecretary();

        // Get the singleton instance of the Gym and retrieve the list of clients.
        Gym gym = Gym.getInstance();
        List<Client> clients = gym.getClients();

        // Attempt to remove the client from the list. If unsuccessful, throw an exception.
        if (!clients.remove(c)) {
            throw new ClientNotRegisteredException("Error: Registration is required before attempting to unregister");
        }

        // Log the action in the gym's history if the client was successfully unregistered.
        gym.addAction("Unregistered client: " + c.getName());
    }

    /**
     * Hires a new instructor for the gym.
     * @param person The Person object representing the instructor.
     * @param hourlyWage The hourly wage for the instructor.
     * @param sessions The list of session types the instructor is certified to teach.
     * @return The newly hired Instructor object.
     */
    public Instructor hireInstructor(Person person, int hourlyWage, ArrayList<SessionType> sessions) {
        // Ensure the secretary performing this action is the registered one for the gym.
        isGymSecretary();
        Instructor newInstructor = PersonFactory.createInstructor(person, hourlyWage, sessions); // Use Factory pattern

        Gym gym = Gym.getInstance();
        gym.getInstructors().add(newInstructor);
        gym.addAction("Hired new instructor: " + person.getName() + " with salary per hour: " + hourlyWage);
        return newInstructor;
    }

    /**
     * Adds a new session to the gym's schedule.
     * @param sessionType The type of session.
     * @param date The date and time of the session.
     * @param forumType The forum type (e.g., All, Male, Female, etc.).
     * @param instructor The instructor conducting the session.
     * @return The newly created Session object.
     * @throws InstructorNotQualifiedException If the instructor is not certified to teach the session type.
     */
    public Session addSession(SessionType sessionType, String date, ForumType forumType, Instructor instructor) throws InstructorNotQualifiedException {
        // Ensure the secretary performing this action is the registered one for the gym.
        isGymSecretary();
        Gym gym = Gym.getInstance();

        // Check if the instructor is certified to teach the given session type.
        if (!instructor.getSpecialties().contains(sessionType)) {
            throw new InstructorNotQualifiedException("Error: Instructor is not qualified to conduct this session type.");
        }

        // Create a new Session object using the provided details
        String formattedDate = formatToISO8601WithTime(date);
        Session newSession = SessionFactory.createSession(sessionType, date, forumType, instructor);

        // Add the new session to the gym's session list.
        gym.getSessions().add(newSession);

        // Record the action of creating a new session in the gym's history.
        gym.addAction(String.format(
                "Created new session: %s on %s with instructor: %s",
                sessionType, formattedDate, instructor.getName()
        ));
        return newSession;
    }

    /**
     * Registers a client to a session.
     * @param c The client to register.
     * @param s The session to register the client for.
     * @throws DuplicateClientException If the client is already registered for the session.
     * @throws ClientNotRegisteredException If the client is not registered with the gym.
     */
    public void registerClientToLesson(Client c, Session s) throws DuplicateClientException, ClientNotRegisteredException {
        isGymSecretary();

        // Get the singleton instance of the Gym and retrieve the list of clients.
        Gym gym = Gym.getInstance();
        List<Client> clients = gym.getClients();

        boolean check = true; // Flag to track if all conditions for registration are met.

        // Check if the client is already registered for the session.
        if (s.getTrainees().contains(c)) {
            throw new DuplicateClientException("Error: The client is already registered for this lesson");
        }

        // Check if the client is registered with the gym.
        if (!clients.contains(c)) {
            throw new ClientNotRegisteredException("Error: The client is not registered with the gym and cannot enroll in lessons");
        }

        // Check if time is correct
        if (!s.hasDatePassed()) {
            gym.addAction("Failed registration: Session is not in the future");
            check = false;
        }

        // Check if the forum is correct
        if (!checkClientForm(c, s.getForumType())) {
            check = false;
        }

        // Check if there are available places left
        if (s.getTrainees().size() >= s.getMaxParticipants()) {
            gym.addAction("Failed registration: No available spots for session");
            check = false;
        }

        // Check if the client has enough balance to pay
        int price = s.getPrice();
        if (price > c.getBalance()) {
            gym.addAction("Failed registration: Client doesn't have enough balance");
            check = false;
        }

        // If all checks pass, register the client for the session.
        if (check) {
            s.addTrainee(c);
            c.chargeClient(price);
            gym.addMoney(price);

            // Log the successful registration in the gym's history.
            gym.addAction(String.format(
                    "Registered client: %s to session: %s on %s for price: %d",
                    c.getName(),
                    s.getSessionType(),
                    formatToISO8601WithTime(s.getDate()),
                    price
            ));
        }
    }

    /**
     * Validates if a client meets the forum requirements for a session.
     * @param c The client to validate.
     * @param forumType The forum type of the session.
     * @return True if the client meets the requirements, false otherwise.
     */
    public boolean checkClientForm(Client c, ForumType forumType) {
        Gym gym = Gym.getInstance();

        switch (forumType) {
            case All:
                return true;

            case Male:
                if (c.getGender() != Gender.Male) {
                    gym.addAction("Failed registration: Client's gender doesn't match the session's gender requirements");
                    return false;
                }
                break;

            case Female:
                if (c.getGender() != Gender.Female) {
                    gym.addAction("Failed registration: Client's gender doesn't match the session's gender requirements");
                    return false;
                }
                break;

            case Seniors:
                if (c.getAge() < 65) {
                    gym.addAction("Failed registration: Client doesn't meet the age requirements for this session (Seniors)");
                    return false;
                }
                break;

            default:
                break;
        }

        return true;
    }

    /**
     * Sends a notification to all clients registered for a specific session.
     * @param session The session for which to send notifications.
     * @param message The message to send.
     */
    @Override
    public void notify(Session session, String message) {
        isGymSecretary();
        Gym gym = Gym.getInstance();

        // Iterate through the list of trainees in the session and send the notification.
        for (Client c : session.getTrainees()) {
            c.notify(message);
        }

        // Log the notification action in the gym's history.
        gym.addAction(String.format(
                "A message was sent to everyone registered for session %s on %s : %s",
                session.getSessionType(),
                formatToISO8601WithTime(session.getDate()),
                message
        ));
    }

    /**
     * Sends a notification to all clients registered for sessions on a specific day.
     * @param day The day in "dd-MM-yyyy" format.
     * @param message The message to send.
     */
    @Override
    public void notify(String day, String message) {
        isGymSecretary();
        Gym gym = Gym.getInstance();
        List<Session> sessions = gym.getSessions();

        // Iterate over all sessions and check if the session's day matches the provided day.
        for (Session session : sessions) {
            if (session.getDay().equals(day)) {
                for (Client client : session.getTrainees()) {
                    client.notify(message);
                }
            }
        }

        gym.addAction("A message was sent to everyone registered for a session on " + formatDateToISO8601(day) + " : " + message);
    }

    /**
     * Sends a notification to all clients in the gym.
     * @param message The message to send.
     */
    @Override
    public void notify(String message) {
        isGymSecretary();
        Gym gym = Gym.getInstance();
        List<Client> clients = gym.getClients();

        // Iterate over all clients and send the notification.
        for (Client client : clients) {
            client.notify(message);
        }

        gym.addAction("A message was sent to all gym clients: " + message);
    }

    /**
     * Pays salaries to all instructors based on their sessions.
     */
    public void paySalaries() {
        isGymSecretary();
        Gym gym = Gym.getInstance();
        List<Session> sessions = gym.getSessions();

        // Pay the secretary
        gym.takeMoney(salary);

        // Iterate over each session and pay the instructor their hourly wage from the gym balance.
        for (Session session : sessions) {
            int instructorWage = session.getInstructor().getHourlyRate();
            gym.takeMoney(instructorWage);
            session.getInstructor().addToBalance(instructorWage);
        }

        gym.addAction("Salaries have been paid to all employees");
    }

    /**
     * Prints the history of all actions taken in the gym.
     */
    public void printActions() {
        isGymSecretary();
        Gym gym = Gym.getInstance();

        // Iterate over the history list and print each recorded action.
        for (String action : gym.getHistory()) {
            System.out.println(action);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Secretary" + " | Salary per Month: " + salary;
    }
}
