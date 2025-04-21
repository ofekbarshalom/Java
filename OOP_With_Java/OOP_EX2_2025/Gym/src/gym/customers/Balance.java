package gym.customers;

 // Represents a customer's balance in the gym system.
public class Balance {
    private int amount;

     /**
      * Constructs a new Balance instance with an initial amount.
      *
      * @param initialAmount the initial balance amount to set
      */
    public Balance(int initialAmount) {
        this.amount = initialAmount;
    }

     /**
      * Retrieves the current balance amount.
      *
      * @return the current balance amount
      */
    public  int getAmount() {
        return amount;
    }

     /**
      * Adds a specified amount to the current balance.
      *
      * @param amount the amount to add to the balance
      */
    public void addAmount(int amount) {
        this.amount += amount;
    }

}
