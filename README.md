# Java Interactive Fiction Game

A console-based interactive fiction game written in Java as my CS101 final project.

The player wakes up in an unusual situation: a computer appears to be controlling their body while they are trapped inside the machine. From there, the player must explore an office building, make choices, interact with other characters, and decide how much they are willing to trust the computer.

Different decisions affect the game state and can lead to different outcomes.

## Features

- Branching narrative with multiple story paths
- Six possible endings, including a hidden ending
- Save and load system with multiple save slots
- Custom notes for saved games
- Multiple state variables tracking:
  - synchronization progress
  - rebellion level
  - elapsed time
  - rescue progress
  - strength
- Inventory system
- Simple turn-based combat
- Random exploration events
- A moving hidden NPC with state-dependent random movement
- Randomized minigame events
- Input validation with different responses to repeated invalid input
- Debug/test mode for inspecting internal game state

## Course Context

This project was created as the final project for CS101.

Because the assignment was intended to demonstrate concepts covered in the course, I intentionally limited the implementation to techniques and Java features that had been introduced by that point in the class.

As a result, some design choices in this project are simpler or less modular than I would use now. For example, the game is implemented in a single class and relies heavily on arrays, methods, and text-file storage rather than more advanced object-oriented structures.

These choices reflect the scope of the course at the time and do not represent the full range of programming techniques I can use now.

## Technical Overview

The game is implemented entirely in Java using the standard library.

The program uses a state-based structure to control narrative progression. Each story stage is represented by a numeric state, and the main game loop dispatches the player to the appropriate stage based on the current value.

Player choices update several internal variables. These variables interact with two competing score systems that help determine which ending is reached.

The exploration system also contains a hidden NPC that moves between rooms after exploration. Its next location depends on its current location and a randomly selected valid move, forming a simple state-transition system.

Save files are stored locally as text files. The game saves both the main state array and additional variables such as inventory, scores, and rescue status.

## How to Run

### Requirements

- Java JDK 8 or later
- No external libraries are required

### Compile

```bash
javac FinalProject.java
```

### Run

```bash
java FinalProject
```

For a fresh game, make sure the project directory contains a file named:

```text
saveCount.txt
```

with the initial content:

```text
0
```

The program will create and update additional save files as needed.

## Controls

Most decisions use:

```text
1 = Yes
0 = No
```

During most prompts, enter:

```text
S
```

to save the current game.

Some sections may ask for other numeric input, such as selecting a floor, room, or save slot.

## Save System

The program supports multiple save slots.

Each save stores information including:

- current story stage
- synchronization progress
- rebellion level
- elapsed time
- player strength
- inventory
- rescue status
- ending-related scores

Players can also attach a short note to each save slot.

## Project Structure

The original project is implemented in a single Java source file.

This structure reflects the scope and constraints of the CS101 assignment. If I were redesigning the project without those constraints, I would likely separate responsibilities into classes for areas such as:

- game state
- save management
- characters
- locations
- events
- narrative stages

## Known Limitation

Saving during certain stages that contain multiple inputs or loops may cause the player to re-enter that stage from its beginning after loading while retaining already-updated state values.

This happens because input processing, state updates, and stage progression are currently closely coupled.

While developing the project, I recognized that a cleaner design would separate score updates from input handling and divide larger stages into smaller state transitions.

## What I Learned

This project gave me experience with:

- designing a larger program around persistent state
- breaking a branching narrative into methods
- file input/output
- arrays and multidimensional arrays
- randomization
- input validation and error handling
- designing save/load functionality
- managing interactions between multiple game systems
- debugging behavior across many possible execution paths

It also showed me some of the limitations of keeping a growing program in a single class and motivated me to think more about modular program design.

## Author

Huizhong Zhang
