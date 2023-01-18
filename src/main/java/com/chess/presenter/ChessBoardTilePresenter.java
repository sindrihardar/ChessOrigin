package com.chess.presenter;

import com.chess.model.util.Pieces;
import com.chess.view.Observer;
import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;

public class ChessBoardTilePresenter implements Observable {
    private int row, col;
    private boolean hoveredOver, available, containsPieceOfCurrentPlayersColor;
    private Pieces piece;
    private List<Observer> observers;

    public ChessBoardTilePresenter(int row, int col, Pieces piece, boolean containsPieceOfCurrentPlayersColor) {
        setUpState(row, col, piece, containsPieceOfCurrentPlayersColor);
        notifyObservers();
    }

    private void setUpState(int row, int col, Pieces piece, boolean containsPieceOfCurrentPlayersColor) {
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.containsPieceOfCurrentPlayersColor = containsPieceOfCurrentPlayersColor;
        hoveredOver = false;
        available = false;
        observers = new LinkedList<>();
    }

    /*
     *  Getters
     */

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Pieces getPiece() {
        return piece;
    }

    public Image getImage() {
        String path = "file:./src/main/java/com/chess/presenter/resources/";
        if (piece == Pieces.BLACK_KING)
            path += "black_king.png";
        else if (piece == Pieces.BLACK_QUEEN)
            path += "black_queen.png";
        else if (piece == Pieces.BLACK_ROOK)
            path += "black_rook.png";
        else if (piece == Pieces.BLACK_BISHOP)
            path += "black_bishop.png";
        else if (piece == Pieces.BLACK_KNIGHT)
            path += "black_knight.png";
        else if (piece == Pieces.BLACK_PAWN)
            path += "black_pawn.png";
        else if (piece == Pieces.WHITE_KING)
            path += "white_king.png";
        else if (piece == Pieces.WHITE_QUEEN)
            path += "white_queen.png";
        else if (piece == Pieces.WHITE_ROOK)
            path += "white_rook.png";
        else if (piece == Pieces.WHITE_BISHOP)
            path += "white_bishop.png";
        else if (piece == Pieces.WHITE_KNIGHT)
            path += "white_knight.png";
        else if (piece == Pieces.WHITE_PAWN)
            path += "white_pawn.png";
        else
            return null;

        try {
            return new Image(path);
        } catch (Exception e) {
            throw new RuntimeException("No image found for file path: " + path);
        }
    }

    public String getStyle() {
        if (hoveredOver && (available || containsPieceOfCurrentPlayersColor))
            return "-fx-background-color: red;";
        if (row % 2 == col % 2) {
            if (available)
                return "-fx-background-color: rgb(155, 49, 148);";
            else
                return "-fx-background-color: rgb(73, 204, 132);";
        } else {
            if (available)
                return "-fx-background-color: rgb(255, 149, 248);";
            else
                return "-fx-background-color: rgb(255, 249, 248);";
        }
    }

    /*
     *  Setters
     */

    public void setPiece(Pieces piece) {
        if (this.piece == piece)
            return;
        this.piece = piece;
        notifyObservers();
    }

    public void setAvailable(boolean available) {
        if (this.available == available)
            return;
        this.available = available;
        notifyObservers();
    }

    public void setHoveredOver(boolean hoveredOver) {
        if (this.hoveredOver == hoveredOver)
            return;
        this.hoveredOver = hoveredOver;
        notifyObservers();
    }

    public void setContainsPieceOfCurrentPlayersColor(boolean containsPieceOfCurrentPlayersColor) {
        if (this.containsPieceOfCurrentPlayersColor == containsPieceOfCurrentPlayersColor)
            return;
        this.containsPieceOfCurrentPlayersColor = containsPieceOfCurrentPlayersColor;
        notifyObservers();
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
