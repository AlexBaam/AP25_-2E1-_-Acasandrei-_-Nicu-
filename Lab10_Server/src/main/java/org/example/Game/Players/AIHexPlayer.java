package org.example.Game.Players;

import org.example.Game.GameLogic.PossiblePath;
import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.HexChecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AIHexPlayer {
    String nameAI;

    public AIHexPlayer() {
        nameAI = "AI";
    }

    public int[] chooseMove(Board board, int lastHumanRow, int lastHumanCol) {

        int size = board.getSize();
        List<int[]> possibleMoves = new ArrayList<>();

        if((lastHumanRow >= 0) && (lastHumanCol >= 0)){
            for(int[] neighbor : HexChecker.getAroundTheHex(lastHumanRow, lastHumanCol, size)){
                int row = neighbor[0];
                int col = neighbor[1];

                if(board.getCell(row, col) == Cell.EMPTY){

                    Board tempBoard = cloneBoard(board);
                    tempBoard.setCell(row, col, Cell.BLUE);

                    if(PossiblePath.hasPossiblePath(tempBoard, Cell.BLUE)){
                        possibleMoves.add(new int[]{row, col});
                    }
                }
            }

            if(!possibleMoves.isEmpty()){
                Collections.shuffle(possibleMoves);
                return possibleMoves.get(0);
            }
        }

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board.getCell(row, col) == Cell.EMPTY){
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    public String getNameAI() {
        return nameAI;
    }

    private Board cloneBoard(Board board){
        int size = board.getSize();
        Board newBoard = new Board(size);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                newBoard.setCell(row, col, board.getCell(row, col));
            }
        }

        return newBoard;
    }
}
