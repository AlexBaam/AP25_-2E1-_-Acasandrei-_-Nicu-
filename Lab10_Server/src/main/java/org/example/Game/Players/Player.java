package org.example.Game.Players;

import org.example.Game.GameTable.Cell;

import java.net.Socket;

public class Player {
    private final String name;
    private final Socket socket;
    private final Cell color;

    public Player(String name, Socket socket, Cell color) {
        this.name = name;
        this.socket = socket;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public Cell getColor() {
        return color;
    }
}
