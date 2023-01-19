package com.chess.model;

public interface AIInterface {
    /**
     * Performs a move on the bot's chess game based.
     */
    void move();

    /**
     * Sets the current game for the bot.
     */
    void setGame(ChessGameInterface game);
}
