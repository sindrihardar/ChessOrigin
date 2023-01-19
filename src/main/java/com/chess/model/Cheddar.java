package com.chess.model;

public class Cheddar implements AIInterface {
    private ChessGameInterface game;

    public Cheddar(ChessGameInterface game) {
        this.game = game;
    }

    @Override
    public void move() {

    }

    @Override
    public void setGame(ChessGameInterface game) {
        this.game = game;
    }
}
