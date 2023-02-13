# Software Development Plan

## Description

My vision for this project is that someday it will be a fleshed out chess application. 
It'll allow players to play on the same device against one another, to play against the computer, and to play against people on other devices.
It'll have a variety of features so that users can keep track of their blunders and mistakes, and they can go back and look at them.

These are high aspirations, and an application that has these features would take a long time to develop. 
With a waterfall development process, I would have to design the entire application before implementing any of the code, which means I won't write code for a very long time.
Another problem with this process for this project is that I have minimal experience with JavaFX, so I might end up designing a user interface and upon implementing it I realize there's a better way of doing it and I'd have to go back and redo the design.

Instead of following this approach, I think it would be best that I follow an incremental development process. 
This would allow me to build a simple chess application and iteratively add features to it.

In this document you'll find a description of the software development process, the phases that compose the process, and also a table which shows which features have been added in each iteration.

## Incremental Software Development Process Description
In an incremental software development process, software is developed in cycles. 
For each iteration, additional features will be added to the application. 
Each iteration involves sequentially executing each phase of the process.

## Software Development Process Phases
Here are the following phases that I'll be going through for this project:
 - Problem Description
   - The problem description document will be updated to include whatever feature is being added to the system.
   - The user interface design will also be included in the problem description document, since the description is tightly coupled with the user interface.
 - Requirements Gathering
   - The list of functional and non-functional requirements will be updated to include whatever functionality is being brought to the system with the new feature.
   - The requirements document will contain all requirements for the system.
 - Design
   - Once the requirements have been defined, the design of the system will be updated accordingly. 
   - The design of the system can be located in the design document.
   - The design of the system will be composed of structural and behavioral UML documents.
 - Testing Plan
   - After the system has been designed and interfaces have been added, a skeleton of the system will be implemented which contains stubs for the interface.
   - A testing plan will be formed to verify/validate the new system functionality, which will be included in the testing plan document.
   - The tests will then be implemented.
     - Note that the tests are implemented *prior* to the implementation of the system.
     I anticipate needing to refactor to improve performance, so I'll be using test-driven development, although I'm not following an agile development process.
 - Implementation
   - The features will be incrementally implemented in each component and subcomponent according to the design of the system.

## Iterations

| Iteration # | Feature Added                                 | Final Commit ID                          |
|-------------|-----------------------------------------------|------------------------------------------|
| 1           | A simple chess application.                   | 8170c59e0dec050f0ed2899623b93bd35de7a3f9 |
| 2           | Added a home screen.                          | 2b77c9e96747f1d60a7113aa59083541477f9af0 |
| 3           | Added an option to play against the computer. | 4d579b41846534e3667a22c30ea1c709395fb77e |
| 4           | Added animations to piece movement.           | ace6e12acb4db4aa01cca333d81b806bd42b6f46 |
| 5           | Added timers for the players.                 | c1ccc1aee0ed7c10e7cf9ef8450b390ccbc32474 |
| 6           | Redesigned the user interface.                | d2b02a95630268e819cf827447ae486555fc9295 |
| 7           | Added algebraic notation.                     | 31963d9f0d9f532377ceb9d1b2c40e9d44f7aa17 |