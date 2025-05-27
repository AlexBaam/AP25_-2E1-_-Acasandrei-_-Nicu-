package org.example;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running;
    /*
    running - utilizata pt a controla durata activitatii unui jucator
            - face posibila intreruperea thread-ului unui jucator atunci cand jocul s-a terminat sau si-a terminat jocul

    * */
    private int score;

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        this.running = true;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    private String createWordFromTiles(List<Tile> extractedTiles) {
        String word = "";
        for (Tile tile : extractedTiles) {
            word = word + tile.getLetter();
        }
        return word;
    }

    // Extrage piesele din sac
    private boolean submitWord() {
        List<Tile> extracted = game.getBag().extractTiles(7); // extragem 7 litere din sac
        if (extracted.isEmpty()) { //verificam daca exista suficiente litere
            return false;
        }

        String word = createWordFromTiles(extracted);

        game.getBoard().addWord(this, word); //punem cuvantul pe Board

        return true;
    }

    @Override
    // ca sa joace incontinuu pana cand variabila running devine fals sau sacul ramane gol
    public void run() {

        while (running && !game.getBag().isEmpty()) { //jucatorii se joaca pana cand sacul e gol
            boolean success = submitWord(); // daca reuseste sa creeze cuvantul
            if (!success) {
                System.out.println(name + " nu s-a putut forma un cuvant, skip la tura.");
            }

            // intarzie de 500ms
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) { //daca thread-ul este intrerupt in timpul timpului de asteptare, arunca exceptia
                e.printStackTrace();
            }
        }

        // Daca sacul este gol, jocul se inchieie
        System.out.println(name + " has finished the game.");
    }


}
