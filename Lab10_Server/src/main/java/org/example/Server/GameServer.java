package org.example.Server;

import org.example.Game.GameLogic.GameManager;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger debugOutput = Logger.getLogger(GameServer.class.getName());
    private ServerSocket server;
    private static final int PORT = 5000;
    private final GameManager gameManager;

    // constructor with port
    public GameServer(int port)
    {
        gameManager = new GameManager();

        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            debugOutput.info("Server started on port " + port);

            while (true) {
                Socket socket = server.accept();
                debugOutput.info("New client connected!");

                ClientThread client = new ClientThread(socket, gameManager);
                client.start();
            }
        } catch (IOException e) {
            debugOutput.severe("Server error: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        new GameServer(PORT);
    }
}
