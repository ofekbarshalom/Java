package gym.management.Sessions;

// Represents a type of gym session, including its name.
public class SessionType {
    private final String name;

    /**
     * Constructs a new SessionType with the specified name.
     *
     * @param name The name of the session type.
     */
    public SessionType(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the session type.
     *
     * @return the name of the session type
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    // Predefined session types for gym sessions used in the main.
    public static final SessionType Pilates = new SessionType("Pilates");
    public static final SessionType MachinePilates = new SessionType("MachinePilates");
    public static final SessionType ThaiBoxing = new SessionType("ThaiBoxing");
    public static final SessionType Ninja = new SessionType("Ninja");
}

