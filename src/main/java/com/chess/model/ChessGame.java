package com.chess.model;

import java.util.*;

public class ChessGame implements ChessGameInterface {
    private static final Pieces WK = Pieces.WHITE_KING, WQ = Pieces.WHITE_QUEEN, WR = Pieces.WHITE_ROOK, WB = Pieces.WHITE_BISHOP,
    Wk = Pieces.WHITE_KNIGHT, WP = Pieces.WHITE_PAWN, BK = Pieces.BLACK_KING, BQ = Pieces.BLACK_QUEEN, BR = Pieces.BLACK_ROOK,
    BB = Pieces.BLACK_BISHOP, Bk = Pieces.BLACK_KNIGHT, BP = Pieces.BLACK_PAWN, NN = null;

    private static final int SIZE_OF_CHESS_BOARD = 8;

    private static final Pieces[][] initialState = {{BR, Bk, BB, BQ, BK, BB, Bk, BR},
                                                    {BP, BP, BP, BP, BP, BP, BP, BP},
                                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                                    {NN, NN, NN, NN, NN, NN, NN, NN},
                                                    {WP, WP, WP, WP, WP, WP, WP, WP},
                                                    {WR, Wk, WB, WQ, WK, WB, Wk, WR}};

    private Colors currentPlayersColor;
    private List<Piece> activeWhitePieces, activeBlackPieces, capturedWhitePieces, capturedBlackPieces;
    private Map<Space, Piece> board;
    private ArrayDeque<MoveCommand> playedMoves;
    private Map<Piece, Set<Space>> availableSpacesCache;
    private boolean isBlackKingInCheckFlag, isWhiteKingInCheckFlag, currentPlayerHasMovesFlag;

