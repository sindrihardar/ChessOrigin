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
        if (super.isGameInStalemate() || super.isPlayerInCheckmate()) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            for (Observer observer : observers)
                ((ChessBoardScene) observer).movePieceAnimation(selected.getRow(), selected.getCol(), row, col);
            animationIsPlaying = true;
            movements.add(new Movement(selected.getRow(), selected.getCol(), row, col));
            /*
            game.move(selected.getRow(), selected.getCol(), row, col);
            resetAvailableTiles();              // reset available spaces
            boardState = game.getBoardState();  // update the boardState
            updateTilePresenterPieces();        // update the state of each of the tiles (which in turn updates the view)
            updateTilePresenterSelectability(); // updates the state of each tile's select-ability
            updateFlags();                      // checks if the game is over
             */
            /*
            if (super.isGameInStalemate() || super.isPlayerInCheckmate())
                return;
            Pair<Tile, Tile> move = bot.move();
            boardState = game.getBoardState();  // update the boardState
            for (Observer observer : observers)
                ((ChessBoardScene) observer).movePieceAnimation(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
            animationIsPlaying = true;
            movements.add(new Movement(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol()));
            */

            /*
            updateTilePresenterPieces();        // update the state of each of the tiles (which in turn updates the view)
            updateTilePresenterSelectability(); // updates the state of each tile's select-ability
            updateFlags();                      // checks if the game is over
             */
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
            if (super.isGameInStalemate() || super.isPlayerInCheckmate())
                return;
            Pair<Tile, Tile> move = bot.move();
            boardState = game.getBoardState();  // update the boardState
            for (Observer observer : observers)
                ((ChessBoardScene) observer).movePieceAnimation(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
            animationIsPlaying = true;
            movements.add(new Movement(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol()));
        } else {
            super.executeQueuedMovement();
        }
    }
}
