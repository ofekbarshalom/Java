package gym.management;

import gym.customers.*;
import gym.management.Sessions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Gym system, implementing the Singleton pattern to ensure a single instance.
 * Manages clients, instructors, sessions, and financial operations.
 * Provides methods to track gym actions, set up staff, and retrieve data about the gym.
 */
public class Gym {
    private static Gym instance; // Singleton instance of the Gym
    private String name; // Name of the gym
    private Secretary Secretary; // Gym's secretary
    private List<Client> clients = new ArrayList<>(); // List of clients registered at the gym
    private List<Instructor> instructors = new ArrayList<>(); // List of instructors working at the gym
    private List<Session> sessions = new ArrayList<>(); // List of sessions at the gym
    private int gymBalance = 0; // Total gym balance
    private List<String> history = new ArrayList<>(); // List of actions recorded in the gym's history

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private Gym() {}

    /**
     * Get the singleton instance of the Gym.
     * @return The single instance of the Gym.
     */
    public static Gym getInstance() {
        if (instance == null) {
            instance = new Gym();
        }
        return instance;
    }

    /**
     * Add an action to the gym's history.
     * @param action The action to record.
     */
    public void addAction(String action) {
        history.add(action);
    }

    /**
     * Set the name of the gym.
     * @param name The name of the gym.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the secretary for the gym using the factory.
     * @param person The Person object to be the secretary.
     * @param salary The salary of the secretary.
     */
    public void setSecretary(Person person, int salary) {
        this.Secretary = PersonFactory.createSecretary(person, salary);
    }

    /**
     * Get the gym's action history.
     * @return A list of recorded actions.
     */
    public List<String> getHistory() {
        return history;
    }

    /**
     * Get the secretary of the gym.
     * @return The Secretary object.
     */
    public Secretary getSecretary() {
        return Secretary;
    }

    /**
     * Get the list of clients registered at the gym.
     * @return A list of Client objects.
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Get the list of instructors working at the gym.
     * @return A list of Instructor objects.
     */
    public List<Instructor> getInstructors() {
        return instructors;
    }

    /**
     * Get the list of sessions offered at the gym.
     * @return A list of Session objects.
     */
    public List<Session> getSessions() {
        return sessions;
    }

    /**
     * Add money to the gym's balance.
     * @param money The amount to add to the balance.
     */
    public void addMoney(int money) {
        this.gymBalance += money;
    }

    /**
     * Take money from the gym's balance.
     * @param money The amount to take from the balance.
     */
    public void takeMoney(int money){
        this.gymBalance -= money;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Gym Name: ").append(name).append("\n");
        sb.append("Gym Secretary: ").append(Secretary).append("\n");
        sb.append("Gym Balance: ").append(gymBalance).append("\n\n");

        sb.append("Clients Data:\n");
        for (Client client : clients) {
            sb.append(client).append("\n");
        }

        sb.append("\nEmployees Data:\n");
        for (Instructor instructor : instructors) {
            sb.append(instructor).append("\n");
        }
        sb.append(Secretary).append("\n");

        sb.append("\nSessions Data:\n");
        for (int i = 0; i < sessions.size(); i++) {
            sb.append(sessions.get(i));
            if (i != sessions.size() - 1) { // Check if it's NOT the last element
                sb.append("\n");
            }
        }

        String result = sb.toString().replace("\u00A0", " "); // Replace non-breaking spaces with regular spaces
        return result.trim();
    }
}
