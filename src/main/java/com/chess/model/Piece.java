package com.chess.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Piece {
    static final int SIZE_OF_CHESS_BOARD = 8;

    protected ChessGame game;
    private Space space;
    private Colors color;
    private boolean wasMoved;

    public Piece(ChessGame game, Space space, Colors color) {
        this.game = game;
        this.space = space;
        this.color = color;
        wasMoved = false;
    }

    public Colors getColor() {
        return color;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public boolean hasBeenMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }

    public Set<Space> getAvailableSpaces() {
        Set<Space> availableSpaces = new HashSet<>();
        Set<Space> potentiallyAvailableSpaces = getPotentiallyAvailableSpaces();
        for (Space space : potentiallyAvailableSpaces)
            if (!moveWouldPutPlayerInCheck(space))
                availableSpaces.add(space);
        return availableSpaces;
    }

    public boolean moveWouldPutPlayerInCheck(Space space) {
        MoveCommand moveCommand = createMoveCommand(space);
        moveCommand.execute();
        game.updateIsWhiteKingInCheckFlag();
        game.updateIsBlackKingInCheckFlag();
        boolean isPlayerInCheck = false;
        if (game.getCurrentPlayersColor() == Colors.WHITE && game.isBlackKingInCheck() || game.getCurrentPlayersColor() == Colors.BLACK && game.isWhiteKingInCheck())
            isPlayerInCheck = true;
        game.pollLastMoveCommand();
        moveCommand.unexecute();
        game.updateIsWhiteKingInCheckFlag();
        game.updateIsBlackKingInCheckFlag();
        return isPlayerInCheck;
    }

    public MoveCommand createStandardMoveCommand(Space space) {
        Set<Pair<Space, Space>> movements = new HashSet<>();
        Map<Space, Piece> captures = new HashMap<>();
        if (game.getPieceAt(space) != null && game.getPieceAt(space).getColor() != getColor())
            captures.put(space, game.getPieceAt(space));
        movements.add(new Pair<>(getSpace(), space));
        return new MoveCommand(game, movements, captures);
    }

    public void addPotentiallyAvailableDiagonalSpaces(Set<Space> potentiallyAvailableSpaces) {
        int[][] diagonalVectors = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] diagonalVector : diagonalVectors) {
            int distanceFromStart = 1;
            while (distanceFromStart < SIZE_OF_CHESS_BOARD) {
                Space diagonalSpace;
                try {
                    diagonalSpace = Space.getSpace(getSpace().getRow() + diagonalVector[0] * distanceFromStart, getSpace().getCol() + diagonalVector[1] * distanceFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece p = game.getPieceAt(diagonalSpace);
                if (p == null) {
                    potentiallyAvailableSpaces.add(diagonalSpace);
                    distanceFromStart++;
                } else {
                    if (p.getColor() != getColor())
                        potentiallyAvailableSpaces.add(diagonalSpace);
                    break;
                }
            }
        }
    }

    public void addPotentiallyAvailableRectangularSpaces(Set<Space> potentiallyAvailableSpaces) {
        int[][] rectangularVectors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] rectangularVector : rectangularVectors) {
            int distanceFromStart = 1;
            while (distanceFromStart < SIZE_OF_CHESS_BOARD) {
                Space rectangularSpace;
                try {
                    rectangularSpace = Space.getSpace(getSpace().getRow() + rectangularVector[0] * distanceFromStart, getSpace().getCol() + rectangularVector[1] * distanceFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece p = game.getPieceAt(rectangularSpace);
                if (p == null) {
                    potentiallyAvailableSpaces.add(rectangularSpace);
                    distanceFromStart++;
                } else {
                    if (p.getColor() != getColor())
                        potentiallyAvailableSpaces.add(rectangularSpace);
                    break;
                }
            }
        }
    }

    public abstract MoveCommand createMoveCommand(Space space);

    public abstract Set<Space> getPotentiallyAvailableSpaces();
}
