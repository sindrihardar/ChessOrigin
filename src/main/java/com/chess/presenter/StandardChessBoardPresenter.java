package com.chess.presenter;

public class StandardChessBoardPresenter extends ChessBoardPresenter {
    public StandardChessBoardPresenter() {
        super();
    }

    @Override
    public void click(int row, int col) {
        if (super.isGameInStalemate() || super.isPlayerInCheckmate()) // clicking should not affect the board if the game is over
            return;

        if (aTileIsSelectedAndGivenTileIsAvailable(row, col)) {
            game.move(selected.getRow(), selected.getCol(), row, col);
            resetAvailableTiles();              // reset available spaces
            boardState = game.getBoardState();  // update the boardState
            updateTilePresenterPieces();        // update the state of each of the tiles (which in turn updates the view)
            updateTilePresenterSelectability(); // updates the state of each tile's select-ability
            updateFlags();                      // checks if the game is over
        } else if (game.doesTileContainPieceOfCurrentPlayersColor(row, col)) {
            if (available != null)
                resetAvailableTiles();
            selected = tilePresenters[row][col];
            available = game.getAvailableMovesForTile(row, col);
            setAvailableTiles();
        }
    }
}
