# MyChessEngine

Download Link: https://drive.google.com/drive/folders/1g6TVNaikgLV6VBmlKgQzC5npMR0Qj6xv
### Important:
* The project is still under construction. (AI versus AI)
* The Player versus AI functionality Minmax algorithm's depth is set to 4 for optimal speed.
* Rating: 1200 - 1500

## About

MyChessEngine is my Summer 2024 project designed to deepen my understanding of chess and to explore chess engine algorithms such as the MinMax algorithm and various weighting systems. This project serves as both an educational tool and a foundation for developing advanced chess algorithms.

## How to Build and Run

### Prerequisites
- Java JDK 1.8 or higher
- Maven

### Building the Project
To build the project and create a runnable JAR file, navigate to the project directory where the `pom.xml` file is located and run the following command:

```bash
mvn clean package
```

This command cleans the target directory, compiles the source code, and packages the compiled code into a JAR file located in the target/ directory.

### Running the Project
After building, you can run the project directly using the following command:

```bash
java -jar target/MyChessEngine-1.0-SNAPSHOT.jar
```
This will start the chess game application. You can also download the JAR file from the target/ directory to run on any machine with a compatible Java runtime.

## Current Features
* Full Chess Functionality: Supports all standard chess rules including castling, en passant, and pawn promotion.
* Game Management: Allows for resetting the board and maintaining move histories to navigate back to previous moves.
* Evaluation System: Implements raw metrics and evaluation systems to lay the groundwork for future algorithm enhancements.
* Maven Integration: Uses Maven to manage project dependencies and streamline the build and execution process, ensuring easy compilation and distribution
* Player vs AI: Implemented functionality to support player versus AI gameplay mode, using MinMax algorithm with alpha/beta pruning.

## Future Work
* AI vs AI: Develop functionality to support AI versus AI gameplay modes.

## Contact
* Email: stanleykim2003@gmail.com
* Author: Stanley Kim