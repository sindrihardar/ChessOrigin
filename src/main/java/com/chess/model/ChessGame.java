package com.chess.model;

import com.chess.model.pieces.*;
import com.chess.model.util.Colors;
import com.chess.model.util.Pair;
import com.chess.model.util.Pieces;

import java.util.*;

import static com.chess.model.util.Constants.*;

public class ChessGame implements ChessGameInterface {
    private Colors currentPlayersColor;
    private List<Piece> activeWhitePieces, activeBlackPieces, capturedWhitePieces, capturedBlackPieces;
    private Map<Tile, Piece> board;
    private ArrayDeque<MoveCommand> playedMoves;
    private Map<Piece, Set<Tile>> availableTilesCache;
    private boolean isBlackKingInCheckFlag, isWhiteKingInCheckFlag, currentPlayerHasMovesFlag;

    public ChessGame(Colors color, Pieces[][] board) {
        setUpState(color, board);
        updateAvailableTilesCache();
        updateFlags();
    }

    public ChessGame(Pieces[][] board) {
        this(Colors.WHITE, board);
    }

    public ChessGame(Colors color) {
        this(color, initialState);
    }

    public ChessGame() {
        this(Colors.WHITE, initialState);
    }

    private void setUpState(Colors color, Pieces[][] board) {
        currentPlayersColor = color;
        ChessGameBuilder builder = new ChessGameBuilder(this, board);
        activeWhitePieces = builder.getActiveWhitePieces();
        activeBlackPieces = builder.getActiveBlackPieces();
        capturedWhitePieces = new LinkedList<>();
        capturedBlackPieces = new LinkedList<>();
        this.board = builder.getBoardMap();
        playedMoves = new ArrayDeque<>();
    }

    @Override
    public boolean doesTileContainPieceOfCurrentPlayersColor(int row, int col) {
        Piece piece = board.getOrDefault(Tile.getTile(row, col), null);
        return piece != null && piece.getColor() == currentPlayersColor;
    }


    @Override
    public Set<Tile> getAvailableMovesForTile(int row, int col) {
        if (isGameInStalemate() || isCurrentPlayerInCheckmate())
            throw new IllegalArgumentException("Game is over!");
        else if (!doesTileContainPieceOfCurrentPlayersColor(row, col))
            throw new IllegalArgumentException("Specified tile does not contain a piece of the current player's color.");

        return availableTilesCache.get(board.get(Tile.getTile(row, col)));
    }

    @Override
    public void move(int startRow, int startCol, int endRow, int endCol) {
        Tile startingTile = Tile.getTile(startRow, startCol), endingTile = Tile.getTile(endRow, endCol);
        isMovementLegal(startingTile, endingTile);
        resolveMovement(startingTile, endingTile);
        updateAvailableTilesCache();
        updateFlags();
    }

    private boolean isMovementLegal(Tile startingTile, Tile endingTile) {
        if (!doesTileContainPieceOfCurrentPlayersColor(startingTile.getRow(), startingTile.getCol()))
            throw new IllegalArgumentException("Starting tile does not contain a piece of the current player's color.");
        Piece p = board.get(startingTile);
        if (!availableTilesCache.get(p).contains(endingTile))
            throw new IllegalArgumentException("Ending tile is not available to be moved to.");
        if (isCurrentPlayerInCheckmate() || isGameInStalemate())
            throw new IllegalArgumentException("Cannot move because game is over.");
        return true;
    }

    private void resolveMovement(Tile startingTile, Tile endingTile) {
        MoveCommand moveCommand = board.get(startingTile).createMoveCommand(endingTile);
        moveCommand.execute();
    }

    @Override
    public void undoLastMove() {
        if (playedMoves.size() == 0)
            throw new IllegalArgumentException("No previous moves have been played!");

        MoveCommand moveCommand = playedMoves.pollLast();
        moveCommand.unexecute();
        updateAvailableTilesCache();
        updateFlags();
    }

    @Override
    public boolean isCurrentPlayerInCheck() {
        return currentPlayersColor == Colors.BLACK ? isBlackKingInCheckFlag : isWhiteKingInCheckFlag;
    }

