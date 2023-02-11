package com.chess.presenter;

import com.chess.view.nodes.BoardNode;
import com.chess.view.Observer;

public class LocalBoardPresenter extends BoardPresenter {
    public LocalBoardPresenter(GameMediator gameMediator) {
        super(gameMediator);
    }

    @Override
    public void click(int row, int col) {
        if (animationIsPlaying || super.isGameInStalemate() || super.isPlayerInCheckmate()) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            for (Observer observer : observers)
                ((BoardNode) observer).movePieceAnimation(selected.getRow(), selected.getCol(), row, col);
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
