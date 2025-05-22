package org.example.Game.GameLogic;

import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.HexChecker;

import java.util.ArrayDeque;
import java.util.Deque;

public class PossiblePath {
    public PossiblePath() {}

    public static boolean hasPossiblePath(Board board, Cell playerColor) {
        int size = board.getSize();
        boolean[][] visited = new boolean[size][size];

        Deque<int[]> stack = new ArrayDeque<>();

        if(playerColor == Cell.RED){
            for(int col = 0; col < size; col++){
                if(canVisit(board.getCell(0, col), playerColor)){
                    stack.push(new int[]{0, col});
                    visited[0][col] = true;
                }
            }

            while(!stack.isEmpty()){
                int[] possible = stack.pop();
                int row = possible[0];
                int col = possible[1];

                if(row == size - 1){
                    return true;
                }

                for(int[] neighbor : HexChecker.getAroundTheHex(row, col, size)){
                    int neighborRow = neighbor[0];
                    int neighborCol = neighbor[1];
                    if(!visited[neighborRow][neighborCol]){
                        if (canVisit(board.getCell(neighborRow, neighborCol), playerColor)){
                            visited[neighborRow][neighborCol] = true;
                            stack.push(new int[]{neighborRow, neighborCol});
                        }
                    }
                }
            }
        } else if(playerColor == Cell.BLUE){
            for(int row = 0; row < size; row++){
                if(canVisit(board.getCell(row, 0), playerColor)){
                    stack.push(new int[]{row, 0});
                    visited[row][0] = true;
                }
            }

            while(!stack.isEmpty()){
                int[] possible = stack.pop();
                int row = possible[0];
                int col = possible[1];

                if(col == size - 1){
                    return true;
                }

                for(int[] neighbor : HexChecker.getAroundTheHex(row, col, size)){
                    int neighborRow = neighbor[0];
                    int neighborCol = neighbor[1];
                    if(!visited[neighborRow][neighborCol]){
                        if (canVisit(board.getCell(neighborRow, neighborCol), playerColor)){
                            visited[neighborRow][neighborCol] = true;
                            stack.push(new int[]{neighborRow, neighborCol});
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean canVisit(Cell cell, Cell playerColor) {
        return cell == playerColor || cell == Cell.EMPTY;
    }
}
