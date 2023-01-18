package com.chess.presenter;

import com.chess.model.*;
import com.chess.model.util.Colors;
import com.chess.model.util.Pieces;
import com.chess.view.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ChessBoardPresenter implements Observable {
    private ChessGameInterface game;
    private ChessBoardTilePresenter[][] tilePresenters;
    private Pieces[][] boardState;
    private ChessBoardTilePresenter selected;
    private Set<Tile> available;
    private List<Observer> observers;
    private boolean isGameOver, isGameInStalemate, isPlayerInCheckmate, didWhiteWin;

    public ChessBoardPresenter() {
        game = new ChessGame();
        setUpState();
        initializeTilePresenters();
        updateFlags();
    }

    public void setUpState() {
        boardState = game.getBoardState();
        selected = null;
        available = null;
        isGameOver = false;
        isGameInStalemate = false;
        isPlayerInCheckmate = false;
        didWhiteWin = false;
        observers = new LinkedList<>();
    }

    public void initializeTilePresenters() {
        tilePresenters = new ChessBoardTilePresenter[8][8];
        for (int i = 0; i < tilePresenters.length; i++)
            for (int j = 0; j < tilePresenters[i].length; j++)
                tilePresenters[i][j] = new ChessBoardTilePresenter(i, j, boardState[i][j], game.doesTileContainPieceOfCurrentPlayersColor(i, j));
    }

    public void updateFlags() {
        updateIsGameInStalemateFlag();
        updateIsPlayerInCheckmateFlag();
        updateDidWhiteWinFlag();
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
    
    /*
     *  Getters
     */
    
    public ChessBoardTilePresenter getChessBoardTilePresenter(int row, int col) {
        return tilePresenters[row][col];
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
    
    /*
     *  hoverInTo, hoverOutOf, and click are called when events are triggered in the view.
     */
    
    public void hoverInTo(int row, int col) {
        if (isGameOver)
            return;
        tilePresenters[row][col].setHoveredOver(true);
    }

    public void hoverOutOf(int row, int col) {
        if (isGameOver)
            return;
        tilePresenters[row][col].setHoveredOver(false);
    }

    public void click(int row, int col) {
        if (isGameOver) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            game.move(selected.getRow(), selected.getCol(), row, col);
            resetAvailableTiles();             // reset available spaces
            boardState = game.getBoardState();  // update the boardState
            updateTilePresenterPieces();        // update the state of each of the tiles (which in turn updates the view)
            updateTilePresenterSelectability(); // updates the state of each tile's select-ability
            updateFlags();                      // checks if the game is over
        } else if (game.doesTileContainPieceOfCurrentPlayersColor(row, col)) {
            if (available != null)
                resetAvailableTiles();
            selected = tilePresenters[row][col];
            available = game.getAvailableMovesForTile(row, col);
            setAvailableTiles();
        }
    }

    public void setAvailableTiles() {
        for (Tile availableTile : available)
            tilePresenters[availableTile.getRow()][availableTile.getCol()].setAvailable(true);
    }

    public void resetAvailableTiles() {
        selected = null;
        for (Tile availableSpace : available)
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
                tilePresenters[i][j].setContainsPieceOfCurrentPlayersColor(game.doesTileContainPieceOfCurrentPlayersColor(i, j));
    }

    public boolean aTileIsSelectedAndGivenTileIsAvailable(int row, int col) {
        return selected != null && available.contains(Tile.getTile(row, col));
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
