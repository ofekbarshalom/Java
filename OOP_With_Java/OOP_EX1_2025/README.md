# OOP_EX1_2025

## Overview

This repository contains the implementation of an Object-Oriented Programming (OOP) project completed as part of a university course. The project is focused on implementing a chess-like game, specifically Reversi (or Othello), with support for different AI players, a graphical user interface, and various gameplay mechanics. The primary goal of the project was to apply OOP principles, such as inheritance, encapsulation, and polymorphism, in a practical and educational context.

---

## Features

- **Graphical User Interface (GUI):** A GUI for chess-like games, facilitating user interaction.
- **AI Players:** Multiple AI strategies are implemented, including:
  - Greedy AI
  - Random AI
  - MinMax AI
- **Game Mechanics:** Core Reversi gameplay logic, including:
  - Handling discs and their states.
  - Supporting human and computer players.
  - Undo and replay functionality.
- **Extensibility:** Modular design for adding additional game logic or AI strategies.

---

## Project Structure

The repository is organized as follows:

- **Main.java:** Entry point for the application.
- **Game Logic:**
  - `GameLogic.java` - Core game mechanics and rules.
  - `PlayableLogic.java` - Interfaces and abstractions for custom logic.
- **Players:**
  - `Player.java` - Abstract class representing a player.
  - `HumanPlayer.java` - Implementation for human players.
  - `AIPlayer.java` - Abstract class for AI players.
  - `GreedyAI.java`, `RandomAI.java`, `MinMaxAI.java` - Specific AI strategies.
- **Discs:**
  - `Disc.java` - Base class for discs used in the game.
  - `SimpleDisc.java`, `BombDisc.java`, `UnflippableDisc.java` - Specialized discs.
- **GUI:**
  - `GUI_for_chess_like_games.java` - Graphical interface for game interaction.
- **Utils:**
  - `Move.java`, `Position.java` - Supporting classes for managing moves and positions.
- **Documentation:**
  - `README.md` - Project overview.
  - `מימוש משחק רברסי.pdf` - Detailed documentation in Hebrew.

---

## Requirements

- **Java Development Kit (JDK):** Version 8 or higher.
- **Development Environment:** IntelliJ IDEA or any preferred Java IDE.
- **Libraries:** None (all dependencies are built-in).

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/<username>/OOP_EX1_2025.git
   cd OOP_EX1_2025
   ```

2. Open the project in your Java IDE.

3. Run the `Main.java` file to start the game.

---

## Acknowledgments

This project was completed as part of an OOP course at Ariel University. Special thanks to the course instructors and teaching assistants for their guidance.
