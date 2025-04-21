package gym.customers;

import gym.management.*;
import gym.management.Sessions.SessionType;

import java.util.List;

/**
 * The PersonFactory class is a factory class for creating different types of persons.
 * It implements the Factory Design Pattern by providing static methods to create instances
 * of Client, Secretary and Instructor.
 */
public class PersonFactory {

    /**
     * Creates a new Client object using the provided Person instance.
     *
     * @param person The base Person object containing general information about the individual.
     * @return A new Client object initialized with the provided Person instance.
     */
    public static Client createClient(Person person) {
        return new Client(person);
    }

    /**
     * Creates a new Secretary object using the provided Person instance and salary.
     *
     * @param person The base Person object containing general information about the individual.
     * @param salary The month salary assigned to the secretary.
     * @return A new Secretary object initialized with the provided Person instance and salary.
     */
    public static Secretary createSecretary(Person person, int salary) {
        return new Secretary(person, salary);
    }

    /**
     * Creates a new Instructor object using the provided Person instance, hourly rate,
     * and list of session type specialties.
     *
     * @param person      The base Person object containing general information about the individual.
     * @param hourlyRate  The hourly rate for the instructor.
     * @param specialties A list of session types the instructor specializes in.
     * @return A new Instructor object initialized with the provided details.
     */
    public static Instructor createInstructor(Person person, int hourlyRate, List<SessionType> specialties) {
        return new Instructor(person, hourlyRate, specialties);
    }
}
