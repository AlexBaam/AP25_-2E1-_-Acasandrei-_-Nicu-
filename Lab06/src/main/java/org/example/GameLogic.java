/**
 * Cum fct jocul?
 * Fiecare player pune cate o linie pe rand
 * Liniile au o lungime ce va fi calculata
 * Scorul este format din suma tuturor lungimilor liniilor unui player
 * Adica iei fiecare lungime de linie si le aduni
 * Ca sa castigi trebuie sa ai scor cat mai mic
 *
 * Cum se incheie jocul?
 * Scopul unui player este sa construiasca o structura astfel incat toate punctele sa fie conectate intre ele
 * (Sa existe o singura componenta conecta)
 * In timp ce mentine si un scor minim
 *
 * Restrictii:
 * Nu poti duce o linie de la un nod la el insusi
 * Nu poti duce o linie peste una existenta
 *
 * Lungimea liniei este distanta euclidiana dintre doua puncte
 * Ce este distanta euclidiana?
 * A(xA, yA), B(xB, yB)
 * Distanta de la A la B este distanta euclidiana
 * Formula este: sqrt((xB - xA)^2 + (yB - yA)^2) (Formula luata de pe profesorjitaruionel.com ptr pregatirea la matematica)
 */

package org.example;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.sqrt;


public class GameLogic implements Serializable {
    private double redScore; // Scor ptr primul player
    private double blueScore; // Scor ptr al doilea player

    private List<Line> redLines = new ArrayList<>();
    private List<Line> blueLines = new ArrayList<>();
    private List<String> history = new ArrayList<>();

    public GameLogic() {
        redScore = 0;
        blueScore = 0;
    } // Default constructor

    // Ptr fiecare player adaugam linia in lista de linii de culoarea lor si calculam scorul;
    public void addLine(Point start, Point end, int currentPlayer) {
        double lineLength = computeDistance(start, end);
        Line line = new Line(start, end, currentPlayer == 0 ? Color.RED : Color.BLUE);

        String move = (currentPlayer == 0 ? "R" : "B") + ": +" + String.format("%.2f", lineLength);
        history.add(move);

        if(currentPlayer == 0){
            redScore += lineLength;
            redLines.add(line);
        } else {
            blueScore += lineLength;
            blueLines.add(line);
        }
    }

    private double computeDistance(Point start, Point end) {
        return Math.sqrt((end.x - start.x)*(end.x - start.x) + (end.y - start.y)*(end.y - start.y)); // Distanta euclidiana
    }

    public double getRedScore() {
        return redScore;
    }

    public double getBlueScore() {
        return blueScore;
    }

    public List<String> getHistory() {
        return history;
    }

    public void reset() {
        redScore = 0;
        blueScore = 0;
        redLines.clear();
        blueLines.clear();
        history.clear();
    }

    public List<Line> getRedLines() {
        return redLines;
    }

    public List<Line> getBlueLines() {
        return blueLines;
    }
}

