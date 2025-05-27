package org.example;

public class TimeKeeper extends Thread {
    private final Game game;
    private final int timeLimitSeconds; // limita de timp in secunde

    public TimeKeeper(Game game, int timeLimitSeconds) {
        this.game = game;
        this.timeLimitSeconds = timeLimitSeconds;
        setDaemon(true); // setam thread-ul ca daemon, adica nu va impiedica inchiderea aplicatiei daca celelalte thread-uri se termina
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis(); // salvam timpul de inceput

        while (true) {
            try {
                Thread.sleep(1000); // asteapta 1 secunda
            } catch (InterruptedException e) {
                e.printStackTrace(); // daca thread-ul este intrerupt, afisam eroarea
            }

            long currentTime = System.currentTimeMillis(); // momentul curent al timpului
            long elapsed = (currentTime - startTime) / 1000; // calculam timpul trecut in secunde
            System.out.println("[TimeKeeper] Time elapsed: " + elapsed + " seconds"); // afisam timpul scurs

            if (elapsed >= timeLimitSeconds) { // daca timpul trecut depaseste limita
                System.out.println("[TimeKeeper] Time limit exceeded. Stopping the game!"); // afisam ca timpul a expirat
                announceWinner(); //afisam castigatorul
                game.stopGame(); // apelam metoda stopGame() pentru a opri jocul
                break;
            }

            if(allPlayersFinished()) //sau au terminat toti jucatorii
            {
                System.out.println("[TimeKeeper] All players finished!");
                announceWinner(); //afisam castigatorul
                game.stopGame(); // apelam metoda stopGame() pentru a opri jocul
                break;
            }
        }
    }

    private boolean allPlayersFinished() {
        for (Player player : game.getPlayers()) {
            if (player.isFinished() == false) { // daca mai exista un jucator care joaca, nu a terminat
                return false;
            }
        }
        return true; // toti jucatorii au terminat
    }

    private void announceWinner() {
        Player winner = game.getPlayers().get(0);
        for (Player player : game.getPlayers()) {
            if (player.getScore() > winner.getScore()) {
                winner = player;
            }
        }
        System.out.println("[TimeKeeper] CASTIGATORUL ESTE: " + winner.getName() + " cu scorul " + winner.getScore());
    }
}
