package com.chess.model;

import java.util.HashMap;
import java.util.Map;

public class Space {
    private static final int SIZE_OF_CHESS_BOARD = 8;
    private int row, col;
    private static final Map<Integer, Space> SPACES = new HashMap<>() {{
        for (int i = 0; i < SIZE_OF_CHESS_BOARD; i++)
            for (int j = 0; j < SIZE_OF_CHESS_BOARD; j++)
                put(i * SIZE_OF_CHESS_BOARD + j, new Space(i, j));
    }};

    private Space(int row, int col) {
        if (!isIndexInRange(row))
            throw new IllegalArgumentException("Row index is out of range.");
        else if (!isIndexInRange(col))
            throw new IllegalArgumentException("Column index is out of range.");

        this.row = row;
        this.col = col;
    }

    private static boolean isIndexInRange(int index) {
        return index >= 0 && index < SIZE_OF_CHESS_BOARD;
    }

    public static Space getSpace(int row, int col) {
        if (!isIndexInRange(row))
            throw new IllegalArgumentException("Row index is out of range.");
        else if (!isIndexInRange(col))
            throw new IllegalArgumentException("Column index is out of range.");

        return SPACES.get(row * SIZE_OF_CHESS_BOARD + col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
