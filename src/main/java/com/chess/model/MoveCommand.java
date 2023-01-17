package com.chess.model;

import java.util.Map;
import java.util.Set;

public class MoveCommand {
    private ChessGame game;
    private Set<Pair<Space, Space>> movements;
    private Map<Space, Piece> captures;
    private boolean firstTimeBeingMoved, promotion;

    public MoveCommand(ChessGame game, Set<Pair<Space, Space>> movements, Map<Space, Piece> captures) {
        this.game = game;
        this.movements = movements;
        this.captures = captures;

        firstTimeBeingMoved = false;
        for (Pair<Space, Space> movement : movements)
            if (!game.getPieceAt(movement.getKey()).hasBeenMoved())
                firstTimeBeingMoved = true;

        promotion = false;
        for (Pair<Space, Space> movement : movements) {
            Piece p = game.getPieceAt(movement.getKey());
            int row = movement.getValue().getRow();
            if (p instanceof Pawn && !((Pawn) p).wasPromoted() && (p.getColor() == Colors.BLACK && row == 7 || p.getColor() == Colors.WHITE && row == 0))
                promotion = true;
        }
    }

    public void execute() {
        executeCaptures();
        executeMovements();
        game.switchCurrentPlayerColor();
        game.addMoveCommandToPlayedMoves(this);
    }

    public void executeCaptures() {
        for (Space space : captures.keySet())
            game.executeCapture(space);
    }

    public void executeMovements() {
        for (Pair<Space, Space> movement : movements) {
            game.executeMovement(movement);
            game.getPieceAt(movement.getValue()).setWasMoved(true);
            if (promotion) {
                Pawn pawn = (Pawn) game.getPieceAt(movement.getValue());
                pawn.promote();
            }
        }
    }

    public void unexecute() {
        unexecuteMovements();
        unexecuteCaptures();
        game.switchCurrentPlayerColor();
    }

    public void unexecuteMovements() {
        for (Pair<Space, Space> movement : movements) {
            game.executeMovement(new Pair<>(movement.getValue(), movement.getKey()));
            if (firstTimeBeingMoved)
                game.getPieceAt(movement.getKey()).setWasMoved(false);
            if (promotion) {
                Pawn pawn = (Pawn) game.getPieceAt(movement.getKey());
                pawn.demote();
            }
        }
    }

    public void unexecuteCaptures() {
        for (Space space : captures.keySet())
            game.unexecuteCapture(space, captures.get(space));
    }

    public boolean pieceWasMovedFirstTime(Space space) {
        if (!firstTimeBeingMoved)
            return false;

        for (Pair<Space, Space> movement : movements)
            if (movement.getValue() == space)
                return true;

        return false;
    }
}
