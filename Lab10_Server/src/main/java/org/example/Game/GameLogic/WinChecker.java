package org.example.Game.GameLogic;

import org.example.Game.GameTable.Board;
import org.example.Game.GameTable.Cell;
import org.example.Game.GameTable.HexChecker;

import java.util.ArrayDeque;
import java.util.Deque;

public class WinChecker {
    public static boolean checkWin(Board board, Cell playerColor) {
        int size = board.getSize();
        boolean[][] visited = new boolean[size][size]; // Verificam daca o caseta a fost vizitata
        Deque<int[]> stack = new ArrayDeque<>();
        /*
        Ptr a verifica daca castigam folosim o pargurgere DFS realizata cu ajutorul unei stive
         */

        if(playerColor == Cell.RED){
            // RED: Ptr rosu cautam conexiuni de sus pana jos, adica de la rand 0 la rand size -1
            for(int col = 0; col < size; col++){
                if(board.getCell(0,col) == playerColor){
                    stack.push(new int[]{0,col});
                    visited[0][col] = true;
                }
            } // Parcurgem prima linie ce este rosie, si le notam pe toate vizitate

            while(!stack.isEmpty()){
                int[] pos = stack.pop(); // Scoatem varful stivei
                int row = pos[0]; // Prima pozitie din stiva (de sus)
                int col = pos[1]; // Fix sub prima pozitie in stiva
                // Un teanc de clatite, prima e cea de deasupra

                if(row == size - 1){
                    return true; // Suntem la marginea de jos pe aceeasi culoare
                }

                for(int[] neighbor : HexChecker.getAroundTheHex(row, col, size)){
                    // Luam toti vecinii ptr un hex, si apoi ii verificam daca ii putem in stiva sau nu
                    int newRow = neighbor[0];
                    int newCol = neighbor[1];

                    if(!visited[newRow][newCol]){
                        if(board.getCell(newRow,newCol) == playerColor){
                            stack.push(new int[]{newRow,newCol});
                            visited[newRow][newCol] = true;
                        }
                    } // Verificam daca vecinii sunt rosii si nevizitati, apoi ii punem in stiva
                }
            }
        } else if(playerColor == Cell.BLUE) {
            // BLUE: Caut de la stanga la dreapta conexiune, adica de la coloana 0 la coloana size -1
            for (int row = 0; row < size; row++) {
                if (board.getCell(row, 0) == playerColor) {
                    stack.push(new int[]{row, 0});
                    visited[row][0] = true;
                }
            }

            while (!stack.isEmpty()) {
                int[] pos = stack.pop();
                int row = pos[0];
                int col = pos[1];

                if (col == size - 1) {
                    return true; // Suntem la marginea de jos pe aceeasi culoare
                }

                for (int[] neighbor : HexChecker.getAroundTheHex(row, col, size)) {
                    int newRow = neighbor[0];
                    int newCol = neighbor[1];

                    if (!visited[newRow][newCol]) {
                        if (board.getCell(newRow, newCol) == playerColor) {
                            stack.push(new int[]{newRow, newCol});
                            visited[newRow][newCol] = true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
