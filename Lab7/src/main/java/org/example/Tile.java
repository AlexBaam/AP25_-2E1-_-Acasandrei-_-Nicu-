package org.example;

public class Tile {
    private final char letter; // final -> adica e constanta, nu ii poti modifica valoarea
    private final int points;


    public Tile(char letter, int points) {
        this.letter = letter;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
    public char getLetter() {
        return letter;
    }

    public String toString() {
        return letter + "" + points;
    }
}
