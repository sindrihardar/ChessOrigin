# Testing Plan

To test this application, I'll be performing white box testing in the form of unit tests for the methods in the model component with JUnit.
Since user interface frameworks such as JavaFX are notoriously difficult to test, and the user interface for my application is incredibly simple, I won't be performing any integration testing.

## ChessGameInterface Unit Tests

| Test # | Method Tested              | Initial Test State Used | Functionality Tested                                                                                                                                                               |
|--------|----------------------------|-------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1      | getAvailableMoves          | 1                       | Adjacent, empty spaces for the king are available.                                                                                                                                 |
| 2      | getAvailableMoves          | 1                       | Adjacent spaces containing a piece of the same color for the king are not available.                                                                                               |
| 3      | getAvailableMoves          | 4                       | Adjacent spaces containing a piece of the opposite color for the king are available.                                                                                               |
| 4      | getAvailableMoves          | 1                       | Adjacent spaces that are out of bounds for the king are not available.                                                                                                             |
| 5      | getAvailableMoves          | 2                       | Adjacent spaces that would put the king in check are not available.                                                                                                                |
| 6      | getAvailableMoves          | 3                       | If the black king and the rook to the right of it have not been moved, the spaces between them are empty, and the king is not in check, then the king can castle.                  |
| 7      | getAvailableMoves          | 3                       | If the black king and the rook to the left of it have not been moved, the spaces between them are empty, and the king is not in check, then the king can castle.                   |
| 8      | getAvailableMoves          | 3                       | If the white king and the rook to the right of it have not been moved, the spaces between them are empty, and the king is not in check, then the king can castle.                  |
| 9      | getAvailableMoves          | 3                       | If the white king and the rook to the left of it have not been moved, the spaces between them are empty, and the king is not in check, then the king can castle.                   |
| 10     | getAvailableMoves          | 4                       | If the black king and the rook to the right of it have not been moved, the spaces between them are not empty, and the king is not in check, then the king cannot castle.           |
| 11     | getAvailableMoves          | 4                       | If the black king and the rook to the left of it have not been moved, the spaces between them are not empty, and the king is not in check, then the king cannot castle.            |
| 12     | getAvailableMoves          | 4                       | If the white king and the rook to the right of it have not been moved, the spaces between them are not empty, and the king is not in check, then the king cannot castle.           |
| 13     | getAvailableMoves          | 4                       | If the white king and the rook to the left of it have not been moved, the spaces between them are not empty, and the king is not in check, then the king cannot castle.            |
| 14     | getAvailableMoves          | 5                       | If the black king and the rook to the right of it have not been moved, the spaces between them are empty, and the king is in check, then the king cannot castle.                   |
| 15     | getAvailableMoves          | 5                       | If the black king and the rook to the left of it have not been moved, the spaces between them are empty, and the king is in check, then the king cannot castle.                    |
| 16     | getAvailableMoves          | 5                       | If the white king and the rook to the right of it have not been moved, the spaces between them are empty, and the king is in check, then the king cannot castle.                   |
| 17     | getAvailableMoves          | 5                       | If the white king and the rook to the left of it have not been moved, the spaces between them are empty, and the king is in check, then the king cannot castle.                    |
| 18     | getAvailableMoves          | 3                       | If the black king has been moved and the rook to the right of it has not been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle. |
| 19     | getAvailableMoves          | 3                       | If the black king has been moved and the rook to the left of it has not been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.  |
| 20     | getAvailableMoves          | 3                       | If the white king has been moved and the rook to the right of it has not been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle. |
| 21     | getAvailableMoves          | 3                       | If the white king has been moved and the rook to the left of it has not been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.  |
| 22     | getAvailableMoves          | 3                       | If the black king hasn't been moved and the rook to the right of it has been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.  |
| 23     | getAvailableMoves          | 3                       | If the black king hasn't been moved and the rook to the left of it has been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.   |
| 24     | getAvailableMoves          | 3                       | If the white king hasn't been moved and the rook to the right of it has been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.  |
| 25     | getAvailableMoves          | 3                       | If the white king hasn't been moved and the rook to the left of it has been moved, the spaces between them are empty, and the king is not in check, then the king cannot castle.   |
| 26     | getAvailableMoves          | 7                       | If castling puts the king in check, then the king cannot castle.                                                                                                                   |
| 27     | getAvailableMoves          | 5                       | The king cannot move to any non-adjacent space.                                                                                                                                    |
| 28     | getAvailableMoves          | 5                       | Diagonal spaces that are empty up until a piece are available for the queen.                                                                                                       |
| 29     | getAvailableMoves          | 7                       | Diagonal spaces that are empty up until the left boundary are available for the queen.                                                                                             |
| 30     | getAvailableMoves          | 7                       | Diagonal spaces that are empty up until the right boundary are available for the queen.                                                                                            |
| 31     | getAvailableMoves          | 7                       | Diagonal spaces that are empty up until the top boundary are available for the queen.                                                                                              |
| 32     | getAvailableMoves          | 7                       | Diagonal spaces that are empty up until the bottom boundary are available for the queen.                                                                                           |
| 33     | getAvailableMoves          | 5                       | Rectangular spaces that are empty up until a piece are available for the queen.                                                                                                    |
| 34     | getAvailableMoves          | 2                       | Rectangular spaces that are empty up until a boundary are available for the queen.                                                                                                 |
| 35     | getAvailableMoves          | 5                       | Spaces that are in a queen's diagonal and contain a piece of the same color are not available.                                                                                     |
| 36     | getAvailableMoves          | 5                       | Spaces that are in a queen's diagonal and contain a piece of the opposite color are available.                                                                                     |
| 37     | getAvailableMoves          | 5                       | Spaces that are in a queen's rectangular path and contain a piece of the same color are not available.                                                                             |
| 38     | getAvailableMoves          | 5                       | Spaces that are in a queen's rectangular path and contain a piece of the opposite color are available.                                                                             |
| 39     | getAvailableMoves          | 5                       | The queen cannot move to a space that is not in it's diagonal or rectangular vectors.                                                                                              |
| 40     | getAvailableMoves          | 2                       | The bishop can move to a space that is in it's diagonal just as the queen can.                                                                                                     |
| 41     | getAvailableMoves          | 2                       | The bishop cannot move to a space that is not in it's diagonal.                                                                                                                    |
| 42     | getAvailableMoves          | 2                       | The rook can move to a space that is in it's rectangular vectors just as the queen can.                                                                                            |
| 43     | getAvailableMoves          | 2                       | The bishop cannot move to a space that is not in it's rectangular vectors.                                                                                                         |
| 44     | getAvailableMoves          | 4                       | The knight can move in any L-shape if those squares are empty.                                                                                                                     |
| 45     | getAvailableMoves          | 4                       | The knight can move in any L-shape if those squares are not out of bounds.                                                                                                         |
| 46     | getAvailableMoves          | 2                       | The knight can move in any L-shape if those squares contain a piece of the opposite color.                                                                                         |
| 47     | getAvailableMoves          | 2                       | The knight cannot move in any L-shape if those squares contain a piece of the same color.                                                                                          |
| 48     | getAvailableMoves          | 4                       | The knight cannot move to a space that is not in an L-shape.                                                                                                                       |
| 49     | getAvailableMoves          | 2                       | The black pawn can move one space forward if that space is empty.                                                                                                                  |
| 50     | getAvailableMoves          | 2                       | The black pawn cannot move one space forward if that space contains a piece of the same color.                                                                                     |
| 51     | getAvailableMoves          | 2                       | The black pawn cannot move one space forward if that space contains a piece of the opposite color.                                                                                 |
| 52     | getAvailableMoves          | 2                       | The black pawn cannot move in a diagonal if that diagonal is empty.                                                                                                                |
| 53     | getAvailableMoves          | 2                       | The black pawn cannot move in a diagonal if that diagonal contains a piece of the same color.                                                                                      |
| 54     | getAvailableMoves          | 2                       | The black pawn can move in a diagonal if that diagonal contains a piece of the opposite color.                                                                                     |
| 55     | getAvailableMoves          | 1                       | The black pawn can perform en passant if there's a pawn of the opposite color to it's left that was moved two spaces on the previous turn.                                         |
| 56     | getAvailableMoves          | 1                       | The black pawn can perform en passant if there's a pawn of the opposite color to it's right that was moved two spaces on the previous turn.                                        |
| 57     | getAvailableMoves          | 1                       | The black pawn cannot perform en passant if there's a pawn of the opposite color to it's left that was not moved two spaces on the previous turn.                                  |
| 58     | getAvailableMoves          | 1                       | The black pawn cannot perform en passant if there's a pawn of the opposite color to it's right that was not moved two spaces on the previous turn.                                 |
| 59     | getAvailableMoves          | 1                       | The white pawn can move one space forward if that space is empty.                                                                                                                  |
| 60     | getAvailableMoves          | 1                       | The white pawn cannot move one space forward if that space contains a piece of the same color.                                                                                     |
| 61     | getAvailableMoves          | 1                       | The white pawn cannot move one space forward if that space contains a piece of the opposite color.                                                                                 |
| 62     | getAvailableMoves          | 1                       | The white pawn cannot move in a diagonal if that diagonal is empty.                                                                                                                |
| 63     | getAvailableMoves          | 1                       | The white pawn cannot move in a diagonal if that diagonal contains a piece of the same color.                                                                                      |
| 64     | getAvailableMoves          | 1                       | The white pawn can move in a diagonal if that diagonal contains a piece of the opposite color.                                                                                     |
| 65     | getAvailableMoves          | 1                       | The white pawn can perform en passant if there's a pawn of the opposite color to it's left that was moved two spaces on the previous turn.                                         |
| 66     | getAvailableMoves          | 1                       | The white pawn can perform en passant if there's a pawn of the opposite color to it's right that was moved two spaces on the previous turn.                                        |
| 67     | getAvailableMoves          | 1                       | The white pawn cannot perform en passant if there's a pawn of the opposite color to it's left that was not moved two spaces on the previous turn.                                  |
| 68     | getAvailableMoves          | 1                       | The white pawn cannot perform en passant if there's a pawn of the opposite color to it's right that was not moved two spaces on the previous turn.                                 |
| 69     | getAvailableMoves          | 1                       | The black pawn cannot move to any spaces that are not one of the two in front of it or the diagonals.                                                                              |
| 70     | getAvailableMoves          | 1                       | The white pawn cannot move to any spaces that are not one of the two in front of it or the diagonals.                                                                              |
| 71     | getAvailableMoves          | 1                       | If the specified space doesn't contain a piece of the current player's color, throws an illegal argument exception.                                                                |
| 72     | move                       | 1                       | A piece can be moved to one of its available spots.                                                                                                                                |
| 73     | move                       | 1                       | A piece cannot be moved to one of its unavailable spots.                                                                                                                           |
| 74     | move                       | 3                       | Moving the white king in a castle to the left places the rook to it's right.                                                                                                       |
| 75     | move                       | 3                       | Moving the white king in a castle to the right places the rook to it's left.                                                                                                       |
| 76     | move                       | 3                       | Moving the black king in a castle to the left places the rook to it's right.                                                                                                       |
| 77     | move                       | 3                       | Moving the black king in a castle to the right places the rook to it's left.                                                                                                       |
| 78     | move                       | 1                       | Moving a pawn in en passant captures the adjacent piece and moves it in a diagonal.                                                                                                |
| 79     | move                       | 1                       | Moving out of bounds throws an illegal argument exception.                                                                                                                         |
| 80     | unexecuteLastMove          | 1                       | Works on standard moves.                                                                                                                                                           |
| 81     | unexecuteLastMove          | 1                       | Works on castle moves.                                                                                                                                                             |
| 82     | unexecuteLastMove          | 3                       | The moved status for a king/rook is reset when a castle move is reversed.                                                                                                          |
| 83     | unexecuteLastMove          | 1                       | Works on en passant moves.                                                                                                                                                         |
| 84     | unexecuteLastMove          | 1                       | Works on promotion.                                                                                                                                                                |
| 85     | isCurrentPlayersPiece      | 1                       | Works for white pieces.                                                                                                                                                            |
| 86     | isCurrentPlayersPiece      | 1                       | Works for black pieces.                                                                                                                                                            |
| 87     | currentPlayerIsInCheckMate | 2                       | Works when the current player is in checkmate.                                                                                                                                     |
| 88     | currentPlayerIsInCheckMate | 2                       | Works when the current player is not in checkmate.                                                                                                                                 |
| 89     | gameIsInStalemate          | 6                       | Works when the game is in stalemate.                                                                                                                                               |
| 90     | gameIsInStalemate          | 1                       | Works when the game is not in stalemate.                                                                                                                                           |
| 91     | getBoardState              | All                     | Works for all test states.                                                                                                                                                         |


