    @Override
    public boolean isCurrentPlayerInCheckmate() {
        return isCurrentPlayerInCheck() && !currentPlayerHasMovesFlag;
    }

    @Override
    public boolean isGameInStalemate() {
        return !isCurrentPlayerInCheck() && !currentPlayerHasMovesFlag;
    }

    @Override
    public Pieces[][] getBoardState() {
        ChessGameBuilder builder = new ChessGameBuilder(board);
        return builder.getBoardState();
    }

    @Override
    public List<Pieces> getActiveWhitePieces() {
        return convertListToEnumeration(activeWhitePieces);
    }

    @Override
    public List<Pieces> getActiveBlackPieces() {
        return convertListToEnumeration(activeBlackPieces);
    }

    @Override
    public List<Pieces> getCapturedWhitePieces() {
        return convertListToEnumeration(capturedWhitePieces);
    }

    @Override
    public List<Pieces> getCapturedBlackPieces() {
        return convertListToEnumeration(capturedBlackPieces);
    }

    @Override
    public Colors getCurrentPlayerColor() {
        return currentPlayersColor;
    }

    @Override
    public Set<Pair<Tile, Tile>> getAvailableMovesForCurrentPlayer() {
        Set<Pair<Tile, Tile>> availableMovesForCurrentPlayer = new HashSet<>();
        for (Piece piece : availableTilesCache.keySet())
            for (Tile tile : availableTilesCache.get(piece))
                availableMovesForCurrentPlayer.add(new Pair<>(piece.getTile(), tile));
        return availableMovesForCurrentPlayer;
    }

    private List<Pieces> convertListToEnumeration(List<Piece> pieces) {
        List<Pieces> converted = new LinkedList<>();
        for (Piece p : pieces)
            converted.add(getPieceEnumeration(p));
        return converted;
    }

    private Pieces getPieceEnumeration(Piece p) {
        if (p instanceof King)
            return p.getColor() == Colors.WHITE ? Pieces.WHITE_KING : Pieces.BLACK_KING;
        else if (p instanceof Queen)
            return p.getColor() == Colors.WHITE ? Pieces.WHITE_QUEEN : Pieces.BLACK_QUEEN;
        else if (p instanceof Rook)
            return p.getColor() == Colors.WHITE ? Pieces.WHITE_ROOK : Pieces.BLACK_ROOK;
        else if (p instanceof Bishop)
            return p.getColor() == Colors.WHITE ? Pieces.WHITE_BISHOP : Pieces.BLACK_BISHOP;
        else if (p instanceof Knight)
            return p.getColor() == Colors.WHITE ? Pieces.WHITE_KNIGHT : Pieces.BLACK_KNIGHT;
        else if (p instanceof Pawn) {
            Pawn pawn = (Pawn) p;
            if (pawn.wasPromoted())
                return getPieceEnumeration(pawn.getPromotedPiece());
        }
        throw new IllegalArgumentException("No enumeration exists for the given piece.");
    }

    private void updateAvailableTilesCache() {
        Map<Piece, Set<Tile>> updatedAvailableTilesCache = new HashMap<>();
        for (Piece p : currentPlayersColor == Colors.WHITE ? activeWhitePieces : activeBlackPieces)
            updatedAvailableTilesCache.put(p, p.getAvailableTiles());
        availableTilesCache = updatedAvailableTilesCache;
    }

    private void updateFlags() {
        updateIsWhiteKingInCheckFlag();
        updateIsBlackKingInCheckFlag();
        updateCurrentPlayerHasMovesFlag();
    }

    public void updateIsWhiteKingInCheckFlag() {
        for (Piece p : activeWhitePieces)
            if (p instanceof King) {
                isWhiteKingInCheckFlag = isKingInCheck(p.getTile());
                return;
            }
    }

    public void updateIsBlackKingInCheckFlag() {
        for (Piece p : activeBlackPieces)
            if (p instanceof King) {
                isBlackKingInCheckFlag = isKingInCheck(p.getTile());
                return;
            }
    }

