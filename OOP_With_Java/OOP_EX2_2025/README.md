# Gym Management System - OOP Assignment

This project implements an Object-Oriented Programming (OOP) model for managing a gym. The system allows handling customers, trainers, and gym sessions while maintaining strict encapsulation, hierarchical design, and other OOP principles.

## Features

1. **Customer Management**
   - Add and remove customers.
   - Ensure only individuals over 18 can register.
2. **Trainer Management**
   - Add trainers with hourly pay rates and specializations.
3. **Session Management**
   - Schedule sessions with specific trainers.
   - Handle registrations based on session capacity, customer eligibility, and payment.
4. **Messaging System**
   - Send messages to all customers or customers of specific sessions.
5. **Activity Logging**
   - Log all system activities for future reference.
6. **OOP Features**
   - Implements OOP principles including encapsulation, polymorphism, and the use of design patterns such as Singleton, Observer, and Factory.

## Project Structure

```
|-- src
    |-- Main.java          # Entry point of the program
    |-- gym
        |-- customers      # Customer-related classes
        |-- Exception      # Custom exception handling
        |-- management     # Gym management classes
```

## Requirements

- **Java 8 or higher**
- IDE (e.g., IntelliJ IDEA or Eclipse) or Java compiler

## Running the Program

1. Open the project in your IDE or ensure all files are in the same folder.
2. Compile the code using the following command:

   ```bash
   javac src/Main.java
   ```

3. Run the program:

   ```bash
   java src/Main
   ```

4. Follow the prompts in the program to test its functionality.

## Testing

A Python script (`auto_check.py`) is provided to verify your implementation against expected outputs. To run the script:

1. Place your compiled program and `output.txt` file in the same directory as the script.
2. Run the script using Python:

   ```bash
   python3 auto_check.py
   ```

3. The script will compare your output with the expected results and indicate if your implementation is correct.
4. keep in mind that the python script works only in linux.

## Design Patterns Used

- **Singleton**: Ensures a single instance of core management classes.
- **Observer**: Implements notifications for customer messaging.
- **Factory**: Creates objects based on input parameters.

## Authors

This project is part of the Object-Oriented Programming (OOP) course and adheres to the guidelines provided in the assignment.

## License

This project is for educational purposes only and should not be reused or distributed without permission.
