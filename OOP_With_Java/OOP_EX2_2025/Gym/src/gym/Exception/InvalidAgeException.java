package gym.Exception;

/**
 * Custom exception for handling cases where an invalid age is provided (under 18).
 */
public class InvalidAgeException extends Exception {

    /**
     * Constructs the exception with a specific error message.
     *
     * @param message The error message.
     */
    public InvalidAgeException(String message) {
        super(message);
    }
}

