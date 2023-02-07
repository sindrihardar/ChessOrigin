package com.chess.presenter;

import com.chess.model.ai.AIInterface;
import com.chess.model.ai.Bleu;
import com.chess.model.ai.Cheddar;
import com.chess.model.game.Tile;
import com.chess.model.util.Colors;
import com.chess.model.util.Pair;
import com.chess.view.ChessBoardScene;
import com.chess.view.Observer;

public class AIChessBoardPresenter extends ChessBoardPresenter {
    private AIInterface bot;

    public AIChessBoardPresenter() {
        super();
        this.bot = new Cheddar(game);
    }

    @Override
    public void click(int row, int col) {
        if (animationIsPlaying || super.isGameInStalemate() || super.isPlayerInCheckmate()) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            animationIsPlaying = true;
            for (Observer observer : observers)
                ((ChessBoardScene) observer).movePieceAnimation(selected.getRow(), selected.getCol(), row, col);
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
            animationIsPlaying = true;
            if (super.isGameInStalemate() || super.isPlayerInCheckmate())
                return;
            Pair<Tile, Tile> move = bot.move();
            boardState = game.getBoardState();  // update the boardState
            for (Observer observer : observers)
                ((ChessBoardScene) observer).movePieceAnimation(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
            movements.add(new Movement(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol()));
        } else {
            super.executeQueuedMovement();
        }
    }
}
