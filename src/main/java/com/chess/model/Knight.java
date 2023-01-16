package com.chess.model;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    public Knight(ChessGame game, Space space, Colors color) {
        super(game, space, color);
    }

    @Override
    public MoveCommand createMoveCommand(Space space) {
        return createStandardMoveCommand(space);
    }

    @Override
    public Set<Space> getPotentiallyAvailableSpaces() {
        Set<Space> potentiallyAvailableSpaces = new HashSet<>();
        addPotentiallyAvailableLSpaces(potentiallyAvailableSpaces);
        return potentiallyAvailableSpaces;
    }

    public void addPotentiallyAvailableLSpaces(Set<Space> potentiallyAvailableSpaces) {
        int[][] adjacentVectors = {{-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {-2, 1}, {-2, -1}, {2, -1}, {2, 1}};
        for (int[] adjacentVector : adjacentVectors) {
            Space adjacentSpace;
            try {
                adjacentSpace = Space.getSpace(getSpace().getRow() + adjacentVector[0], getSpace().getCol() + adjacentVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece p = game.getPieceAt(adjacentSpace);
            if (p == null || p.getColor() != getColor())
                potentiallyAvailableSpaces.add(adjacentSpace);
        }
    }
}
