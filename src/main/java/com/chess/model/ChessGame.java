package com.chess.model;

import java.util.Set;

public class ChessGame implements ChessGameInterface {

    public ChessGame(Pieces[][] board) {

    }

    public ChessGame() {}

    @Override
    public boolean doesSpaceContainAPieceOfTheCurrentPlayersColor(Space space) {
        return false;
    }

    @Override
    public Set<Space> getAvailableMovesForPiece(Space space) {
        return null;
    }

    @Override
    public void move(Space start, Space end) {

    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public boolean isCurrentPlayerInCheck() {
        return false;
    }

    @Override
    public boolean isCurrentPlayerInCheckmate() {
        return false;
    }

    @Override
    public boolean isGameInStalemate() {
        return false;
    }

    @Override
    public Pieces[][] getBoardState() {
        return new Pieces[0][];
    }
}
