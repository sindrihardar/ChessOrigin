import com.chess.model.game.ChessGame;
import com.chess.model.game.ChessGameInterface;
import com.chess.model.game.Tile;
import com.chess.model.util.Colors;
import com.chess.model.util.Pieces;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.chess.model.util.Pieces.*;
import static org.junit.jupiter.api.Assertions.*;

public class ChessGameInterfaceTestSuite {
    private ChessGameInterface game;
    private static final boolean T = true, F = false;
    private static final Pieces WK = WHITE_KING, WQ = WHITE_QUEEN, WR = WHITE_ROOK,
                                WB = WHITE_BISHOP, Wk = WHITE_KNIGHT, WP = WHITE_PAWN,
                                BK = BLACK_KING, BQ = BLACK_QUEEN, BR = BLACK_ROOK,
                                BB = BLACK_BISHOP, Bk = BLACK_KNIGHT, BP = BLACK_PAWN, NN = null;

    /*
     * Helper method to check if all the spaces that are marked as true in spaces are contained in set.
     */
    public void spacesAreContainedInSet(boolean[][] spaces, Set<Tile> set) {
        for (int i = 0; i < spaces.length; i++)
            for (int j = 0; j < spaces[0].length; j++)
                if (spaces[i][j])
                    assertTrue(set.contains(Tile.getTile(i, j)));
    }

