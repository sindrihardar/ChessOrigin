package com.chess.presenter;

import com.chess.model.ai.AIInterface;
import com.chess.model.ai.Cheddar;
import com.chess.model.util.Colors;
import com.chess.model.util.Pair;
import com.chess.model.util.Tile;
import com.chess.view.Observer;
import com.chess.view.nodes.BoardNode;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class AITimedChessBoardPresenter extends BoardPresenter {
    private TimerPresenter whiteTimer, blackTimer, currentTimer;
    private AIInterface bot;

    public AITimedChessBoardPresenter() {
        super();
        this.bot = new Cheddar(game);
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
    public void notifyObservers() {
        super.notifyObservers();
        if (super.isPlayerInCheckmate() || super.isGameInStalemate()) {
            whiteTimer.gameIsOver();
            blackTimer.gameIsOver();
        }
    }

    @Override
    public void click(int row, int col) {
        if (animationIsPlaying || super.isGameInStalemate() || super.isPlayerInCheckmate())
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            animationIsPlaying = true;
            for (Observer observer : observers)
                ((BoardNode) observer).movePieceAnimation(selected.getRow(), selected.getCol(), row, col);
            movements.add(new Movement(selected.getRow(), selected.getCol(), row, col));
        } else if (game.doesTileContainPieceOfCurrentPlayersColor(row, col)) {
            if (available != null)
                resetAvailableTiles();
            selected = tilePresenters[row][col];
            available = game.getAvailableMovesForTile(row, col);
            setAvailableTiles();
        }
    }

    @Override
    public void executeQueuedMovement() {
        if (game.getCurrentPlayerColor() == Colors.WHITE) {
            super.executeQueuedMovement();
            hitTimer();
            animationIsPlaying = true;

            Task<Pair<Tile, Tile>> task = new Task<>() {
                @Override
                protected Pair<Tile, Tile> call() throws Exception {
                    if (isGameInStalemate() || isPlayerInCheckmate())
                        return null;
                    Pair<Tile, Tile> move = bot.move();
                    return move;
                }
            };

            task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    if (task.getValue() == null)
                        return;

                    Pair<Tile, Tile> move = task.getValue();
                    boardState = game.getBoardState();  // update the boardState
                    for (Observer observer : observers)
                        ((BoardNode) observer).movePieceAnimation(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
                    movements.add(new Movement(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol()));
                }
            });

            new Thread(task).start();
        } else {
            super.executeQueuedMovement();
            hitTimer();
        }
    }
}
