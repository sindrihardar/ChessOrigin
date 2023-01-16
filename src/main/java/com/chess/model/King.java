package com.chess.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class King extends Piece {
    public King(ChessGame game, Space space, Colors color) {
        super(game, space, color);
    }

    @Override
    public MoveCommand createMoveCommand(Space space) {
        Space startingSpace = super.getSpace();
        if (Math.abs(startingSpace.getRow() - space.getRow()) <= 1 && Math.abs(startingSpace.getCol() - space.getCol()) <= 1)
            return createStandardMoveCommand(space);
        return createMoveCommandForCastle(space);
    }

    private MoveCommand createMoveCommandForCastle(Space space) {
        Set<Pair<Space, Space>> movements = new HashSet<>();
        Map<Space, Piece> captures = new HashMap<>();
        movements.add(new Pair<>(getSpace(), space));
        if (space.getRow() == 7 && space.getCol() == 6)
            movements.add(new Pair<>(Space.getSpace(7, 7), Space.getSpace(7, 5)));
        else if (space.getRow() == 7 && space.getCol() == 2)
            movements.add(new Pair<>(Space.getSpace(7, 0), Space.getSpace(7, 3)));
        else if (space.getRow() == 0 && space.getCol() == 6)
            movements.add(new Pair<>(Space.getSpace(0, 7), Space.getSpace(0, 5)));
        else if (space.getRow() == 0 && space.getCol() == 2)
            movements.add(new Pair<>(Space.getSpace(0, 0), Space.getSpace(0, 3)));
        return new MoveCommand(game, movements, captures);
    }

    @Override
    public Set<Space> getPotentiallyAvailableSpaces() {
        Set<Space> potentiallyAvailableSpaces = new HashSet<>();
        addPotentiallyAvailableAdjacentSpaces(potentiallyAvailableSpaces);
        addPotentiallyAvailableCastleSpaces(potentiallyAvailableSpaces);
        return potentiallyAvailableSpaces;
    }

    public void addPotentiallyAvailableAdjacentSpaces(Set<Space> potentiallyAvailableSpaces) {
        int[][] adjacentVectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
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

    public void addPotentiallyAvailableCastleSpaces(Set<Space> potentiallyAvailableSpaces) {
        if (hasBeenMoved() || getColor() == Colors.WHITE && game.isWhiteKingInCheck() || getColor() == Colors.BLACK && game.isBlackKingInCheck())
            return;

        Piece rightPiece = game.getPieceAt(Space.getSpace(getSpace().getRow(), 7));
        if (rightPiece != null && !rightPiece.hasBeenMoved() && rightSpacesAreEmpty())
            potentiallyAvailableSpaces.add(Space.getSpace(getSpace().getRow(), getSpace().getCol() + 2));

        Piece leftPiece = game.getPieceAt(Space.getSpace(getSpace().getRow(), 0));
        if (leftPiece != null && !leftPiece.hasBeenMoved() && leftSpacesAreEmpty())
            potentiallyAvailableSpaces.add(Space.getSpace(getSpace().getRow(), getSpace().getCol() - 2));
    }

    public boolean rightSpacesAreEmpty() {
        try {
            return game.getPieceAt(Space.getSpace(getSpace().getRow(), getSpace().getCol() + 1)) == null &&
                    game.getPieceAt(Space.getSpace(getSpace().getRow(), getSpace().getCol() + 2)) == null;
        } catch (Exception e) {}
        return false;
    }

    public boolean leftSpacesAreEmpty() {
        try {
            return game.getPieceAt(Space.getSpace(getSpace().getRow(), getSpace().getCol() - 1)) == null &&
                    game.getPieceAt(Space.getSpace(getSpace().getRow(), getSpace().getCol() - 2)) == null &&
                    game.getPieceAt(Space.getSpace(getSpace().getRow(), getSpace().getCol() - 3)) == null;
        } catch (Exception e) {}
        return false;
    }
}
