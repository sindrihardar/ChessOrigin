package com.chess.model;

import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {
    public Queen(ChessGame game, Space space, Colors color) {
        super(game, space, color);
    }

    @Override
    public MoveCommand createMoveCommand(Space space) {
        return createStandardMoveCommand(space);
    }

    @Override
    public Set<Space> getPotentiallyAvailableSpaces() {
        Set<Space> potentiallyAvailableSpaces = new HashSet<>();
        addPotentiallyAvailableRectangularSpaces(potentiallyAvailableSpaces);
        addPotentiallyAvailableDiagonalSpaces(potentiallyAvailableSpaces);
        return potentiallyAvailableSpaces;
    }
}
