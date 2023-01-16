package com.chess.model;

import java.util.List;
import java.util.Set;

public class ChessGame implements ChessGameInterface {
    public ChessGame(Colors color, Pieces[][] board) {

    }

    public ChessGame(Pieces[][] board) {

    }

    public ChessGame(Colors color) {

    }

    public ChessGame() {

    }

    @Override
    public boolean doesSpaceContainAPieceOfTheCurrentPlayersColor(int row, int col) {
        return false;
    }

    @Override
    public Set<Space> getAvailableMovesForSpace(int row, int col) {
        return null;
    }

    @Override
    public void move(int startRow, int startCol, int endRow, int endCol) {

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

    @Override
    public List<Pieces> getActiveWhitePieces() {
        return null;
    }

    @Override
    public List<Pieces> getActiveBlackPieces() {
        return null;
    }

    @Override
    public List<Pieces> getCapturedWhitePieces() {
        return null;
    }

    @Override
    public List<Pieces> getCapturedBlackPieces() {
        return null;
    }
}
