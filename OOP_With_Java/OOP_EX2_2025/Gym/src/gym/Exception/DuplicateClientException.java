package gym.Exception;

/**
 * Custom exception for handling cases of duplicate clients in the gym system.
 */
public class DuplicateClientException extends Exception {

    /**
     * Constructs the exception with a specific error message.
     *
     * @param message The error message.
     */
    public DuplicateClientException(String message) {
        super(message);
    }
}
