package org.example.Game.GameTable;

public class MoveValidator {
    public static boolean isValid(Board board, int row, int col) {
        int size = board.getSize();

        if(row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        } // Verific sa fiu inauntrul tablei de joc

        return board.getCell(row, col) == Cell.EMPTY; // Verific daca celula este goala, sa nu acopar miscarea altcuiva
    }
}
