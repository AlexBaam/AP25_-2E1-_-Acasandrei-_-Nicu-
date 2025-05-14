package org.example.Game.GameTable;

import java.util.*;

public class Board {
    private final int size;
    private final Cell[][] grid;

    public Board(int size) {
        this.size = size;
        grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], Cell.EMPTY);
        }
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getCell(int row, int collum) {
        return grid[row][collum];
    }

    public void setCell(int row, int collum, Cell cell) {
        grid[row][collum] = cell;
    }
}
