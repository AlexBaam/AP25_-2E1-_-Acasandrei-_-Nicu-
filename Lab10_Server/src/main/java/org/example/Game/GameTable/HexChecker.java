package org.example.Game.GameTable;

import java.util.*;

public class HexChecker {

    public static List<int[]> getAroundTheHex(int row, int col, int boardSize) {
        List<int[]> neighbours = new ArrayList<>();

        // Adjusted for flat-topped hexes
        int[][] evenRowOffsets = {
                {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, 0}, {1, 1}
        };

        int[][] oddRowOffsets = {
                {-1, -1}, {-1, 0},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}
        };

        int[][] offsets = (row % 2 == 0) ? evenRowOffsets : oddRowOffsets;

        for (int[] offset : offsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (inBounds(newRow, newCol, boardSize)) {
                neighbours.add(new int[]{newRow, newCol});
            }
        }

        return neighbours;
    }

    private static boolean inBounds(int row, int col, int boardSize) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
}