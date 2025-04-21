package gym.customers;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Client in the gym system, implementing the Observer interface.
 * A Client is associated with a Person object.
 */
public class Client extends Person implements Observer {
    private List<String> messages = new ArrayList<>(); // List to store messages for the client

    /**
     * Constructs a new Client object associated with a specific Person.
     *
     * @param person The Person object to associate with this Client.
     */
    public Client(Person person) {
        super(person);
    }

    /**
     * Charges the client a specified amount of money by reducing their balance.
     *
     * @param money The amount to charge (subtracted from the balance).
     */
    public void chargeClient(int money){
        this.addToBalance(-money);
    }

    @Override
    public void notify(String message) {
        messages.add(message);
    }

    /**
     * Retrieves all notifications for the client as a string.
     *
     * @return A string containing all notifications, separated by commas.
     */
    public String getNotifications() {
        return "[" + String.join(", ", messages) + "]";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
