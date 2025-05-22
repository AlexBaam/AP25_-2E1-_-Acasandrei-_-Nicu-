
// HexBoardModel.java
package org.example.lab10_client_gui.Board;

import java.util.Arrays;

/**
 * Stores the state of the hex board (empty, red, or blue cells).
 */
public class HexBoardModel {
    private final int    size;
    private final Cell[][] grid;

    public HexBoardModel(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], Cell.EMPTY);
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Place a stone if the cell is empty.
     * @return true on success.
     */
    public boolean placeStone(int row, int col, Cell color) {
        if (grid[row][col] == Cell.EMPTY) {
            grid[row][col] = color;
            return true;
        }
        return false;
    }
}
