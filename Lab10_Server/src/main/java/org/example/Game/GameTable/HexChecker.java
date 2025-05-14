package org.example.Game.GameTable;

import java.util.*;

public class HexChecker {

    public static List<int[]> getAroundTheHex(int row, int collum, int boardSize) {
        List<int[]> neighbours = new ArrayList<>();

        /*
        Urmeaza sa declar toate marginile unui hexagon deoarece tabla este formata din 11x11 hexagoane
        Fiecare hexagon, are 6 vecini, asa ca verificam pe 6 directii
         */
        int[][] sides = {
                {-1, 0}, // Stanga sus
                {1, 0}, // Stanga jos
                {0, -1}, // Stanga
                {0, 1}, // Dreapta
                {-1, 1}, // Dreapta sus
                {1, -1} // Dreapta jos
        };

        for(int[] side : sides) {
            int newRow = row + side[0];
            int newCol = collum + side[1];

            if(inBounds(newRow, newCol, boardSize)){
                neighbours.add(new int[]{newRow, newCol});
            }
        }

        return neighbours;
    }

    private static boolean inBounds(int newRow, int newCol, int boardSize) {
        return newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize;
    }
}
