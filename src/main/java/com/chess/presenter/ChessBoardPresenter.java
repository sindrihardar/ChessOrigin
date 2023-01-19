package com.chess.presenter;

import com.chess.model.*;
import com.chess.model.util.Colors;
import com.chess.model.util.Pieces;
import com.chess.view.Observer;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ChessBoardPresenter implements Observable {
    protected ChessGameInterface game;
    protected ChessBoardTilePresenter[][] tilePresenters;
    protected Pieces[][] boardState;
    protected ChessBoardTilePresenter selected;
    protected Set<Tile> available;
    private List<Observer> observers;
    private boolean isGameOver, isGameInStalemate, isPlayerInCheckmate, didWhiteWin;

    public ChessBoardPresenter() {
        game = new ChessGame();
        setUpState();
        initializeTilePresenters();
        updateFlags();
    }

    private void setUpState() {
        boardState = game.getBoardState();
        selected = null;
        available = null;
        isGameOver = false;
        isGameInStalemate = false;
        isPlayerInCheckmate = false;
        didWhiteWin = false;
        observers = new LinkedList<>();
    }

    private void initializeTilePresenters() {
        tilePresenters = new ChessBoardTilePresenter[8][8];
        for (int i = 0; i < tilePresenters.length; i++)
            for (int j = 0; j < tilePresenters[i].length; j++)
                tilePresenters[i][j] = new ChessBoardTilePresenter(i, j, boardState[i][j], game.doesTileContainPieceOfCurrentPlayersColor(i, j));
    }

    protected void updateFlags() {
        updateIsGameInStalemateFlag();
        updateIsPlayerInCheckmateFlag();
        updateDidWhiteWinFlag();
    }

    private void updateIsGameInStalemateFlag() {
        if (this.isGameInStalemate == game.isGameInStalemate())
            return;
        this.isGameInStalemate = game.isGameInStalemate();
        notifyObservers();
    }

    private void updateIsPlayerInCheckmateFlag() {
        if (this.isPlayerInCheckmate == game.isCurrentPlayerInCheckmate())
            return;
        this.isPlayerInCheckmate = game.isCurrentPlayerInCheckmate();
        notifyObservers();
    }

    private void updateDidWhiteWinFlag() {
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

    public Colors getCurrentPlayersColor() {
        return game.getCurrentPlayerColor();
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

    public abstract void click(int row, int col);

    protected void setAvailableTiles() {
        for (Tile availableTile : available)
            tilePresenters[availableTile.getRow()][availableTile.getCol()].setAvailable(true);
    }

    protected void resetAvailableTiles() {
        selected = null;
        for (Tile availableSpace : available)
            tilePresenters[availableSpace.getRow()][availableSpace.getCol()].setAvailable(false);
        available = null;
    }

    protected void updateTilePresenterPieces() {
        for (int i = 0; i < boardState.length; i++)
            for (int j = 0; j < boardState.length; j++)
                tilePresenters[i][j].setPiece(boardState[i][j]);
    }

    protected void updateTilePresenterSelectability() {
        for (int i = 0; i < boardState.length; i++)
            for (int j = 0; j < boardState.length; j++)
                tilePresenters[i][j].setContainsPieceOfCurrentPlayersColor(game.doesTileContainPieceOfCurrentPlayersColor(i, j));
    }

    protected boolean aTileIsSelectedAndGivenTileIsAvailable(int row, int col) {
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
