package com.chess.model;

import java.util.Set;

public class ChessGame implements ChessGameInterface {

    public ChessGame(Pieces[][] board) {

    }

    public ChessGame() {}

    @Override
    public void move(int startRow, int startCol, int endRow, int endCol) {

    }

    @Override
    public void unexecuteLastMove() {

    }

    @Override
    public boolean isCurrentPlayersPiece(int row, int col) {
        return false;
    }

    @Override
    public Set<Space> getAvailableMovesForPiece(int row, int col) {
        return null;
    }

    @Override
    public boolean currentPlayerIsInCheckmate() {
        return false;
    }

    @Override
    public boolean gameIsInStaleMate() {
        return false;
    }

    @Override
    public Pieces[][] getBoardState() {
        return new Pieces[0][];
    }
}