    private void updateCurrentPlayerHasMovesFlag() {
        currentPlayerHasMovesFlag = true;
        for (Set<Tile> tiles : availableTilesCache.values())
            if (tiles.size() != 0)
                return;
        currentPlayerHasMovesFlag = false;
    }

    private boolean isKingInCheck(Tile tile) {
        return adjacentTilesContainKingOfOppositeColor(tile) || diagonalTilesContainQueenOrBishopOfOppositeColor(tile) ||
                rectangleTilesContainQueenOrRookOfOppositeColor(tile) || tilesInLContainKnightOfOppositeColor(tile) ||
                diagonalTilesContainPawnOfOppositeColor(tile);
    }

    private boolean adjacentTilesContainKingOfOppositeColor(Tile tile) {
        Piece pieceUnderAttack = board.get(tile);
        int[][] adjacentVectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] adjacentVector : adjacentVectors) {
            Tile adjacentTile;
            // if the row and column are out of range, an exception is thrown, and we move on to the next vector
            try {
                adjacentTile = Tile.getTile(tile.getRow() + adjacentVector[0], tile.getCol() + adjacentVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece piece = board.getOrDefault(adjacentTile, null);
            if (isPieceKingOfOppositeColor(piece, pieceUnderAttack.getColor()))
                return true;
        }
        return false;
    }

    private boolean diagonalTilesContainQueenOrBishopOfOppositeColor(Tile tile) {
        Piece pieceUnderAttack = board.get(tile);
        int[][] diagonalVectors = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] diagonalVector : diagonalVectors) {
            int tilesAwayFromStart = 1;
            while (tilesAwayFromStart < SIZE_OF_CHESS_BOARD) {
                Tile diagonalTile;
                // if the row and column are out of range, an exception is thrown, and we move on to the next vector
                try {
                    diagonalTile = Tile.getTile(tile.getRow() + diagonalVector[0] * tilesAwayFromStart, tile.getCol() + diagonalVector[1] * tilesAwayFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece piece = board.getOrDefault(diagonalTile, null);
                if (isPieceQueenOfOppositeColor(piece, pieceUnderAttack.getColor()) || isPieceBishopOfOppositeColor(piece, pieceUnderAttack.getColor()))
                    return true;
                else if (piece != null)
                    break;
                tilesAwayFromStart++;
            }
        }
        return false;
    }

    private boolean rectangleTilesContainQueenOrRookOfOppositeColor(Tile tile) {
        Piece pieceUnderAttack = board.get(tile);
        int[][] rectangularVectors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] rectangularVector : rectangularVectors) {
            int tilesAwayFromStart = 1;
            while (tilesAwayFromStart < SIZE_OF_CHESS_BOARD) {
                Tile rectangularTile;
                // if the row and column are out of range, an exception is thrown, and we move on to the next vector
                try {
                    rectangularTile = Tile.getTile(tile.getRow() + rectangularVector[0] * tilesAwayFromStart, tile.getCol() + rectangularVector[1] * tilesAwayFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece piece = board.getOrDefault(rectangularTile, null);
                if (isPieceQueenOfOppositeColor(piece, pieceUnderAttack.getColor()) || isPieceRookOfOppositeColor(piece, pieceUnderAttack.getColor()))
                    return true;
                else if (piece != null)
                    break;
                tilesAwayFromStart++;
            }
        }
        return false;
    }

    private boolean tilesInLContainKnightOfOppositeColor(Tile tile) {
        Piece pieceUnderAttack = board.get(tile);
        int[][] lShapeVectors = {{-1, 2}, {-1, -2}, {1, 2}, {1, -2}, {-2, -1}, {-2, 1}, {2, -1}, {2, 1}};
        for (int[] lShapeVector : lShapeVectors) {
            Tile adjacentTile;
            // if the row and column are out of range, an exception is thrown, and we move on to the next vector
            try {
                adjacentTile = Tile.getTile(tile.getRow() + lShapeVector[0], tile.getCol() + lShapeVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece piece = board.getOrDefault(adjacentTile, null);
            if (isPieceKnightOfOppositeColor(piece, pieceUnderAttack.getColor()))
                return true;
        }
        return false;
    }

    private boolean diagonalTilesContainPawnOfOppositeColor(Tile tile) {
        Piece pieceUnderAttack = board.get(tile);
        int[][] diagonalVectors = pieceUnderAttack.getColor() == Colors.WHITE ? new int[][] {{-1, -1}, {-1, 1}} : new int[][] {{1, -1}, {1, 1}};

        for (int[] diagonalVector : diagonalVectors) {
            Tile diagonalTile;
            // if the row and column are out of range, an exception is thrown, and we move on to the next vector
            try {
                diagonalTile = Tile.getTile(tile.getRow() + diagonalVector[0], tile.getCol() + diagonalVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece piece = board.getOrDefault(diagonalTile, null);
            if (isPiecePawnOfOppositeColor(piece, pieceUnderAttack.getColor()))
                return true;
        }
        return false;
    }

    private boolean isPieceKingOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece instanceof King && piece.getColor() != color;
    }

    private boolean isPieceQueenOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece.getColor() != color && (piece instanceof Queen || piece instanceof Pawn && ((Pawn) piece).getPromotedPiece() instanceof Queen);
    }

    private boolean isPieceRookOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece.getColor() != color && (piece instanceof Rook || piece instanceof Pawn && ((Pawn) piece).getPromotedPiece() instanceof Rook);
    }

    private boolean isPieceBishopOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece.getColor() != color && (piece instanceof Bishop || piece instanceof Pawn && ((Pawn) piece).getPromotedPiece() instanceof Bishop);
    }

    private boolean isPieceKnightOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece.getColor() != color && (piece instanceof Knight || piece instanceof Pawn && ((Pawn) piece).getPromotedPiece() instanceof Knight);
    }

    private boolean isPiecePawnOfOppositeColor(Piece piece, Colors color) {
        return piece != null && piece.getColor() != color && piece instanceof Pawn && !((Pawn) piece).wasPromoted();
    }

    public void executeCapture(Tile tile) {
        Piece piece = getPieceAt(tile);
        if (piece.getColor() == Colors.WHITE) {
            activeWhitePieces.remove(piece);
            capturedWhitePieces.add(piece);
        } else {
            activeBlackPieces.remove(piece);
            capturedBlackPieces.add(piece);
        }
        board.remove(tile);
        piece.setTile(null);
    }

    public void executeMovement(Pair<Tile, Tile> movement) {
        Piece p = getPieceAt(movement.getKey());
        board.remove(movement.getKey());
        if (board.containsKey(movement.getValue()))
            throw new IllegalArgumentException("Cannot move piece to an occupied tile.");
        board.put(movement.getValue(), p);
        p.setTile(movement.getValue());
    }

    public void unexecuteCapture(Tile tile, Piece piece) {
        if (board.containsKey(tile))
            throw new IllegalArgumentException("Cannot reverse capture if a piece is in the captured piece's spot.");
        board.put(tile, piece);
        piece.setTile(tile);
        if (piece.getColor() == Colors.WHITE) {
            capturedWhitePieces.remove(piece);
            activeWhitePieces.add(piece);
        } else {
            capturedBlackPieces.remove(piece);
            activeBlackPieces.add(piece);
        }
    }

    public void switchCurrentPlayerColor() {
        if (currentPlayersColor == Colors.WHITE)
            currentPlayersColor = Colors.BLACK;
        else
            currentPlayersColor = Colors.WHITE;
    }

    public void addMoveCommandToPlayedMoves(MoveCommand moveCommand) {
        playedMoves.add(moveCommand);
    }

    public boolean isWhiteKingInCheck() {
        return isWhiteKingInCheckFlag;
    }

    public boolean isBlackKingInCheck() {
        return isBlackKingInCheckFlag;
    }

    public Colors getCurrentPlayersColor() {
        return currentPlayersColor;
    }

    public Piece getPieceAt(Tile tile) {
        return board.get(tile);
    }

    public void pollLastMoveCommand() {
        playedMoves.pollLast();
    }

    public boolean movedFirstTimeLastTurn(Tile tile) {
        return playedMoves.getLast().pieceWasMovedFirstTime(tile);
    }
}
