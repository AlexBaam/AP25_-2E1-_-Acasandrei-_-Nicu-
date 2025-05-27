package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;


public class Player implements Runnable {
    private String name;
    private Game game;
    private boolean running;
    private int index;
    private boolean finished;

    public List<Tile> currentTiles = new ArrayList<>(); // aici facem o lista cu literele curente ale jucatorului
    // (ca sa putem sa scoatem cate litere vrem si sa completam dupa pana la 7)
    /*
    running - utilizata pt a controla durata activitatii unui jucator
            - face posibila intreruperea thread-ului unui jucator atunci cand jocul s-a terminat sau si-a terminat jocul

    TimeKeeper (thread care cronometreaza jocul)
              - e un fir de executie care ruleaza in paralel cu jucatorii
              - il facem daemon ca sa nu blocheze aplicatia cand totul e gata
              - afiseaza timpul trecut la fiecare secunda
              - daca se depaseste timpul maxim, opreste jocul

    * */
    private int score;

    public Player(String name, Game game, int index) {
        this.name = name;
        this.game = game;
        this.running = true;
        this.score = 0;
        this.index = index;
        this.finished = false;
    }

    public boolean isFinished() {
        return finished;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    private String createWordFromTiles(List<Tile> extractedTiles) {
        StringBuilder word = new StringBuilder();
        boolean[] visited = new boolean[extractedTiles.size()];

//        System.out.println(name + " are literele: " + extractedTiles.stream()
//                .map(tile -> String.valueOf(tile.getLetter()))
//                .collect(Collectors.joining(", ")));

        //folosim un bkt pt a genera toate cuvintele posibile si il returnam pe primul cuvant valid pe care il gasim
        return bkt(word, visited, extractedTiles);
    }

    private String bkt(StringBuilder word, boolean[] visited, List<Tile> tiles){

        // verificam daca cuvantul este valid
        if(word.length() > 0) {
            String candidate = word.toString();
//            System.out.println("Cuvantul incercat: " + candidate); // Print pentru cuvintele incercate
            if (game.getDictionary().isWord(candidate)) {
//                System.out.println("TRIMIT CUVANTUL: " + candidate);
                return candidate;
            }
        }

        for(int i = 0; i<tiles.size(); i++) {
            if(!visited[i]) { //verificam daca aceasta litera a fost deja folosita
                visited[i] = true;
                word.append(tiles.get(i).getLetter()); // o adaugam la cuvantul nostru

                //continuam sa formam cuvantul recursiv
                String candidate = bkt(word, visited, tiles);
                if(candidate != null) {
                    return candidate;
                }


                word.deleteCharAt(word.length() - 1);
                visited[i] = false;
            }
        }
        return null; //nu s-a gasit niciun cuvant
    }

    // tragem piese pana cand avem 7 piese
    private void refillTiles() {
        int howMany = 7-currentTiles.size();
        List<Tile> newTiles = game.getBag().extractTiles(howMany);
        currentTiles.addAll(newTiles);
    }

    // Extrage piesele din sac
    private boolean submitWord() {

        refillTiles(); //daca avem mai putin de 7 litere in sac, completeaza

        if (currentTiles.isEmpty()) { //verificam daca exista suficiente litere
            return false;
        }

        String word = createWordFromTiles(currentTiles);
//        System.out.println("Cuvantul primit este: " + word);
        if(word == null) { //daca nu se pot forma cuvinte
            currentTiles.clear(); //curatam lista de litere
            return false;
        }

        List<Tile> usedTiles = new ArrayList<>();
        StringBuilder wordCopy = new StringBuilder(word); // copie pentru eliminare literelor folosite

        for (Tile tile : currentTiles) {
            if (wordCopy.length() > 0 && tile.getLetter() == wordCopy.charAt(0)) {
                usedTiles.add(tile);
                wordCopy.deleteCharAt(0);
            }
        }

        // actualizam scorul pt fiecare litera folosita
        for (Tile tile : usedTiles) {
            score += tile.getPoints();
        }

        currentTiles.removeAll(usedTiles); // scoatem literele folosite
        game.getBoard().addWord(this, word); //punem cuvantul pe Board

        return true;
    }

    public void stop() {
        running = false;
    } // opreste bucla while



    @Override
    // ca sa joace incontinuu pana cand variabila running devine fals sau sacul ramane gol
    public void run() {

        /*
        lock - obiectul comun pe care toti jucatorii se sincronizeaza (prin synchronized(lock))
        isMyTurn(index) - verifica daca jucatorul care a intrat in sectiunea sincronizata e cel care trebuie sa joace

        */

        while (running) { //jucatorii se joaca pana cand sacul e gol
            synchronized (game.getLock()) {
                while (!game.isMyTurn(index) && !game.getBag().isEmpty()) {
                    try {
                        game.getLock().wait(); // isi așteapta randul
                    } catch (InterruptedException e) {
                        System.out.println(name + " a fost intrerupt.");
                        return;
                    }
                }

                if (!game.isGameRunning()) {
                    break;
                }

                if (game.getBag().isEmpty()) break;

                boolean success = submitWord(); // daca reuseste sa creeze cuvantul
                if (!success) {
                    System.out.println(name + " nu s-a putut forma un cuvant, skip la tura.");
                }

                game.nextTurn(); // trece la urmatorul jucator in ordine ciclica
                game.getLock().notifyAll(); // notifica ceilalți jucatori

                // intarzie de 500ms
                try {
                    sleep(500);
                } catch (
                        InterruptedException e) { //daca thread-ul este intrerupt in timpul timpului de asteptare, arunca exceptia
                    e.printStackTrace();
                }
            }
        }

        // Daca sacul este gol, jocul se inchieie
        System.out.println(name + " has finished the game.");
        System.out.println(name + " are scorul final: " + score);
        finished = true;
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void stopPlaying() {
        finished = true;
    }
}
