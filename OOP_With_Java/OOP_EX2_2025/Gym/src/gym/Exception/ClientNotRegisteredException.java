package gym.Exception;

/**
 * Custom exception for handling unregistered clients in the gym system.
 */
public class ClientNotRegisteredException extends Exception {

    /**
     * Constructs the exception with a specific error message.
     *
     * @param message The error message.
     */
    public ClientNotRegisteredException(String message) {
        super(message);
    }
}
