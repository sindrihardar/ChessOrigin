# Design

Since features will be iteratively added to the project, I want to ensure that my project is modifiable and well-designed.
To do so, I'll be implementing a variety of well-known design patterns, many of which are described in *Design Patterns*.
I'll be specifying the design in UML as both structural and behavioral diagrams.

## Structural

### Component Diagram
![](resources/component_diagram.png)

The overall architectural style that I chose for this project is the Model-View-Controller (MVC) style.
The rationale behind this choice was that MVC is the most popular architectural style, and Java's built-in Observer and Observable interfaces make it simple to bind the view to the model.

### Class Diagram - View Component
![](resources/class_diagram_view_component.png)

There isn't much of an explanation needed here.
The application has a single scene, which is a TilePane which is composed of StackPanes that contain images of each of the pieces of the board.

### Class Diagram - Controller Component
![](resources/class_diagram_controller_component.png)

The controller component is composed of two event handlers. 
One of them is for when the user clicks on a space on the board, the other is for when the user hovers over a space.

### Class Diagram - Model Component
![](resources/class_diagram_model_component.png)

To construct the above class diagram, I started with an Object-Oriented Analysis (OOA), which led me to develop a ChessGame class, a Space class, and a Piece class.
The Piece class is abstract, and each concrete sub class represents a type of piece.
To distribute the workload amongst the different classes, I wanted the pieces to decide how movements on the board should occur, and which spaces they can move to.
However, I didn't want the pieces to actually operate on the board.
Thus, I followed the command design pattern and created the MoveCommand class. 
The pieces construct a move command, which tells the game how a move should be executed.
Then, the game executes the move command itself.
This also supports the ability to undo moves that have been made.

I also utilized the builder design pattern with the ChessBoard builder class.
This can be used to construct the board for the game, as well as getting the state of the board from the game.

## Behavioral

For the behavioral diagrams, there's no need to create a use case diagram since there's just a single actor, the user, and there's only two ways for the user to interact with the application, either by hovering over a space or by selecting it.

Instead, I'll be creating some sequence diagrams for the more complicated methods in the ChessGameInterface.

Please note that many details have been removed from the following diagrams to keep them concise.
Creating a diagram for each and every method would also take an obscene amount of time, so many methods have been omitted.

### *getAvailableMoves* Sequence Diagram

![](resources/get_available_moves_sequence_diagram.png)

### *move* Sequence Diagram

![](resources/move_sequence_diagram.png)