    public ChessGame(Colors color, Pieces[][] board) {
        currentPlayersColor = color;
        ChessGameBuilder builder = new ChessGameBuilder(this, board);
        activeWhitePieces = builder.getActiveWhitePieces();
        activeBlackPieces = builder.getActiveBlackPieces();
        capturedWhitePieces = new LinkedList<>();
        capturedBlackPieces = new LinkedList<>();
        this.board = builder.getBoardMap();
        playedMoves = new ArrayDeque<>();
        updateAvailableSpacesCache();
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

    @Override
    public boolean doesSpaceContainAPieceOfTheCurrentPlayersColor(int row, int col) {
        Piece piece = board.getOrDefault(Space.getSpace(row, col), null);

        if (piece == null)
            return false;

        return piece.getColor() == currentPlayersColor;
    }


    @Override
    public Set<Space> getAvailableMovesForSpace(int row, int col) {
        if (isGameInStalemate() || isCurrentPlayerInCheckmate())
            throw new IllegalArgumentException("Game is over!");

        if (!doesSpaceContainAPieceOfTheCurrentPlayersColor(row, col))
            throw new IllegalArgumentException("Specified space does not contain a piece of the current player's color.");

        return availableSpacesCache.get(board.get(Space.getSpace(row, col)));
    }

    @Override
    public void move(int startRow, int startCol, int endRow, int endCol) {
        Space startingSpace = Space.getSpace(startRow, startCol), endingSpace = Space.getSpace(endRow, endCol);
        isMovementLegal(startingSpace, endingSpace);
        resolveMovement(startingSpace, endingSpace);
        updateAvailableSpacesCache();
        updateFlags();
    }

    public void resolveMovement(Space startingSpace, Space endingSpace) {
        MoveCommand moveCommand = board.get(startingSpace).createMoveCommand(endingSpace);
        moveCommand.execute();
    }

    public boolean isMovementLegal(Space startingSpace, Space endingSpace) {
        if (!doesSpaceContainAPieceOfTheCurrentPlayersColor(startingSpace.getRow(), startingSpace.getCol()))
            throw new IllegalArgumentException("Starting space does not contain a piece of the current player's color.");
        Piece p = board.get(startingSpace);
        if (!availableSpacesCache.get(p).contains(endingSpace))
            throw new IllegalArgumentException("Ending space is not available to be moved to.");
        if (isCurrentPlayerInCheckmate() || isGameInStalemate())
            throw new IllegalArgumentException("Cannot move because game is over.");
        return true;
    }

    @Override
    public void undoLastMove() {
        if (playedMoves.size() == 0)
            throw new IllegalArgumentException("No previous moves have been played!");
        MoveCommand moveCommand = playedMoves.pollLast();
        moveCommand.unexecute();
        updateAvailableSpacesCache();
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

    public List<Pieces> convertListToEnumeration(List<Piece> pieces) {
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

    private void updateAvailableSpacesCache() {
        Map<Piece, Set<Space>> updatedAvailableSpacesCache = new HashMap<>();
        for (Piece p : currentPlayersColor == Colors.WHITE ? activeWhitePieces : activeBlackPieces)
            updatedAvailableSpacesCache.put(p, p.getAvailableSpaces());
        availableSpacesCache = updatedAvailableSpacesCache;
    }

    private void updateFlags() {
        updateIsWhiteKingInCheckFlag();
        updateIsBlackKingInCheckFlag();
        updateCurrentPlayerHasMovesFlag();
    }

    public void updateIsWhiteKingInCheckFlag() {
        for (Piece p : activeWhitePieces)
            if (p instanceof King) {
                isWhiteKingInCheckFlag = isKingInCheck(p.getSpace());
                return;
            }
    }

    public void updateIsBlackKingInCheckFlag() {
        for (Piece p : activeBlackPieces)
            if (p instanceof King) {
                isBlackKingInCheckFlag = isKingInCheck(p.getSpace());
                return;
            }
    }

    private boolean isKingInCheck(Space space) {
        return adjacentSpacesContainsKing(space) || diagonalContainsQueenOrBishop(space) || rectangleContainsQueenOrRook(space) ||
                spacesInLContainKnight(space) || diagonalSpacesContainPawn(space);
    }

    private boolean adjacentSpacesContainsKing(Space space) {
        int[][] adjacentVectors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] adjacentVector : adjacentVectors) {
            Space adjacentSpace;
            try {
                adjacentSpace = Space.getSpace(space.getRow() + adjacentVector[0], space.getCol() + adjacentVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece p = board.getOrDefault(adjacentSpace, null);
            if (p instanceof King && p.getColor() != board.get(space).getColor())
                return true;
        }
        return false;
    }

    private boolean diagonalContainsQueenOrBishop(Space space) {
        int[][] diagonalVectors = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] diagonalVector : diagonalVectors) {
            int spacesAwayFromStart = 1;
            while (spacesAwayFromStart < SIZE_OF_CHESS_BOARD) {
                Space diagonalSpace;
                try {
                    diagonalSpace = Space.getSpace(space.getRow() + diagonalVector[0] * spacesAwayFromStart, space.getCol() + diagonalVector[1] * spacesAwayFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece p = board.getOrDefault(diagonalSpace, null);
                if (p instanceof Queen && p.getColor() != board.get(space).getColor() || p instanceof Bishop && p.getColor() != board.get(space).getColor() || pieceIsAPromotedPawnQueenWithOppositeColor(p, board.get(space).getColor()))
                    return true;
                else if (p != null)
                    break;
                spacesAwayFromStart++;
            }
        }
        return false;
    }

    private boolean rectangleContainsQueenOrRook(Space space) {
        int[][] rectangularVectors = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] rectangularVector : rectangularVectors) {
            int spacesAwayFromStart = 1;
            while (spacesAwayFromStart < SIZE_OF_CHESS_BOARD) {
                Space diagonalSpace;
                try {
                    diagonalSpace = Space.getSpace(space.getRow() + rectangularVector[0] * spacesAwayFromStart, space.getCol() + rectangularVector[1] * spacesAwayFromStart);
                } catch (Exception e) {
                    break;
                }
                Piece p = board.getOrDefault(diagonalSpace, null);
                if (p instanceof Queen && p.getColor() != board.get(space).getColor() || p instanceof Rook && p.getColor() != board.get(space).getColor() ||
                    pieceIsAPromotedPawnQueenWithOppositeColor(p, board.get(space).getColor()))
                    return true;
                else if (p != null)
                    break;
                spacesAwayFromStart++;
            }
        }
        return false;
    }

    private boolean spacesInLContainKnight(Space space) {
        int[][] lShapeVectors = {{-1, 2}, {-1, -2}, {1, 2}, {1, -2}, {-2, -1}, {-2, 1}, {2, -1}, {2, 1}};
        for (int[] lShapeVector : lShapeVectors) {
            Space adjacentSpace;
            try {
                adjacentSpace = Space.getSpace(space.getRow() + lShapeVector[0], space.getCol() + lShapeVector[1]);
            } catch (Exception e) {
                continue;
            }
            Piece p = board.getOrDefault(adjacentSpace, null);
            if (p instanceof Knight && p.getColor() != board.get(space).getColor())
                return true;
        }
        return false;
    }

    private boolean diagonalSpacesContainPawn(Space space) {
        int[][] diagonalVectors;
        if (board.get(space).getColor() == Colors.WHITE)
            diagonalVectors = new int[][] {{-1, -1}, {-1, 1}};
        else
            diagonalVectors = new int[][] {{1, -1}, {1, 1}};

        for (int[] diagonalVector : diagonalVectors) {
            Space diagonalSpace;
            try {
                diagonalSpace = Space.getSpace(space.getRow() + diagonalVector[0], space.getCol() + diagonalVector[1]);
            } catch (Exception e) {
                break;
            }
            Piece p = board.getOrDefault(diagonalSpace, null);
            if (p instanceof Pawn && p.getColor() != board.get(space).getColor())
                return true;
        }
        return false;
    }

    private boolean pieceIsAPromotedPawnQueenWithOppositeColor(Piece p, Colors color) {
        if (!(p instanceof Pawn))
            return false;
        Pawn pawn = (Pawn) p;
        return pawn.wasPromoted() && pawn.getPromotedPiece() instanceof Queen && pawn.getColor() != color;
    }

    private void updateCurrentPlayerHasMovesFlag() {
        currentPlayerHasMovesFlag = true;
        for (Set<Space> spaces : availableSpacesCache.values())
            if (spaces.size() != 0)
                return;
        currentPlayerHasMovesFlag = false;
    }

    public Piece getPieceAt(Space space) {
        return board.get(space);
    }

    public void executeCapture(Space space) {
        Piece p = getPieceAt(space);
        if (p.getColor() == Colors.WHITE) {
            activeWhitePieces.remove(p);
            capturedWhitePieces.add(p);
        } else {
            activeBlackPieces.remove(p);
            capturedBlackPieces.add(p);
        }
        board.remove(space);
        p.setSpace(null);
    }

    public void executeMovement(Pair<Space, Space> movement) {
        Piece p = getPieceAt(movement.getKey());
        board.remove(movement.getKey());
        if (board.containsKey(movement.getValue()))
            throw new IllegalArgumentException("Cannot move piece to an occupied space.");
        board.put(movement.getValue(), p);
        p.setSpace(movement.getValue());
    }

    public void unexecuteCapture(Space space, Piece piece) {
        if (board.containsKey(space))
            throw new IllegalArgumentException("Cannot reverse capture if a piece is in the captured piece's spot.");
        board.put(space, piece);
        piece.setSpace(space);
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

    public void pollLastMoveCommand() {
        playedMoves.pollLast();
    }

    public boolean movedFirstTimeLastTurn(Space space) {
        return playedMoves.getLast().pieceWasMovedFirstTime(space);
    }
}
