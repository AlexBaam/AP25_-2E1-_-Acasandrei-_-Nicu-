package org.example;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Object lock = new Object(); // este obiectul comun pe care toti jucatorii se sincronizeaza (prin synchronized(lock))
    private int currentPlayerIndex = 0; //tine evidența al cui este randul

    private final Bag bag = new Bag();
    private final Board board = new Board();
    private final Dictionary dictionary;
    private boolean gameRunning = true; // flag pentru a verifica daca jocul este inca in desfasurare
    private final List<Player> players = new ArrayList<>();

    public Game(String dictionaryFile) {
        this.dictionary = new Dictionary(dictionaryFile);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void play() {
        TimeKeeper timeKeeper = new TimeKeeper(this, 30); // 30 secunde
        timeKeeper.start(); // porneste cronometrul

        for (Player player : players) {
            //start a new Thread representing the player;
            Thread playerThread = new Thread(player);
            playerThread.start();
        }
    }
    public static void main(String args[]) {
        //new Dictionary("words.txt");
        Game game = new Game("words.txt");
        Player player1 = new Player("Player 1", game,0);
        Player player2 = new Player("Player 2", game, 1);
        Player player3 = new Player("Player 3", game, 2);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.play();
    }

    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    // Metoda care ofera cheia pentru sincronizare
    public Object getLock() {
        return lock;
    }

    // Verifica daca este tura jucatorului
    public boolean isMyTurn(int index) {
        return currentPlayerIndex == index;
    }

    // Trecem la urmatorul jucator
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void stopGame() {
        if (gameRunning) {
            gameRunning = false;
            // Intrerupem thread-urile fiecarui jucator
            for (Player player : players) {
                player.stopPlaying(); // Setează flag-ul pentru a opri thread-ul
                Thread.currentThread().interrupt();  // intrerupe thread-ul curent al jucătorului
            }
            System.out.println("Jocul s-a oprit!");
        }
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players); // Ce facem aici? Suntem mai profi
        // facem o copie la lista pe care o avem si o trimitem mai departe pt a evita modificarea ei
        //return players;
    }
}
