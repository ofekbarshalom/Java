package gym.customers;

import gym.management.Gender;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Person in the gym system, with personal details such as name, balance, gender, and birth date.
 * Each person is assigned a unique ID and supports operations such as adding or deducting money from their balance
 * and calculating their age.
 */
public class Person {
    private static int nextId = 1111; // * Starting ID for auto-generated unique IDs *//
    private int id;
    private String name;
    private Gender gender;
    private String birthDate;
    private Balance balance;

    /**
     * Constructs a new Person object with the specified details.
     *
     * @param name      The name of the person.
     * @param balance   The initial balance of the person.
     * @param gender    The gender of the person.
     * @param birthDate The birthdate of the person in "dd-MM-yyyy" format.
     */
    public Person(String name, int balance, Gender gender, String birthDate) {
        this.id = nextId++;
        this.name = name;
        this.balance = new Balance(balance);
        this.gender = gender;
        this.birthDate = birthDate;
    }

    /**
     * Constructs a copy of an existing Person object.
     *
     * @param p The Person object to copy.
     */
    public Person(Person p)
    {
        this.name = p.name;
        this.gender = p.gender;
        this.birthDate = p.birthDate;
        this.balance = p.balance;
        this.id = p.id;
    }

    /**
    Getter for the unique ID of the person.
    return:
        The unique ID assigned to this person.
    **/
    public int getId() {
        return id;
    }

    /**
     * Getter for the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter for the current balance of the person.
     *
     * @return The balance of the person.
     */
    public int getBalance() {
        return this.balance.getAmount();
    }

    /**
     * Getter for the gender of the person.
     *
     * @return The gender of the person.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Adds a specified amount of money to the person's balance.
     *
     * @param money The amount to add.
     */
    public void addToBalance(int money) {
        this.balance.addAmount(money);
    }

    /**
     * Getter for the birth date of the person.
     *
     * @return The birth date of the person in "dd-MM-yyyy" format.
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * Calculates the age of the person as of a fixed comparison date ("03-12-2024").
     *
     * @return The age of the person in years, with partial years rounded.
     */
    public int getAge() {
        double totalAge = getExactAge();

        if (totalAge - Math.floor(totalAge) >= 0.9) {
            return (int) Math.ceil(totalAge);
        } else {
            return (int) Math.floor(totalAge);
        }
    }

    /**
     * Calculates the exact age of the person as a decimal value based on "03-12-2024".
     * Takes years, months, and days into account.
     *
     * @return The exact age of the person as a double.
     */
    public double getExactAge(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse birth date and comparison date
        LocalDate birthDate = LocalDate.parse(this.birthDate, formatter);
        LocalDate comparisonDate = LocalDate.parse("03-12-2024", formatter);

        // Calculate the difference between dates
        Period period = Period.between(birthDate, comparisonDate);

        // Convert years, months, and days to fractional years
        double years = period.getYears();
        double months = period.getMonths() / 12.0;
        double days = period.getDays() / 365.0;

        return years + months + days; // Return the exact age
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Gender: " + gender + " | Birthday: " + birthDate + " | Age: " + this.getAge() + " | Balance: " + balance.getAmount();
    }

    /**
     * Checks if this person is the same as another person (represented as a Client).
     *
     * @param c The client to compare with.
     * @return True if the ID, name, birth date, gender, and balance match; otherwise false.
     */
    public boolean isTheSamePerson(Client c) {
        return id == c.getId() && name.equals(c.getName()) &&
                birthDate.equals(c.getBirthDate()) && gender == c.getGender() && balance.getAmount() == c.getBalance();
    }
}
