package org.example;
import java.util.*;

public class GameAI {
    private final int difficulty; // 0 = easy, 1 = medium, 2 = hard
    private final DrawingPanel canvas;

    public GameAI(int difficulty, DrawingPanel canvas) {
        this.difficulty = difficulty;
        this.canvas = canvas;
    }

    public void playTurn() {
        if(canvas.isGameOver()) {
            return;
        }

        System.out.println("AI playing at difficulty level: " + difficulty);
        List<List<Line>> allTrees = SpanningTreeGenerator.generateTrees(canvas.getDotsLocations(), canvas.getLines());

        if (allTrees.isEmpty()) {
            return;
        }

        //Selecting tree based on difficulty
        int index = switch(difficulty){
            case 0 -> allTrees.size() - 1; // easy (cel mai prost spanning tree, ultimul)
            case 1 -> allTrees.size()/2; // medium (luam un spanning tree de pe la mijloc)
            case 2 -> 0; // hard (luam primul ST adica cel mai bun, fiind minimum spanning tree)
            default -> 0;
        };

        List<Line> selectedTree = allTrees.get(index);

        for(Line line : selectedTree) {
           if(!lineAlreadyExists(line)){
                //Desenam linia
                canvas.drawLineAI(line.getStart(), line.getEnd());
                break;
            }
        }
    }

    // Metoda ptr a verifica daca o linie deja este desenata
    private boolean lineAlreadyExists(Line line) {
        return canvas.getLines().contains(line) ||
                canvas.getLines().contains(new Line(line.getEnd(), line.getStart(), line.getColor()));
    }
}
