package org.example.Server;

import org.example.Game.GameLogic.GameManager;
import org.example.Game.GameLogic.PlayGame;
import org.example.Game.GameTable.Cell;
import org.example.Game.Player;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final GameManager gameManager;
    private Player player;
    private String gameId;

    private static final Logger debugOutput = Logger.getLogger(ClientThread.class.getName());

    public ClientThread(Socket socket, GameManager gameManager) {
        this.socket = socket;
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true); // True ptr autoflush

            debugOutput.info("New client connected on thread number: " + getThreadId(socket));
            out.println("Welcome to Hex Game!");
            out.println("Use: create <name>, join <name> <id>, move <row> <collum>, exit");

            String line = "";
            while ((line = in.readLine()) != null) {
                line = line.trim().toLowerCase();
                debugOutput.info("Input received: " + line);

                if (line.equals("exit")) {
                    out.println("Goodbye!");
                    out.println();
                    break;
                }

                if (line.startsWith("create")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length < 2) {
                        out.println("Usage: create <name>");
                        out.println();
                        continue;
                    }

                    String name = tokens[1];
                    player = new Player(name, socket, Cell.RED);
                    gameId = gameManager.createGame(player);
                    out.println("Game created. ID: " + gameId);
                    out.println();
                } else if (line.startsWith("join")) {
                    String[] tokens = line.split(" ");
                    if (tokens.length < 3) {
                        out.println("Usage: join <name> <id>");
                        out.println();
                        continue;
                    }

                    String name = tokens[1];
                    String id = tokens[2];

                    Player blue = new Player(name, socket, Cell.BLUE);
                    boolean joined = gameManager.joinGame(id, blue);

                    if (joined) {
                        this.player = blue;
                        this.gameId = id;
                        out.println("Game joined as blue. Game id: " + gameId);
                        out.println();
                    } else {
                        out.println("Could not join game. Invalid ID or already full");
                        out.println();
                    }
                } else if (line.startsWith("move")) {
                    String[] tokens = line.split(" ");
                    if ((tokens.length < 3) || (gameId == null) || (player == null)) {
                        out.println("You must join/create a game first. Usage: move <row> <collum>");
                        out.println();
                        continue;
                    }

                    try {
                        int row = Integer.parseInt(tokens[1]);
                        int collum = Integer.parseInt(tokens[2]);

                        boolean success = gameManager.submitMove(gameId, player, row, collum);
                        if (success) {
                            PlayGame game = gameManager.getGame(gameId);
                            if (game.hasEnded()) {
                                out.println("Game over! Winner: " + game.getWinner().getName());
                            } else {
                                out.println("Move accepted. It's now " + game.getCurrentTurn() + " turn.");
                                out.println();
                            }
                        } else {
                            out.println("Invalid move or not your turn");
                            out.println();
                        }
                    } catch (NumberFormatException e) {
                        out.println("Invalid coordinates.");
                        out.println();
                    }
                }    else {
                    out.println("Unknown command: " + line);
                    out.println();
                }
            }

            socket.close();
        } catch (IOException e) {
           debugOutput.severe("ClientThread error: " + e.getMessage());
        }
    }

    private long getThreadId(Socket socket){
        return Thread.currentThread().getId();
    }
}
