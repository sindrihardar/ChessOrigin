package com.chess.model.ai;

import com.chess.model.game.ChessGameInterface;
import com.chess.model.game.Tile;
import com.chess.model.util.Colors;
import com.chess.model.util.Pair;
import com.chess.model.util.Pieces;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.chess.model.util.Pieces.*;

public class Cheddar implements AIInterface {
    private ChessGameInterface game;
    private Map<Pieces, int[][]> pieceTables;

    private int[][] PAWN_PIECE_SQUARE =        {{0,     0,      0,      0,      0,      0,      0,      0},
                                                {50,    50,     50,     50,     50,     50,     50,     50},
                                                {10,    10,     20,     30,     30,     20,     10,     10},
                                                {5,     5,      10,     25,     25,     10,     5,      5},
                                                {0,     0,      0,      20,     20,     0,      0,      0},
                                                {5,     -5,     -10,    0,      0,      -10,    -5,     5},
                                                {5,     10,     10,     -20,    -20,    10,     10,     5},
                                                {0,     0,      0,      0,      0,      0,      0,      0}};

    private int[][] KNIGHT_PIECE_SQUARE =      {{-50,   -40,    -30,    -30,    -30,    -30,    -40,    -50},
                                                {-40,   -20,    0,      0,      0,      0,      -20,    -40},
                                                {-30,   0,      10,     15,     15,     10,     0,      -30},
                                                {-30,   5,      15,     20,     20,     15,     5,      -30},
                                                {-30,   0,      15,     20,     20,     15,     0,      -30},
                                                {-30,   5,      10,     15,     15,     10,     5,      -30},
                                                {-40,   -20,    0,      5,      5,      0,      -20,    -40},
                                                {-50,   -40,    -30,    -30,    -30,    -30,    -40,    -50}};

    private int[][] BISHOP_PIECE_SQUARE =      {{-20,   -10,    -10,    -10,    -10,    -10,    -10,    -20},
                                                {-10,   0,      0,      0,      0,      0,      0,      -10},
                                                {-10,   0,      5,      10,     10,     5,      0,      -10},
                                                {-10,   5,      5,      10,     10,     5,      5,      -10},
                                                {-10,   0,      10,     10,     10,     10,     0,      -10},
                                                {-10,   10,     10,     10,     10,     10,     10,     -10},
                                                {-10,   5,      0,      0,      0,      0,      5,      -10},
                                                {-20,   -10,    -10,    -10,    -10,    -10,    -10,    -20}};

    private int[][] ROOK_PIECE_SQUARE =        {{0,     0,      0,      0,      0,      0,      0,      0},
                                                {5,     10,     10,     10,     10,     10,     10,     5},
                                                {-5,    0,      0,      0,      0,      0,      0,      -5},
                                                {-5,    0,      0,      0,      0,      0,      0,      -5},
                                                {-5,    0,      0,      0,      0,      0,      0,      -5},
                                                {-5,    0,      0,      0,      0,      0,      0,      -5},
                                                {-5,    0,      0,      0,      0,      0,      0,      -5},
                                                {0,     0,      0,      5,      5,      0,      0,      0}};

    private int[][] QUEEN_PIECE_SQUARE =        {{-20,  -10,    -10,    -5,     -5,     -10,    -10,    -20},
                                                 {-10,  0,      0,      0,      0,      0,      0,      -10},
                                                 {-10,  0,      5,      5,      5,      5,      0,      -10},
                                                 {-5,   0,      5,      5,      5,      5,      0,      -5},
                                                 {0,    0,      5,      5,      5,      5,      0,      -5},
                                                 {-10,  5,      5,      5,      5,      5,      0,      -10},
                                                 {-10,  0,      5,      0,      0,      0,      0,      -10},
                                                 {-20,  -10,    -10,    -5,     -5,     -10,    -10,    -20}};

    public Cheddar(ChessGameInterface game) {
        this.game = game;
        pieceTables = new HashMap<>();

        pieceTables.put(WHITE_QUEEN, QUEEN_PIECE_SQUARE);
        pieceTables.put(BLACK_QUEEN, reverse(QUEEN_PIECE_SQUARE));

        pieceTables.put(WHITE_ROOK, ROOK_PIECE_SQUARE);
        pieceTables.put(BLACK_ROOK, reverse(ROOK_PIECE_SQUARE));

        pieceTables.put(WHITE_BISHOP, BISHOP_PIECE_SQUARE);
        pieceTables.put(BLACK_BISHOP, reverse(BISHOP_PIECE_SQUARE));

        pieceTables.put(WHITE_KNIGHT, KNIGHT_PIECE_SQUARE);
        pieceTables.put(BLACK_KNIGHT, reverse(KNIGHT_PIECE_SQUARE));

        pieceTables.put(WHITE_PAWN, PAWN_PIECE_SQUARE);
        pieceTables.put(BLACK_PAWN, reverse(PAWN_PIECE_SQUARE));
    }