    /*
     * Helper method to check that all the spaces that are marked as false in spaces are not contained in set.
     */
    public void spacesAreNotContainedInSet(boolean[][] spaces, Set<Tile> set) {
        for (int i = 0; i < spaces.length; i++)
            for (int j = 0; j < spaces[0].length; j++)
                if (!spaces[i][j])
                    assertTrue(!set.contains(Tile.getTile(i, j)));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorWhenRowIsInvalid() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.doesTileContainPieceOfCurrentPlayersColor(-1, 0));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorWhenColumnIsInvalid() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.doesTileContainPieceOfCurrentPlayersColor(0, -1));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorForValidSelectionOfWhitePiece() {
        ChessGameInterface game = new ChessGame();
        assertTrue(game.doesTileContainPieceOfCurrentPlayersColor(7, 0));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorForValidSelectionOfBlackPiece() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);
        assertTrue(game.doesTileContainPieceOfCurrentPlayersColor(0, 0));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorForEmptySpace() {
        ChessGameInterface game = new ChessGame();
        assertTrue(!game.doesTileContainPieceOfCurrentPlayersColor(3, 0));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorForOppositeColorWhenPlayerIsWhite() {
        ChessGameInterface game = new ChessGame();
        assertTrue(!game.doesTileContainPieceOfCurrentPlayersColor(0, 0));
    }

    @Test
    public void testDoesSpaceContainPieceOfCurrentPlayersColorForOppositeColorWhenPlayerIsBlack() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);
        assertTrue(!game.doesTileContainPieceOfCurrentPlayersColor(7, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceWhenRowIsInvalid() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(-1, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceWhenColumnIsInvalid() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(0, -1));
    }

    @Test
    public void testGetAvailableMovesForPieceForOppositeColorWhenPlayerIsWhite() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(0, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForOppositeColorWhenPlayerIsBlack() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(7, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForEmptySpace() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(2, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceWhenGameIsOver() {
        Pieces[][] initialState =  {{BK, NN, NN, NN, NN, NN, WR, NN},
                                    {NN, NN, NN, NN, NN, NN, WQ, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, WK}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertThrows(IllegalArgumentException.class, () -> game.getAvailableMovesForTile(0, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingsAdjacentSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WR, NN, NN},
                                    {NN, NN, NN, NN, WR, NN, NN, WK},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, T, T},
                                                    {F, F, F, F, F, F, T, F},
                                                    {F, F, F, F, F, F, T, T}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 7));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingsAdjacentSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WR, NN, NN},
                                    {NN, NN, NN, NN, WR, NN, NN, WK},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {T, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingsAdjacentSpacesWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BR, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WR, NN, NN},
                                    {NN, WR, NN, NN, NN, NN, NN, WK},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, T},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 7));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingsAdjacentSpacesWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BR, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WR, NN, NN},
                                    {NN, WR, NN, NN, NN, NN, NN, WK},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingsAdjacentSpacesWithSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BR, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BR, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, WR, NN},
                                    {NN, WR, NN, NN, NN, NN, WK, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, T, F, T},
                                                    {F, F, F, F, F, T, F, T},
                                                    {F, F, F, F, F, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 6));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingsAdjacentSpacesWithSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BR, NN},
                                    {BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BR, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, WR, NN},
                                    {NN, WR, NN, NN, NN, NN, WK, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingRightCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, T, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingRightCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, T, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingLeftCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingLeftCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingRightCastleWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, BR, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, T, T, F, T, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingRightCastleWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, WR, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, T, T, F, T, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingLeftCastleWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, BR, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, T, F, T, T, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingLeftCastleWhenItPutsItInCheck() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WR, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, T, F, T, T, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingToCastleWhenInCheck() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, BQ, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, F, T, F, F},
                                                    {F, F, F, T, F, T, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingToCastleWhenInCheck() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WQ, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, T, F, T, F, F},
                                                    {F, F, F, T, F, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingToCastleWhenSpacesAreNotEmpty() {
        Pieces[][] initialState =  {{BR, Bk, NN, NN, BK, NN, Bk, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, Wk, NN, NN, WK, NN, Wk, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, T, F, T, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingToCastleWhenSpacesAreNotEmpty() {
        Pieces[][] initialState =  {{BR, Bk, NN, NN, BK, NN, Bk, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, Wk, NN, NN, WK, NN, Wk, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, T, F, T, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingToCastleWhenKingHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 4, 6, 4);
        game.move(0, 4, 1, 4);
        game.move(6, 4, 7, 4);
        game.move(1, 4, 0, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, T, F, T, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingToCastleWhenKingHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 4, 1, 4);
        game.move(7, 4, 6, 4);
        game.move(1, 4, 0, 4);
        game.move(6, 4, 7, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, T, F, T, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingToCastleWhenRightRookHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 7, 6, 7);
        game.move(0, 4, 0, 5);
        game.move(6, 7, 7, 7);
        game.move(0, 5, 0, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, T, T, F, T, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingToCastleWhenRightRookHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 7, 1, 7);
        game.move(7, 4, 6, 4);
        game.move(1, 7, 0, 7);
        game.move(6, 4, 7, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, T, T, F, T, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKingToCastleWhenLeftRookHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 0, 6, 0);
        game.move(0, 4, 0, 5);
        game.move(6, 0, 7, 0);
        game.move(0, 5, 0, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, T, F, T, T, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(7, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKingToCastleWhenLeftRookHasBeenMoved() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 0, 1, 0);
        game.move(7, 4, 6, 4);
        game.move(1, 0, 0, 0);
        game.move(6, 4, 7, 4);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, T, F, T, T, F},
                                                    {F, F, F, T, T, T, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 4));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenEmptyDiagonalSpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WQ, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, T},
                                                    {F, F, F, F, T, F, T, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, T, F, T, F},
                                                    {F, F, F, T, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenEmptyDiagonalSpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BQ, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, T, F},
                                                    {F, T, F, F, F, T, F, F},
                                                    {F, F, T, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, T, F, F, F},
                                                    {F, T, F, F, F, T, F, F},
                                                    {F, F, F, F, F, F, T, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenDiagonalSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WQ, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenDiagonalSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BQ, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenDiagonalSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WQ, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenDiagonalSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BQ, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, F},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenRectangularEmptySpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, T, T, T, T, T, T, T},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenRectangularEmptySpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, T, T, T, T, T, T, T},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenRectangularSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenRectangularSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenRectangularSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {F, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenRectangularSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BQ, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteQueenUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {WQ, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {T, T, F, F, F, F, F, F},
                                                    {F, T, T, T, F, F, F, F},
                                                    {T, T, F, F, F, F, F, F},
                                                    {T, F, T, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackQueenUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {BQ, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, T, T, T, T, F, F, F},
                                                    {T, T, F, F, F, F, F, F},
                                                    {T, F, T, F, F, F, F, F},
                                                    {T, F, F, T, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteBishopEmptyDiagonalSpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WB, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, T},
                                                    {F, F, F, F, T, F, T, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, T, F, T, F},
                                                    {F, F, F, T, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackBishopEmptyDiagonalSpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BB, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, T, F},
                                                    {F, T, F, F, F, T, F, F},
                                                    {F, F, T, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, T, F, F, F},
                                                    {F, T, F, F, F, T, F, F},
                                                    {F, F, F, F, F, F, T, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteBishopDiagonalSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WB, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackBishopDiagonalSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BB, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteBishopDiagonalSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WB, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 5));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackBishopDiagonalSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BB, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, F},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 3));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteBishopUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {WB, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackBishopUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {BB, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteRookRectangularEmptySpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, T, T, T, T, T, T, T},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackRookRectangularEmptySpaces() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, T, T, T, T, T, T, T},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteRookRectangularSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackRookRectangularSpacesWithOppositeColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteRookRectangularSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BB, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {F, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackRookRectangularSpacesWithSameColor() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteRookUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, T, T, T, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackRookUnavailableSpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BB, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, NN, NN, NN, NN},
                                    {BR, NN, NN, NN, WB, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, T, T, T, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKnightEmptySpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, Wk, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKnightEmptySpaces() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, Bk, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKnightSpacesWithTheOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, Bk, NN, Bk, NN, NN, NN, NN},
                                    {Bk, NN, NN, NN, Bk, NN, NN, NN},
                                    {NN, NN, Wk, NN, NN, NN, NN, NN},
                                    {Bk, NN, NN, NN, Bk, NN, NN, NN},
                                    {NN, Bk, NN, Bk, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKnightSpacesWithTheOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BK, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, Wk, NN, Wk, NN, NN, NN, NN},
                                    {Wk, NN, NN, NN, Wk, NN, NN, NN},
                                    {NN, NN, Bk, NN, NN, NN, NN, NN},
                                    {Wk, NN, NN, NN, Wk, NN, NN, NN},
                                    {NN, Wk, NN, Wk, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, T, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhiteKnightSpacesWithTheSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, NN, NN, BK, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, Wk, NN, Wk, NN, NN, NN, NN},
                                    {Wk, NN, NN, NN, Wk, NN, NN, NN},
                                    {NN, NN, Wk, NN, NN, NN, NN, NN},
                                    {Wk, NN, NN, NN, Wk, NN, NN, NN},
                                    {NN, Wk, NN, Wk, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackKnightSpacesWithTheSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, Bk, NN, Bk, NN, NN, NN, NN},
                                    {Bk, NN, NN, NN, Bk, NN, NN, NN},
                                    {NN, NN, Bk, NN, NN, NN, NN, NN},
                                    {Bk, NN, NN, NN, Bk, NN, NN, NN},
                                    {NN, Bk, NN, Bk, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T},
                                                    {T, T, T, T, T, T, T, T}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptySpaceInFront() {
        ChessGameInterface game = new ChessGame();

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptySpaceInFront() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnFrontSpaceContainsPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnFrontSpaceContainsPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnFrontSpaceContainsPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnFrontSpaceContainsPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoEmptySpacesInFrontIfPawnWasMoved() {
        ChessGameInterface game = new ChessGame();

        game.move(6, 0, 5, 0);
        game.move(1, 0, 2, 0);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(5, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoEmptySpacesInFrontIfPawnWasMoved() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);

        game.move(1, 0, 2, 0);
        game.move(6, 0, 5, 0);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(2, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoEmptySpacesInFront() {
        ChessGameInterface game = new ChessGame();

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 0));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoEmptySpacesInFront() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 0));
    }


    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoSpacesInFrontWithPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoSpacesInFrontWithPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoSpacesInFrontWithPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoSpacesInFrontWithPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoSpacesInFrontWhenFirstSpaceContainsPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoSpacesInFrontWhenFirstSpaceContainsPieceOfSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnTwoSpacesInFrontWhenFirstSpaceContainsPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BP, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnTwoSpacesInFrontWhenFirstSpaceContainsPieceOfOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnDiagonalWithOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, BP, BP, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnDiagonalWithOppositeColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {WP, WP, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnDiagonalWithSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, BP, WP, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnDiagonalWithSameColor() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {BP, WP, BP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyDiagonal() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BP, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(6, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyDiagonal() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(1, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyLeftDiagonalWithAdjacentPawnMovedTwoSpacesLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 1, 3, 1);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyLeftDiagonalWithAdjacentPawnMovedTwoSpacesLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 2, 4, 2);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyRightDiagonalWithAdjacentPawnMovedTwoSpacesLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, BP, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 3, 3, 3);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, T, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyRightDiagonalWithAdjacentPawnMovedTwoSpacesLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 0, 4, 0);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {T, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyLeftDiagonalWithAdjacentPawnMovedTwoSpacesNotOnLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 1, 3, 1);
        game.move(7, 4, 7, 5);
        game.move(0, 4, 0, 5);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyLeftDiagonalWithAdjacentPawnMovedTwoSpacesNotOnLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 2, 4, 2);
        game.move(0, 4, 0, 5);
        game.move(7, 4, 7, 5);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyRightDiagonalWithAdjacentPawnMovedTwoSpacesNotOnLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, BP, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 3, 3, 3);
        game.move(7, 4, 7, 5);
        game.move(0, 4, 0, 5);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyRightDiagonalWithAdjacentPawnMovedTwoSpacesNotOnLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 0, 4, 0);
        game.move(0, 4, 0, 5);
        game.move(7, 4, 7, 5);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyLeftDiagonalWithAdjacentPawnMovedOneSpaceLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 1, 2, 1);
        game.move(7, 4, 7, 5);
        game.move(2, 1, 3, 1);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyLeftDiagonalWithAdjacentPawnMovedOneSpaceLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 2, 5, 2);
        game.move(0, 4, 0, 5);
        game.move(5, 2, 4, 2);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePawnEmptyRightDiagonalWithAdjacentPawnMovedOneSpaceLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, BP, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 3, 2, 3);
        game.move(7, 4, 7, 5);
        game.move(2, 3, 3, 3);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, T, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(3, 2));
    }

    @Test
    public void testGetAvailableMovesForPieceForBlackPawnEmptyRightDiagonalWithAdjacentPawnMovedOneSpaceLastTurn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 0, 5, 0);
        game.move(0, 4, 0, 5);
        game.move(5, 0, 4, 0);

        boolean[][] expectedSpacesContainedInSet = {{F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, T, F, F, F, F, F, F},
                                                    {F, F, F, F, F, F, F, F}};
        spacesAreNotContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(4, 1));
    }

    @Test
    public void testGetAvailableMovesForPieceForWhitePromotedPawn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(1, 1, 0, 1);
        game.move(0, 4, 1, 4);

        boolean[][] expectedSpacesContainedInSet = {{T, F, T, T, T, T, T, T},
                                                    {T, T, T, F, F, F, F, F},
                                                    {F, T, F, T, F, F, F, F},
                                                    {F, T, F, F, T, F, F, F},
                                                    {F, T, F, F, F, T, F, F},
                                                    {F, T, F, F, F, F, T, F},
                                                    {F, T, F, F, F, F, F, T},
                                                    {F, T, F, F, F, F, F, F}};
        spacesAreContainedInSet(expectedSpacesContainedInSet, game.getAvailableMovesForTile(0, 1));
    }

    @Test
    public void testMoveWhenStartingRowIsInvalid() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(-1, 0, 0,  0));
    }

    @Test
    public void testMoveWhenStartingColumnIsInvalid() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(0, -1, 0,  0));
    }

    @Test
    public void testMoveWhenEndingRowIsInvalid() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(0, 0, -1,  0));
    }

    @Test
    public void testMoveWhenEndingColumnIsInvalid() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(0, 0, 0,  -1));
    }

    @Test
    public void testMoveWhenStartingSpaceIsEmpty() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(2, 0, 3,  0));
    }

    @Test
    public void testMoveWhenStartingSpaceContainsOppositeColorWhenPlayerIsWhite() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(1, 0, 3,  0));
    }

    @Test
    public void testMoveWhenStartingSpaceContainsOppositeColorWhenPlayerIsBlack() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);

        assertThrows(IllegalArgumentException.class, () -> game.move(6, 0, 4,  0));
    }

    @Test
    public void testMoveWhenEndingSpaceIsNotAvailable() {
        ChessGameInterface game = new ChessGame();

        assertThrows(IllegalArgumentException.class, () -> game.move(6, 0, 3,  0));
    }

    @Test
    public void testMoveForStandardMove() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, BP, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(2, 1, 1, 2);

        Pieces[][] expectedGameState = {{NN, NN, NN, NN, BK, NN, NN, NN},
                                        {NN, NN, WP, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {WP, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, WK, NN, NN, NN}};
        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveForWhiteKingLeftCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 4, 7, 2);

        Pieces[][] expectedGameState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, WK, WR, NN, NN, NN, WR}};

        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveForWhiteKingRightCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 4, 7, 6);

        Pieces[][] expectedGameState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {WR, NN, NN, NN, NN, WR, WK, NN}};

        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveForBlackKingLeftCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 4, 0, 2);

        Pieces[][] expectedGameState =  {{NN, NN, BK, BR, NN, NN, NN, BR},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {WR, NN, NN, NN, WK, NN, NN, WR}};

        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveForBlackKingRightCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 4, 0, 6);

        Pieces[][] expectedGameState =  {{BR, NN, NN, NN, NN, BR, BK, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {WR, NN, NN, NN, WK, NN, NN, WR}};

        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveCapturesEnPassantForWhitePawn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, BP, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(1, 3, 3, 3);
        game.move(3, 2, 2, 3);

        Pieces[][] expectedGameState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, WP, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, WK, NN, NN, NN}};
        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveCapturesEnPassantForBlackPawn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 2, 4, 2);
        game.move(4, 1, 5, 2);

        Pieces[][] expectedGameState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, BP, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, NN, NN, NN, NN},
                                         {NN, NN, NN, NN, WK, NN, NN, NN}};
        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveWhitePromotion() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(1, 2, 0, 2);

        Pieces[][] expectedGameState = {{NN, NN, WQ, NN, BK, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, BP, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, WP, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, WK, NN, NN, NN}};
        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testMoveBlackPromotion() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(6, 1, 7, 1);

        Pieces[][] expectedGameState = {{NN, NN, NN, NN, BK, NN, NN, NN},
                                        {NN, NN, WP, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, BP, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, NN, NN, NN, NN, NN, NN},
                                        {NN, NN, WP, NN, NN, NN, NN, NN},
                                        {NN, BQ, NN, NN, WK, NN, NN, NN}};
        assertArrayEquals(expectedGameState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveWhenThereWasNoPreviousMove() {
        ChessGameInterface game = new ChessGame();
        assertThrows(IllegalArgumentException.class, () -> game.undoLastMove());
    }

    @Test
    public void testUndoLastMoveForStandardMove() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, BP, NN, NN, NN, NN, NN},
                                    {NN, WP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WP, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(2, 1, 1, 2);
        game.undoLastMove();
        assertArrayEquals(initialState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveForCastle() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(0, 4, 0, 6);
        game.undoLastMove();

        assertArrayEquals(initialState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveForEnPassant() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(6, 2, 4, 2);
        game.move(4, 1, 5, 2);
        game.undoLastMove();
        game.undoLastMove();

        assertArrayEquals(initialState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveForPromotion() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, BP, WP, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WK, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);

        game.move(6, 1, 7, 1);
        game.undoLastMove();

        assertArrayEquals(initialState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveResetsMovementStatusForPiecesMovedFromLastTurn() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 0, 7, 1);
        game.undoLastMove();
        game.move(7, 4, 7, 2);
        game.undoLastMove();

        assertArrayEquals(initialState, game.getBoardState());
    }

    @Test
    public void testUndoLastMoveResetsMovementStatusForPiecesMovedNotFromLastTurn() {
        Pieces[][] initialState =  {{BR, NN, NN, NN, BK, NN, NN, BR},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WR, NN, NN, NN, WK, NN, NN, WR}};
        ChessGameInterface game = new ChessGame(initialState);

        game.move(7, 0, 7, 1);
        game.move(0, 0, 0, 1);
        game.move(7, 1, 7, 0);
        game.move(0, 1, 0, 0);
        game.move(7, 0, 7, 1);
        game.undoLastMove();

        assertThrows(IllegalArgumentException.class, () -> game.move(7, 4, 7, 2));
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackKing() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, WK, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackQueen() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, BQ, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackRook() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BR, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackBishop() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BB, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackKnight() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, Bk, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingByBlackPawn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, BP, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckWhiteKingWhenNotInCheck() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(!game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhiteKing() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, WK, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhiteQueen() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, WK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, WQ, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, BK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhiteRook() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, WK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WR, NN, NN, BK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhiteBishop() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, WK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, WB, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, BK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhiteKnight() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, WK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, Wk, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, BK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingByWhitePawn() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, WK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, BK, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, WP, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckBlackKingWhenNotInCheck() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(!game.isCurrentPlayerInCheck());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenWhiteIsInCheckMate() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BR, NN, NN, NN, NN, NN},
                                    {NN, NN, BR, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenBlackIsInCheckMate() {
        Pieces[][] initialState =  {{NN, NN, WR, NN, BK, NN, NN, NN},
                                    {NN, NN, WR, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenWhiteIsInStaleMate() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BQ, NN, NN, NN, NN, NN},
                                    {WK, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(!game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenBlackIsInStaleMate() {
        Pieces[][] initialState =  {{BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WQ, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(!game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenWhiteIsInCheckAndHasMovesLeft() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, BQ, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WK, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(!game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInCheckMateWhenBlackIsInCheckAndHasMovesLeft() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WQ, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BQ, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {WK, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(!game.isCurrentPlayerInCheckmate());
    }

    @Test
    public void testIsCurrentPlayerInStaleMateWhenWhiteIsInStaleMate() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BQ, NN, NN, NN, NN, NN},
                                    {WK, NN, NN, NN, NN, NN, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(game.isGameInStalemate());
    }

    @Test
    public void testIsCurrentPlayerInStaleMateWhenBlackIsInStaleMate() {
        Pieces[][] initialState =  {{BK, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, WQ, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(game.isGameInStalemate());
    }

    @Test
    public void testIsCurrentPlayerInStaleMateWhenWhiteIsInCheckMate() {
        Pieces[][] initialState =  {{NN, NN, NN, NN, BK, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, BR, NN, NN, NN, NN, NN},
                                    {NN, NN, BR, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(initialState);
        assertTrue(!game.isGameInStalemate());
    }

    @Test
    public void testIsCurrentPlayerInStaleMateWhenBlackIsInCheckMate() {
        Pieces[][] initialState =  {{NN, NN, WR, NN, BK, NN, NN, NN},
                                    {NN, NN, WR, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                    {NN, NN, NN, NN, NN, WK, NN, NN}};
        ChessGameInterface game = new ChessGame(Colors.BLACK, initialState);
        assertTrue(!game.isGameInStalemate());
    }

    @Test
    public void testIsGameInStalemateWhenWhiteNotInCheckAndHasMovesLeft() {
        ChessGameInterface game = new ChessGame();
        assertTrue(!game.isGameInStalemate());
    }

    @Test
    public void testIsGameInStalemateWhenBlackNotInCheckAndHasMovesLeft() {
        ChessGameInterface game = new ChessGame(Colors.BLACK);
        assertTrue(!game.isGameInStalemate());
    }
}
