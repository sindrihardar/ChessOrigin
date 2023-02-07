package com.chess.model.ai;

import com.chess.model.game.ChessGameInterface;
import com.chess.model.game.Tile;
import com.chess.model.util.Pair;

public interface AIInterface {
    /**
     * Performs a move on the bot's chess game based.
     */
    Pair<Tile, Tile> move();

    /**
     * Sets the current game for the bot.
     */
    void setGame(ChessGameInterface game);
}
