package com.chess.presenter;

import com.chess.model.ChessGame;
import com.chess.model.ChessGameInterface;
import com.chess.model.Pieces;
import com.chess.model.Space;

import java.util.Set;

public class ChessGamePresenter {
    private ChessGameInterface game;
    private ChessBoardSpacePresenter[][] spacePresenters;
    private Pieces[][] boardState;
    private ChessBoardSpacePresenter selected;
    private Set<Space> available;

    public ChessGamePresenter() {
        game = new ChessGame();
        boardState = game.getBoardState();
        spacePresenters = new ChessBoardSpacePresenter[8][8];
        for (int i = 0; i < spacePresenters.length; i++)
            for (int j = 0; j < spacePresenters[i].length; j++)
                spacePresenters[i][j] = new ChessBoardSpacePresenter(i, j, boardState[i][j]);
        selected = null;
        available = null;
    }

    public ChessBoardSpacePresenter getSpacePresenter(int row, int col) {
        return spacePresenters[row][col];
    }

    public void hoverOver(int row, int col) {
        spacePresenters[row][col].setHoveredOver(true);
    }

    public void hoverOutOf(int row, int col) {
        spacePresenters[row][col].setHoveredOver(false);
    }

    public void click(int row, int col) {
        if (selected != null && available.contains(Space.getSpace(row, col))) {
            game.move(selected.getRow(), selected.getCol(), row, col);
            selected = null;
            for (Space availableSpace : available)
                spacePresenters[availableSpace.getRow()][availableSpace.getCol()].setAvailable(false);
            available = null;
            boardState = game.getBoardState();
            for (int i = 0; i < boardState.length; i++)
                for (int j = 0; j < boardState.length; j++)
                    if (boardState[i][j] != spacePresenters[i][j].getPiece())
                        spacePresenters[i][j].setPiece(boardState[i][j]);
        } else if (game.doesSpaceContainAPieceOfTheCurrentPlayersColor(row, col)) {
            if (available != null)
                for (Space availableSpace : available)
                    spacePresenters[availableSpace.getRow()][availableSpace.getCol()].setAvailable(false);
            selected = spacePresenters[row][col];
            available = game.getAvailableMovesForSpace(row, col);
            for (Space availableSpace : available)
                spacePresenters[availableSpace.getRow()][availableSpace.getCol()].setAvailable(true);
        }
    }
}
