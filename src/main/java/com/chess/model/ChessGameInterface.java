package com.chess.model;

import java.util.Set;

public interface ChessGameInterface {
    /**
     * Moves the piece at the startRow row and startCol column to the space at the endRow row and endCol column.
     *
     * @throws IllegalArgumentException if:
     *  - the startRow row and startCol col don't contain the current player's piece, or
     *  - the space at the endRow row and the endCol column isn't available for the piece at startRow row and startCol column, or
     *  - the game is over
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     */
    public void move(int startRow, int startCol, int endRow, int endCol);

    /**
     * Reverts the game to it's state prior to the last move that was made.
     */
    public void unexecuteLastMove();

    /**
     * @param row
     * @param col
     * @return true if the piece at the given row and col is the same color as the current player, false otherwise
     */
    public boolean isCurrentPlayersPiece(int row, int col);

    /**
     * Gives a set of the spaces that the piece at the given row and col can move to.
     *
     * @throws IllegalArgumentException if
     *  - the space at the given row and column doesn't contain a piece, or
     *  - the space at the given row and column contains a piece of the color of the inactive player.
     * @param row
     * @param col
     * @return
     */
    public Set<Space> getAvailableMovesForPiece(int row, int col);

    /**
     * @return true if the current player is in checkmate, false otherwise
     */
    public boolean currentPlayerIsInCheckmate();

    /**
     * @return true if the game is in stalemate, false otherwise
     */
    public boolean gameIsInStaleMate();

    /**
     * @return the state of the board
     */
    public Pieces[][] getBoardState();
}
