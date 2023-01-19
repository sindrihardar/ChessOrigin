package com.chess.model.pieces;

import com.chess.model.game.ChessGame;
import com.chess.model.util.Colors;
import com.chess.model.game.MoveCommand;
import com.chess.model.game.Tile;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece {
    public Rook(ChessGame game, Tile tile, Colors color) {
        super(game, tile, color);
    }

    @Override
    public MoveCommand createMoveCommand(Tile tile) {
        return createStandardMoveCommand(tile);
    }

    @Override
    public Set<Tile> getPotentiallyAvailableTiles() {
        Set<Tile> potentiallyAvailableTiles = new HashSet<>();
        addPotentiallyAvailableRectangularTiles(potentiallyAvailableTiles);
        return potentiallyAvailableTiles;
    }
}
