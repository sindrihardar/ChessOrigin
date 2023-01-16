package com.chess.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Pawn extends Piece {
    private int direction;
    private boolean promoted;
    private Piece promotedPiece;

    public Pawn(ChessGame game, Space space, Colors color) {
        super(game, space, color);
        direction = color == Colors.WHITE ? -1 : 1;
        promoted = false;
        promotedPiece = null;
    }

    public boolean wasPromoted() {
        return promoted;
    }

    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    public void promote() {
        promoted = true;
        promotedPiece = new Queen(game, getSpace(), getColor());
        promotedPiece.setWasMoved(true);
    }

    public void demote() {
        promoted = false;
        promotedPiece = null;
    }

    @Override
    public MoveCommand createMoveCommand(Space space) {
        if (space.getCol() != getSpace().getCol() && game.getPieceAt(space) == null)
            return createEnPassantMoveCommand(space);
        return createStandardMoveCommand(space);
    }

    private MoveCommand createEnPassantMoveCommand(Space space) {
        Set<Pair<Space, Space>> movements = new HashSet<>();
        Map<Space, Piece> captures = new HashMap<>();
        movements.add(new Pair<>(getSpace(), space));
        Space capturedSpace = Space.getSpace(getSpace().getRow(), space.getCol());
        captures.put(capturedSpace, game.getPieceAt(capturedSpace));
        return new MoveCommand(game, movements, captures);
    }

    @Override
    public Set<Space> getPotentiallyAvailableSpaces() {
        Set<Space> potentiallyAvailableSpaces = new HashSet<>();
        addOneSpaceForward(potentiallyAvailableSpaces);
        addTwoSpacesForward(potentiallyAvailableSpaces);
        addDiagonalCaptures(potentiallyAvailableSpaces);
        addEnPassant(potentiallyAvailableSpaces);
        return potentiallyAvailableSpaces;
    }

    public void addOneSpaceForward(Set<Space> potentiallyAvailableSpaces) {
        Space oneSpaceForward;
        try {
            oneSpaceForward = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol());
        } catch (Exception e) {
            return;
        }

        Piece p = game.getPieceAt(oneSpaceForward);
        if (p == null)
            potentiallyAvailableSpaces.add(oneSpaceForward);
    }

    public void addTwoSpacesForward(Set<Space> potentiallyAvailableSpaces) {
        Space oneSpaceForward, twoSpacesForward;
        try {
            oneSpaceForward = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol());
            twoSpacesForward = Space.getSpace(getSpace().getRow() + direction * 2, getSpace().getCol());
        } catch (Exception e) {
            return;
        }

        Piece p1 = game.getPieceAt(oneSpaceForward), p2 = game.getPieceAt(twoSpacesForward);
        if (p1 == null && p2 == null && !hasBeenMoved())
            potentiallyAvailableSpaces.add(twoSpacesForward);
    }

    public void addDiagonalCaptures(Set<Space> potentiallyAvailableSpaces) {
        Space leftDiagonal, rightDiagonal;
        try {
            leftDiagonal = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol() - 1);

            if (game.getPieceAt(leftDiagonal).getColor() != getColor())
                potentiallyAvailableSpaces.add(leftDiagonal);
        } catch (Exception e) {}

        try {
            rightDiagonal = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol() + 1);

            if (game.getPieceAt(rightDiagonal).getColor() != getColor())
                potentiallyAvailableSpaces.add(rightDiagonal);
        } catch (Exception e) {}
    }

    public void addEnPassant(Set<Space> potentiallyAvailableSpaces) {
        try {
            Space leftDiagonal = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol() - 1);
            Space leftAdjacentSpace = Space.getSpace(getSpace().getRow(), getSpace().getCol() - 1);

            if (game.getPieceAt(leftDiagonal) == null && game.getPieceAt(leftAdjacentSpace) instanceof Pawn && game.movedFirstTimeLastTurn(leftAdjacentSpace))
                potentiallyAvailableSpaces.add(leftDiagonal);
        } catch (Exception e) {}

        try {
            Space rightDiagonal = Space.getSpace(getSpace().getRow() + direction, getSpace().getCol() + 1);
            Space rightAdjacentSpace = Space.getSpace(getSpace().getRow(), getSpace().getCol() + 1);

            if (game.getPieceAt(rightDiagonal) == null && game.getPieceAt(rightAdjacentSpace) instanceof Pawn && game.movedFirstTimeLastTurn(rightAdjacentSpace))
                potentiallyAvailableSpaces.add(rightDiagonal);
        } catch (Exception e) {}
    }
}
