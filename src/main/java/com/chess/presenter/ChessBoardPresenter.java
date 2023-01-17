package com.chess.presenter;

import com.chess.model.*;
import com.chess.view.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChessBoardPresenter implements Observable {
    private ChessGameInterface game;
    private ChessBoardTilePresenter[][] tilePresenters;
    private Pieces[][] boardState;
    private ChessBoardTilePresenter selected;
    private Set<Space> available;
    private List<Observer> observers;
    private boolean isGameOver, isGameInStalemate, isPlayerInCheckmate, didWhiteWin;

    public ChessBoardPresenter() {
        game = new ChessGame();
        boardState = game.getBoardState();
        initializeTilePresenters();
        selected = null;
        available = null;
        isGameOver = false;
        isGameInStalemate = false;
        isPlayerInCheckmate = false;
        didWhiteWin = false;
        updateFlags();
        observers = new LinkedList<>();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isGameInStalemate() {
        return isGameInStalemate;
    }

    public boolean isPlayerInCheckmate() {
        return isPlayerInCheckmate;
    }

    public boolean didWhiteWin() {
        return didWhiteWin;
    }

    public void updateFlags() {
        updateIsGameInStalemateFlag();
        updateIsPlayerInCheckmateFlag();
        updateDidWhiteWinFlag();
        updateIsGameOverFlag();
    }

    public void updateIsGameInStalemateFlag() {
        if (this.isGameInStalemate == game.isGameInStalemate())
            return;
        this.isGameInStalemate = game.isGameInStalemate();
        notifyObservers();
    }

    public void updateIsPlayerInCheckmateFlag() {
        if (this.isPlayerInCheckmate == game.isCurrentPlayerInCheckmate())
            return;
        this.isPlayerInCheckmate = game.isCurrentPlayerInCheckmate();
        notifyObservers();
    }

    public void updateDidWhiteWinFlag() {
        boolean didWhiteWin = game.getCurrentPlayerColor() == Colors.BLACK && game.isCurrentPlayerInCheckmate();
        if (this.didWhiteWin == didWhiteWin)
            return;
        this.didWhiteWin = didWhiteWin;
        notifyObservers();
    }

    public void updateIsGameOverFlag() {
        boolean isGameOver = game.isGameInStalemate() || game.isCurrentPlayerInCheckmate();
        if (this.isGameOver == isGameOver)
            return;
        this.isGameOver = isGameOver;
        notifyObservers();
    }

    public void initializeTilePresenters() {
        tilePresenters = new ChessBoardTilePresenter[8][8];
        for (int i = 0; i < tilePresenters.length; i++)
            for (int j = 0; j < tilePresenters[i].length; j++)
                tilePresenters[i][j] = new ChessBoardTilePresenter(i, j, boardState[i][j], game.doesSpaceContainAPieceOfTheCurrentPlayersColor(i, j));
    }

    public ChessBoardTilePresenter getSpacePresenter(int row, int col) {
        return tilePresenters[row][col];
    }

    public void hoverInTo(int row, int col) {
        tilePresenters[row][col].setHoveredOver(true);
    }

    public void hoverOutOf(int row, int col) {
        tilePresenters[row][col].setHoveredOver(false);
    }

    public void click(int row, int col) {
        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            game.move(selected.getRow(), selected.getCol(), row, col);
            resetAvailableSpaces();
            boardState = game.getBoardState();
            updateTilePresenterPieces();
            updateTilePresenterSelectability();
            updateFlags();
        } else if (game.doesSpaceContainAPieceOfTheCurrentPlayersColor(row, col)) {
            if (available != null)
                resetAvailableSpaces();
            selected = tilePresenters[row][col];
            available = game.getAvailableMovesForSpace(row, col);
            setAvailableTiles();
        }
    }

    public void setAvailableTiles() {
        for (Space availableTile : available)
            tilePresenters[availableTile.getRow()][availableTile.getCol()].setAvailable(true);
    }

    public void resetAvailableSpaces() {
        selected = null;
        for (Space availableSpace : available)
            tilePresenters[availableSpace.getRow()][availableSpace.getCol()].setAvailable(false);
        available = null;
    }

    public void updateTilePresenterPieces() {
        for (int i = 0; i < boardState.length; i++)
            for (int j = 0; j < boardState.length; j++)
                tilePresenters[i][j].setPiece(boardState[i][j]);
    }

    public void updateTilePresenterSelectability() {
        for (int i = 0; i < boardState.length; i++)
            for (int j = 0; j < boardState.length; j++)
                tilePresenters[i][j].setContainsPieceOfCurrentPlayersColor(game.doesSpaceContainAPieceOfTheCurrentPlayersColor(i, j));
    }

    public boolean aTileIsSelectedAndGivenTileIsAvailable(int row, int col) {
        return selected != null && available.contains(Space.getSpace(row, col));
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update();
    }
}
