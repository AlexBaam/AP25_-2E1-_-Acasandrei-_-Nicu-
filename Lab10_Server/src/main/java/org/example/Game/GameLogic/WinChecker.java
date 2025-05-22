package org.example.Game.GameLogic;

import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.HexChecker;

import java.util.*;

public class WinChecker {
    public static boolean checkWin(Board board, Cell playerColor) {
        int size = board.getSize();
        boolean[][] visited = new boolean[size][size];
        Deque<int[]> stack = new ArrayDeque<>();

        System.out.println("[DEBUG] Checking win for: " + playerColor);

        if (playerColor == Cell.RED) {
            for (int col = 0; col < size; col++) {
                if (board.getCell(0, col) == Cell.RED) {
                    stack.push(new int[]{0, col});
                    visited[0][col] = true;
                    System.out.println("[DEBUG] RED start from: (0, " + col + ")");
                }
            }

            while (!stack.isEmpty()) {
                int[] pos = stack.pop();
                int row = pos[0];
                int col = pos[1];

                if (row == size - 1) {
                    System.out.println("[DEBUG] RED reached bottom at: (" + row + ", " + col + ")");
                    return true;
                }

                for (int[] neighbor : HexChecker.getAroundTheHex(row, col, size)) {
                    int newRow = neighbor[0];
                    int newCol = neighbor[1];
                    if (!visited[newRow][newCol] && board.getCell(newRow, newCol) == Cell.RED) {
                        visited[newRow][newCol] = true;
                        stack.push(new int[]{newRow, newCol});
                        System.out.println("[DEBUG] RED visiting: (" + newRow + ", " + newCol + ")");
                    }
                }
            }

        } else if (playerColor == Cell.BLUE) {
            for (int row = 0; row < size; row++) {
                if (board.getCell(row, 0) == Cell.BLUE) {
                    stack.push(new int[]{row, 0});
                    visited[row][0] = true;
                    System.out.println("[DEBUG] BLUE start from: (" + row + ", 0)");
                }
            }

            while (!stack.isEmpty()) {
                int[] pos = stack.pop();
                int row = pos[0];
                int col = pos[1];

                if (col == size - 1) {
                    System.out.println("[DEBUG] BLUE reached right edge at: (" + row + ", " + col + ")");
                    return true;
                }

                for (int[] neighbor : HexChecker.getAroundTheHex(row, col, size)) {
                    int newRow = neighbor[0];
                    int newCol = neighbor[1];
                    if (!visited[newRow][newCol] && board.getCell(newRow, newCol) == Cell.BLUE) {
                        visited[newRow][newCol] = true;
                        stack.push(new int[]{newRow, newCol});
                        System.out.println("[DEBUG] BLUE visiting: (" + newRow + ", " + newCol + ")");
                    }
                }
            }
        }

        return false;
    }
}