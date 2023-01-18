# Requirements

## Functional
- When the current player selects a tile on the screen, the following must occur:
  - If their piece is selected, highlight all available tiles that the piece can be moved to.
  - If a highlighted tile is selected (which means that the player's piece is already selected), the piece will move there, it will be the other player's turn, and all highlighting will be reset.
  - If neither of the above spaces are selected, nothing should occur.
  - When the cursor goes over a selectable space, that space should be highlighted.

## Non-Functional

Note: Many non-functional requirements are not relevant to this project, so this list will be small.

- The processing for moving a piece should be nearly instant.
  - While I understand that non-functional requirements should be measurable so that they can be tested, I have no intention of performing integration testing on such a small project, so I'll manually test this to ensure that the processing speed isn't too slow.