    private int[][] reverse(int[][] table) {
        int[][] reversed = new int[table.length][table[0].length];
        for (int i = 0; i < table.length; i++)
            for (int j = 0; j < table.length; j++)
                reversed[i][j] = -table[table.length - 1 - i][j];
        return reversed;
    }

    private Pair<Tile, Tile> minimax(int depth) {
        if (game.getCurrentPlayerColor() == Colors.WHITE)
            return max(depth, Integer.MIN_VALUE, Integer.MAX_VALUE).pair;
        return min(depth, Integer.MIN_VALUE, Integer.MAX_VALUE).pair;
    }

    private ReturnNodeFromMinimax min(int depth, int alpha, int beta) {
        if (depth < 0)
            throw new IllegalArgumentException("Depth cannot be less than 0 for minimax.");
        else if (depth == 0)
            return new ReturnNodeFromMinimax(null, evaluate(game));
        else if (game.isGameInStalemate() || game.isCurrentPlayerInCheckmate())
            return new ReturnNodeFromMinimax(null, Integer.MAX_VALUE);

        ReturnNodeFromMinimax min = null;
        Set<Pair<Tile, Tile>> availableMoves = game.getAvailableMovesForCurrentPlayer();
        for (Iterator<Pair<Tile, Tile>> it = availableMoves.iterator(); it.hasNext(); ) {
            Pair<Tile, Tile> move = it.next();
            if (min == null)
                min = new ReturnNodeFromMinimax(move, Integer.MAX_VALUE);
            game.move(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
            ReturnNodeFromMinimax returned = max(depth - 1, alpha, beta);
            if (returned.value < min.value) {
                min.pair = move;
                min.value = returned.value;
            }
            game.undoLastMove();
            if (returned.value < alpha)
                break;
            beta = Math.min(beta, returned.value);
        }

        return min;
    }

    private ReturnNodeFromMinimax max(int depth, int alpha, int beta) {
        if (depth < 0)
            throw new IllegalArgumentException("Depth cannot be less than 0 for minimax.");
        else if (depth == 0)
            return new ReturnNodeFromMinimax(null, evaluate(game));
        else if (game.isGameInStalemate() || game.isCurrentPlayerInCheckmate())
            return new ReturnNodeFromMinimax(null, Integer.MIN_VALUE);

        ReturnNodeFromMinimax max = null;
        Set<Pair<Tile, Tile>> availableMoves = game.getAvailableMovesForCurrentPlayer();
        for (Iterator<Pair<Tile, Tile>> it = availableMoves.iterator(); it.hasNext(); ) {
            Pair<Tile, Tile> move = it.next();
            if (max == null)
                max = new ReturnNodeFromMinimax(move, Integer.MIN_VALUE);
            game.move(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
            ReturnNodeFromMinimax returned = min(depth - 1, alpha, beta);
            if (returned.value > max.value) {
                max.pair = move;
                max.value = returned.value;
            }
            game.undoLastMove();
            if (returned.value > beta)
                break;
            alpha = Math.max(alpha, returned.value);
        }

        return max;
    }

    private int evaluate(ChessGameInterface game) {
        int material = 0;
        for (Pieces piece : game.getActiveWhitePieces())
            material += getPieceValue(piece);
        for (Pieces piece : game.getActiveBlackPieces())
            material -= getPieceValue(piece);

        Pieces[][] boardState = game.getBoardState();
        int position = 0;
        for (int i = 0; i < boardState.length; i++)
            for (int j = 0; j < boardState.length; j++)
                position += getPositionalValue(i, j, boardState[i][j]);
        return material + position;
    }

    private int getPositionalValue(int row, int col, Pieces piece) {
        if (piece == null || piece == WHITE_KING || piece == BLACK_KING)
            return 0;

        return pieceTables.get(piece)[row][col];
    }

    private int getPieceValue(Pieces piece) {
        if (piece == null)
            throw new IllegalArgumentException("Piece cannot be null if we want to get it's value.");

        if (piece == WHITE_KING || piece == BLACK_KING)
            return 20000;
        else if (piece == WHITE_QUEEN || piece == BLACK_QUEEN)
            return 900;
        else if (piece == WHITE_ROOK || piece == BLACK_ROOK)
            return 500;
        else if (piece == WHITE_BISHOP || piece == BLACK_BISHOP)
            return 330;
        else if (piece == WHITE_KNIGHT || piece == BLACK_KNIGHT)
            return 320;
        else
            return 100;
    }

    @Override
    public void move() {
        Pair<Tile, Tile> move = minimax(2);
        game.move(move.getKey().getRow(), move.getKey().getCol(), move.getValue().getRow(), move.getValue().getCol());
    }

    @Override
    public void setGame(ChessGameInterface game) {
        this.game = game;
    }

    class ReturnNodeFromMinimax {
        Pair<Tile, Tile> pair;
        int value;

        ReturnNodeFromMinimax(Pair<Tile, Tile> pair, int value) {
            this.pair = pair;
            this.value = value;
        }
    }
}
