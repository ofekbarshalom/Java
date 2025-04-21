package gym.management;

import gym.customers.Person;
import gym.management.Sessions.SessionType;

import java.util.List;

/**
 * Represents an Instructor in the gym system.
 * Instructors are associated with a Person object.
 */
public class Instructor extends Person {
    private int hourlyRate; // The instructor's hourly wage
    private List<SessionType> specialties; // List of session types the instructor is certified to teach

    /**
     * Constructor to initialize an Instructor with a Person object, hourly rate, and specialties.
     * @param person The Person object associated with this instructor.
     * @param hourlyRate The hourly wage for the instructor.
     * @param specialties The list of session types the instructor is certified to teach.
     */
    public Instructor(Person person, int hourlyRate, List<SessionType> specialties) {
        super(person);
        this.hourlyRate = hourlyRate;
        this.specialties = specialties;
    }

    /**
     * Getter for the hourly rate of the instructor.
     * @return The hourly wage of the instructor.
     */
    public int getHourlyRate() {
        return hourlyRate;
    }

    /**
     * Getter for the list of specialties the instructor is certified to teach.
     * @return A list of session types representing the instructor's specialties.
     */
    public List<SessionType> getSpecialties() {
        return specialties;
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: Instructor" + " | Salary per Hour: " + hourlyRate +
                " | Certified Classes: " + specialtiesToString(specialties);
    }

    /**
     * Convert the list of specialties to a formatted string.
     * @param specialties The list of session types representing the instructor's specialties.
     * @return A string representation of the specialties, separated by commas.
     */
    public static String specialtiesToString(List<SessionType> specialties) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < specialties.size(); i++) {
            sb.append(specialties.get(i).toString());
            if (i != specialties.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
