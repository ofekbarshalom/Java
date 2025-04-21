package gym.Exception;

/**
 * Custom exception for handling cases where an instructor is not qualified to run the class.
 */
public class InstructorNotQualifiedException extends Exception {

    /**
     * Constructs the exception with a specific error message.
     *
     * @param message The error message.
     */
    public InstructorNotQualifiedException(String message) {
        super(message);
    }
}
