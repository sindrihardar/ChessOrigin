import com.chess.model.ChessGame;
import com.chess.model.ChessGameInterface;
import com.chess.model.Pieces;
import com.chess.model.Space;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.chess.model.Pieces.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChessGameInterfaceTestSuite {
    private ChessGameInterface game;

    private static final boolean T = true, F = false;

    private static final Pieces[][] TEST_STATE_1 = {{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, null},
                                                    {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, null, null, BLACK_PAWN, BLACK_PAWN, WHITE_PAWN},
                                                    {null, WHITE_KNIGHT, null, null, BLACK_PAWN, null, null, null},
                                                    {null, null, null, BLACK_PAWN, null, null, null, BLACK_ROOK},
                                                    {null, WHITE_QUEEN, null, null, WHITE_PAWN, WHITE_BISHOP, WHITE_KNIGHT, null},
                                                    {null, null, null, WHITE_PAWN, null, null, null, null},
                                                    {WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, null, WHITE_BISHOP, WHITE_PAWN, BLACK_PAWN, null},
                                                    {WHITE_ROOK, null, null, null, WHITE_KING, null, null, WHITE_ROOK}};

    private static final Pieces[][] TEST_STATE_2 = {{BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, null, BLACK_BISHOP, BLACK_KNIGHT, null},
                                                    {BLACK_PAWN, null, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, null, null, WHITE_PAWN},
                                                    {BLACK_PAWN, null, WHITE_KNIGHT, null, null, BLACK_PAWN, null, null},
                                                    {null, BLACK_KING, null, null, null, null, BLACK_PAWN, null},
                                                    {null, null, null, null, null, null, WHITE_KING, null},
                                                    {null, WHITE_PAWN, WHITE_QUEEN, null, WHITE_BISHOP, null, null, null},
                                                    {null, null, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, BLACK_PAWN},
                                                    {null, WHITE_ROOK, null, null, null, WHITE_BISHOP, WHITE_KNIGHT, null}};

    private static final Pieces[][] TEST_STATE_3 = {{BLACK_ROOK, null, null, null, BLACK_KING, null, null, BLACK_ROOK},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {WHITE_ROOK, null, null, null, WHITE_KING, null, null, WHITE_ROOK}};

    private static final Pieces[][] TEST_STATE_4 = {{BLACK_ROOK, BLACK_KNIGHT, null, null, BLACK_KING, null, BLACK_KNIGHT, BLACK_ROOK},
                                                    {null, null, null, null, WHITE_PAWN, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, BLACK_PAWN, null, null, null},
                                                    {WHITE_ROOK, WHITE_KNIGHT, null, null, WHITE_KING, null, WHITE_KNIGHT, WHITE_ROOK}};

    private static final Pieces[][] TEST_STATE_5 = {{BLACK_ROOK, null, null, null, BLACK_KING, null, null, BLACK_ROOK},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {BLACK_PAWN, null, null, null, WHITE_QUEEN, null, null, BLACK_PAWN},
                                                    {WHITE_PAWN, null, null, null, BLACK_QUEEN, null, null, WHITE_PAWN},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {WHITE_ROOK, null, null, null, WHITE_KING, null, null, WHITE_ROOK}};

    private static final Pieces[][] TEST_STATE_6 = {{BLACK_KING, null, null, null, null, null, null, null},
                                                    {null, null, null, WHITE_QUEEN, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, WHITE_KING, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null},
                                                    {null, null, null, null, null, null, null, null}};

    private static final Pieces[][] TEST_STATE_7 = {{BLACK_ROOK, null, null, null, BLACK_KING, null, null, BLACK_ROOK},
                                                    {null, null, null, null, WHITE_PAWN, null, null, null},
                                                    {null, null, null, null, WHITE_QUEEN, null, null, null},
                                                    {BLACK_PAWN, null, null, null, null, null, null, BLACK_PAWN},
                                                    {WHITE_PAWN, null, null, null, null, null, null, WHITE_PAWN},
                                                    {null, null, null, null, BLACK_QUEEN, null, null, null},
                                                    {null, null, null, null, BLACK_PAWN, null, null, null},
                                                    {WHITE_ROOK, null, null, null, WHITE_KING, null, null, WHITE_ROOK}};

    /*
     * Helper method that checks if the given 2D array of spaces is contained in the given set of spaces.
     *
     * Spaces marked as 'true' in the 2D array should be present.
     */
    public void setContainsSpaces(Set<Space> set, boolean[][] spaces) {

    }

    /*
     * Helper method that checks if the given 2D array of spaces is not contained in the given set of spaces.
     *
     * Spaces marked as 'true' in the 2D array should not be present.
     */
    public void setDoesNotContainSpaces(Set<Space> set, boolean[][] spaces) {
        int numberOfUnavailableSpaces = 0;
        for (boolean[] row : spaces)
            for (boolean val : row)
                numberOfUnavailableSpaces += val ? 1 : 0;

        Space[] expectedUnavailableMoves = new Space[numberOfUnavailableSpaces];

        int k = 0;
        for (int i = 0; i < spaces.length; i++)
            for (int j = 0; j < spaces[0].length; i++)
                if (spaces[i][j])
                    expectedUnavailableMoves[k++] = new Space(i, j);

        setDoesNotContainSpaces(set, expectedUnavailableMoves);
    }

    /*
     * Helper method that checks if the given set of spaces contains the given list of spaces.
     */
    public void setContainsSpaces(Set<Space> set, Space[] spaces) {
        for (Space space : spaces)
            assertTrue(set.contains(space));
    }

    /*
     * Helper method that checks if the given set of spaces does not contain the given list of spaces.
     */
    public void setDoesNotContainSpaces(Set<Space> set, Space[] spaces) {
        for (Space space : spaces)
            assertTrue(!set.contains(space));
    }

    /*
     * Helper method that takes the complement of a 2D array of booleans.
     */
    public void complement(boolean[][] spaces) {
        for (int i = 0; i < spaces.length; i++)
            for (int j = 0; j < spaces[i].length; j++)
                spaces[i][j] = !spaces[i][j];
    }

    @AfterEach
    public void tearDown() {
        game = null;
    }

    @Test
    public void test1() {
        game = new ChessGame(TEST_STATE_1);

        // check adjacent spaces for white king
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedAvailableMoves = {new Space(7, 3), new Space(7, 5), new Space(6, 3)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);

        game.move(4, 1, 5, 1); // moves queen back a space so that it becomes black's turn

        // check adjacent spaces for black king
        availableMoves = game.getAvailableMovesForPiece(0, 4);
        expectedAvailableMoves = new Space[] {new Space(1, 3), new Space(1, 4)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test2() {
        game = new ChessGame(TEST_STATE_1);

        // check adjacent spaces for the white king that contain pieces of the same color
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(6, 4), new Space(6, 5)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);

        game.move(4, 1, 5, 1); // moves queen back a space so that it becomes black's turn

        // check adjacent spaces for the black king that contain pieces of the same color
        availableMoves = game.getAvailableMovesForPiece(0, 4);
        expectedUnavailableMoves = new Space[] {new Space(0, 3), new Space(0, 5), new Space(1, 5)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test3() {
        game = new ChessGame(TEST_STATE_4);

        // check adjacent spaces for the white king that contain pieces of the opposite color
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedAvailableMoves = {new Space(6, 4)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);

        game.move(7, 1, 5, 2); // moves knight so that it becomes black's turn

        // check adjacent spaces for the black king that contain pieces of the opposite color
        availableMoves = game.getAvailableMovesForPiece(0, 4);
        expectedAvailableMoves = new Space[] {new Space(1, 4)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test4() {
        game = new ChessGame(TEST_STATE_1);

        // check adjacent spaces for the white king to make sure that spaces out of bounds aren't included
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(8, 4), new Space(8, 3), new Space(8, 5)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);

        game.move(4, 1, 5, 1); // moves queen back a space so that it becomes black's turn

        // check adjacent spaces for the white king to make sure that spaces out of bounds aren't included
        availableMoves = game.getAvailableMovesForPiece(0, 4);
        expectedUnavailableMoves = new Space[] {new Space(-1, 4), new Space(-1, 3), new Space(-1, 5)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test5() {
        game = new ChessGame(TEST_STATE_2);

        // check adjacent spaces for the white king that would put it in check to make sure it can't move there
        Set<Space> availableMoves = game.getAvailableMovesForPiece(4, 6);
        Space[] expectedUnavailableMoves = {new Space(4, 7), new Space(4, 5)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);

        game.move(7, 1, 6, 1); // moves rook up a space to make it black's turn

        // check adjacent spaces for the black king that would put it in check to make sure it can't move there
        availableMoves = game.getAvailableMovesForPiece(3, 1);
        expectedUnavailableMoves = new Space[] {new Space(4, 0), new Space(4, 1), new Space(4, 2),
                                                new Space(3, 0), new Space(3, 2), new Space(2, 1), new Space(2, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test6() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedAvailableMoves = {new Space(0, 6)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test7() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedAvailableMoves = {new Space(0, 2)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test8() {
        game = new ChessGame(TEST_STATE_3);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedAvailableMoves = {new Space(7, 6)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test9() {
        game = new ChessGame(TEST_STATE_3);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedAvailableMoves = {new Space(7, 2)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test10() {
        game = new ChessGame(TEST_STATE_4);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test11() {
        game = new ChessGame(TEST_STATE_4);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test12() {
        game = new ChessGame(TEST_STATE_4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test13() {
        game = new ChessGame(TEST_STATE_4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test14() {
        game = new ChessGame(TEST_STATE_5);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test15() {
        game = new ChessGame(TEST_STATE_5);

        game.move(7, 0, 6, 0); // moves rook up a space to make it black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test16() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test17() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test18() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0);
        game.move(0, 4, 1, 4);
        game.move(6, 0, 7, 0);
        game.move(1, 4, 0, 4);
        game.move(7, 0, 6, 0);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test19() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0);
        game.move(0, 4, 1, 4);
        game.move(6, 0, 7, 0);
        game.move(1, 4, 0, 4);
        game.move(7, 0, 6, 0);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test20() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 4, 6, 4);
        game.move(0, 4, 1, 4);
        game.move(6, 4, 7, 4);
        game.move(1, 4, 0, 4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test21() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 4, 6, 4);
        game.move(0, 4, 1, 4);
        game.move(6, 4, 7, 4);
        game.move(1, 4, 0, 4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test22() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0);
        game.move(0, 7, 1, 7);
        game.move(6, 0, 7, 0);
        game.move(1, 7, 0, 7);
        game.move(7, 0, 6, 0);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test23() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0);
        game.move(0, 0, 1, 0);
        game.move(6, 0, 7, 0);
        game.move(1, 0, 0, 0);
        game.move(7, 0, 6, 0);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(0, 4);
        Space[] expectedUnavailableMoves = {new Space(0, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test24() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 7, 6, 7);
        game.move(0, 4, 1, 4);
        game.move(6, 7, 7, 7);
        game.move(1, 4, 0, 4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 6)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test25() {
        game = new ChessGame(TEST_STATE_3);

        game.move(7, 0, 6, 0);
        game.move(0, 4, 1, 4);
        game.move(6, 0, 7, 0);
        game.move(1, 4, 0, 4);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test26() {
        game = new ChessGame(TEST_STATE_7);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test27() {
        game = new ChessGame(TEST_STATE_5);

        Space[] expectedUnavailableMoves = new Space[56];
        for (int i = 0; i < 6; i++)
            for (int j = 0; j < 8; j++)
                expectedUnavailableMoves[i * 8 + j] = new Space(i, j);
        expectedUnavailableMoves[6 * 8] = new Space(6, 0);
        expectedUnavailableMoves[6 * 8 + 1] = new Space(6, 1);
        expectedUnavailableMoves[6 * 8 + 2] = new Space(6, 2);
        expectedUnavailableMoves[6 * 8 + 3] = new Space(6, 6);
        expectedUnavailableMoves[6 * 8 + 4] = new Space(6, 7);
        expectedUnavailableMoves[6 * 8 + 5] = new Space(7, 1);
        expectedUnavailableMoves[6 * 8 + 6] = new Space(7, 2);
        expectedUnavailableMoves[6 * 8 + 7] = new Space(7, 7);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 4);
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test28() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(3, 4);
        Space[] expectedAvailableMoves = {new Space(4, 3), new Space(5, 2), new Space(6, 1),
                                          new Space(2, 5), new Space(1, 6), new Space(0, 7)};
        setDoesNotContainSpaces(availableMoves, expectedAvailableMoves);

        game.move(3, 4, 2, 4); // move the white queen up to switch to black's turn

        availableMoves = game.getAvailableMovesForPiece(4, 4);
        expectedAvailableMoves = new Space[] {new Space(5, 5), new Space(6, 6), new Space(7, 7),
                                 new Space(3, 3), new Space(2, 2), new Space(1, 1)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test29() {
        game = new ChessGame(TEST_STATE_7);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(2, 4);
        Space[] expectedAvailableMoves = {new Space(3, 3), new Space(4, 2), new Space(5, 1), new Space(6, 0)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test30() {
        game = new ChessGame(TEST_STATE_7);

        game.move(2, 4, 3, 4); // moves the white queen down a space so that it's black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(5, 4);
        Space[] expectedAvailableMoves = {new Space(4, 5), new Space(3, 6), new Space(2, 7)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test31() {
        game = new ChessGame(TEST_STATE_7);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(2, 4);
        Space[] expectedAvailableMoves = {new Space(1, 5), new Space(0, 6)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test32() {
        game = new ChessGame(TEST_STATE_7);

        game.move(2, 4, 3, 4); // moves the white queen down a space so that it's black's turn

        Set<Space> availableMoves = game.getAvailableMovesForPiece(5, 4);
        Space[] expectedAvailableMoves = {new Space(6, 5), new Space(7, 6)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test33() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(3, 4);
        Space[] expectedAvailableMoves = {new Space(3, 5), new Space(3, 6), new Space(3, 3),
                                          new Space(3, 2), new Space(3, 1), new Space(2, 4),
                                          new Space(1, 4)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test34() {
        game = new ChessGame(TEST_STATE_7);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(2, 4);
        Space[] expectedAvailableMoves = {new Space(2, 3), new Space(2, 2), new Space(2, 1),
                                          new Space(2, 0), new Space(2, 5), new Space(2, 6),
                                          new Space(2, 7)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test35() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(3, 4);
        Space[] expectedUnavailableMoves = {new Space(7, 0)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test36() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(3, 4);
        Space[] expectedAvailableMoves = {new Space(0, 7)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test37() {
        game = new ChessGame(TEST_STATE_2);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(5, 2);
        Space[] expectedUnavailableMoves = {new Space(6, 2)};
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test38() {
        game = new ChessGame(TEST_STATE_5);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(3, 4);
        Space[] expectedAvailableMoves = {new Space(4, 4)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test39() {
        game = new ChessGame();

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 3);
        assertTrue(availableMoves.size() == 0);
    }

    @Test
    public void test40() {
        game = new ChessGame(TEST_STATE_2);

        Set<Space> availableMoves = game.getAvailableMovesForPiece(5, 4);
        Space[] expectedAvailableMoves = {new Space(4, 3), new Space(3, 2), new Space(2, 1), new Space(4, 6)};
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test41() {
        game = new ChessGame(TEST_STATE_2);

        boolean[][] unavailableSpaces = {{T, T, T, T, T, T, T, T},
                                         {F, T, T, T, T, T, T, T},
                                         {T, F, T, T, T, T, T, T},
                                         {T, T, F, T, T, T, F, T},
                                         {T, T, T, F, T, F, T, T},
                                         {T, T, T, T, T, T, T, T},
                                         {T, T, T, T, T, T, T, T}};

        Set<Space> availableMoves = game.getAvailableMovesForPiece(5, 4);
        setDoesNotContainSpaces(availableMoves, unavailableSpaces);
    }

    @Test
    public void test42() {
        game = new ChessGame(TEST_STATE_3);

        boolean[][] unavailableSpaces = {{T, F, F, F, F, F, F, F},
                                         {T, F, F, F, F, F, F, F},
                                         {T, F, F, F, F, F, F, F},
                                         {T, F, F, F, F, F, F, F},
                                         {T, F, F, F, F, F, F, F},
                                         {T, F, F, F, F, F, F, F},
                                         {F, T, T, T, F, F, F, F}};

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 0);
        setContainsSpaces(availableMoves, unavailableSpaces);
    }

    @Test
    public void test43() {
        game = new ChessGame(TEST_STATE_3);

        boolean[][] unavailableSpaces = {{T, F, F, F, F, F, F, F},
                                        {T, F, F, F, F, F, F, F},
                                        {T, F, F, F, F, F, F, F},
                                        {T, F, F, F, F, F, F, F},
                                        {T, F, F, F, F, F, F, F},
                                        {T, F, F, F, F, F, F, F},
                                        {F, T, T, T, F, F, F, F}};

        complement(unavailableSpaces);
        boolean[][] availableSpaces = unavailableSpaces;
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 0);
        setDoesNotContainSpaces(availableMoves, unavailableSpaces);
    }

    @Test
    public void test44() {
        game = new ChessGame(TEST_STATE_4);

        boolean[][] availableSpacesBottomLeftWhiteKnight = {{F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {T, F, T, F, F, F, F, F},
                                                            {F, F, F, T, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F}};
        boolean[][] availableSpacesBottomRightWhiteKnight = {{F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, F, F, F},
                                                            {F, F, F, F, F, T, F, T},
                                                            {F, F, F, F, T, F, F, F},
                                                            {F, F, F, F, F, F, F, F}};
        boolean[][] availableSpacesTopLeftBlackKnight = {{F, F, F, F, F, F, F, F},
                                                        {F, F, F, T, F, F, F, F},
                                                        {T, F, T, F, F, F, F, F},
                                                        {F, F, F, F, F, F, F, F},
                                                        {F, F, F, F, F, F, F, F},
                                                        {F, F, F, F, F, F, F, F},
                                                        {F, F, F, F, F, F, F, F},
                                                        {F, F, F, F, F, F, F, F}};
        boolean[][] availableSpacesTopRightBlackKnight = {{F, F, F, F, F, F, F, F},
                                                         {F, F, F, F, T, F, F, F},
                                                         {F, F, F, F, F, T, F, T},
                                                         {F, F, F, F, F, F, F, F},
                                                         {F, F, F, F, F, F, F, F},
                                                         {F, F, F, F, F, F, F, F},
                                                         {F, F, F, F, F, F, F, F},
                                                         {F, F, F, F, F, F, F, F}};

        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 1);
        setDoesNotContainSpaces(availableMoves, availableSpacesBottomLeftWhiteKnight);
        availableMoves = game.getAvailableMovesForPiece(7, 6);
        setDoesNotContainSpaces(availableMoves, availableSpacesBottomRightWhiteKnight);

        game.move(7, 4, 6, 4);

        availableMoves = game.getAvailableMovesForPiece(0, 1);
        setDoesNotContainSpaces(availableMoves, availableSpacesTopLeftBlackKnight);
        availableMoves = game.getAvailableMovesForPiece(6, 1);
        setDoesNotContainSpaces(availableMoves, availableSpacesTopRightBlackKnight);
    }

    @Test
    public void test45() {
        game = new ChessGame(TEST_STATE_4);

        Space[] expectedUnavailableMoves = {new Space(8, 3)};
        Set<Space> availableMoves = game.getAvailableMovesForPiece(7, 1);
        setDoesNotContainSpaces(availableMoves, expectedUnavailableMoves);
    }

    @Test
    public void test46() {
        game = new ChessGame(TEST_STATE_2);

        Space[] expectedAvailableMoves = {new Space(0, 1), new Space(0, 3)};
        Set<Space> availableMoves = game.getAvailableMovesForPiece(2, 2);
        setContainsSpaces(availableMoves, expectedAvailableMoves);
    }

    @Test
    public void test47() {

    }

    @Test
    public void test48() {

    }

    @Test
    public void test49() {

    }

    @Test
    public void test50() {

    }

    @Test
    public void test51() {

    }

    @Test
    public void test52() {

    }

    @Test
    public void test53() {

    }

    @Test
    public void test54() {

    }

    @Test
    public void test55() {

    }

    @Test
    public void test56() {

    }

    @Test
    public void test57() {

    }

    @Test
    public void test58() {

    }

    @Test
    public void test59() {

    }

    @Test
    public void test60() {

    }

    @Test
    public void test61() {

    }

    @Test
    public void test62() {

    }

    @Test
    public void test63() {

    }

    @Test
    public void test64() {

    }

    @Test
    public void test65() {

    }

    @Test
    public void test66() {

    }

    @Test
    public void test67() {

    }

    @Test
    public void test68() {

    }

    @Test
    public void test69() {

    }

    @Test
    public void test70() {

    }

    @Test
    public void test71() {

    }

    @Test
    public void test72() {

    }

    @Test
    public void test73() {

    }

    @Test
    public void test74() {

    }

    @Test
    public void test75() {

    }

    @Test
    public void test76() {

    }

    @Test
    public void test77() {

    }

    @Test
    public void test78() {

    }

    @Test
    public void test79() {

    }

    @Test
    public void test80() {

    }

    @Test
    public void test81() {

    }

    @Test
    public void test82() {

    }

    @Test
    public void test83() {

    }

    @Test
    public void test84() {

    }

    @Test
    public void test85() {

    }

    @Test
    public void test86() {

    }

    @Test
    public void test87() {

    }

    @Test
    public void test88() {

    }

    @Test
    public void test89() {

    }

    @Test
    public void test90() {

    }

    @Test
    public void test91() {

    }
}
