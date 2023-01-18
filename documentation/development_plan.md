# Software Development Plan

## Description
I'll be following a software development process as I build this project in order to catch problems before they become difficult to fix.

My vision for this project is that someday it will be a fleshed out chess application. 
It'll allow players to play on the same device against one another, to play against the computer, and to play against people on other devices.
It'll have a variety of features so that users can keep track of their blunders and mistakes, and they can go back and look at those.

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

| Iteration # | Feature Added |
|-------------|---------------|
|1| A simple chess application. |
