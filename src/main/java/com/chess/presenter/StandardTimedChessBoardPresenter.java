package com.chess.presenter;

import com.chess.model.util.Colors;
import com.chess.view.Observer;
import com.chess.view.nodes.ChessBoardNode;

public class StandardTimedChessBoardPresenter extends ChessBoardPresenter {
    private TimerPresenter whiteTimer, blackTimer, currentTimer;

    public StandardTimedChessBoardPresenter() {
        super();
        whiteTimer = new TimerPresenter(10 * 60 * 1000 + 1000, Colors.WHITE, this);
        blackTimer = new TimerPresenter(10 * 60 * 1000, Colors.BLACK, this);
        currentTimer = whiteTimer;
        currentTimer.start();
    }

    public TimerPresenter getWhiteTimer() {
        return whiteTimer;
    }

    public TimerPresenter getBlackTimer() {
        return blackTimer;
    }

    public void hitTimer() {
        currentTimer.stop();
        currentTimer = currentTimer == whiteTimer ? blackTimer : whiteTimer;
        currentTimer.start();
    }

    @Override
    public void executeQueuedMovement() {
        super.executeQueuedMovement();
        hitTimer();
    }

    @Override
    public void click(int row, int col) {
        if (animationIsPlaying || super.isGameInStalemate() || super.isPlayerInCheckmate()) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            for (Observer observer : observers)
                ((ChessBoardNode) observer).movePieceAnimation(selected.getRow(), selected.getCol(), row, col);
            animationIsPlaying = true;
            movements.add(new Movement(selected.getRow(), selected.getCol(), row, col));
            resetAvailableTiles();
        } else if (game.doesTileContainPieceOfCurrentPlayersColor(row, col)) {
            if (available != null)
                resetAvailableTiles();
            selected = tilePresenters[row][col];
            available = game.getAvailableMovesForTile(row, col);
            setAvailableTiles();
        }
    }
}
