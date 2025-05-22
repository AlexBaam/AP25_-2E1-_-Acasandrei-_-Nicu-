package org.example.lab10_client_gui.Connect;
import org.example.lab10_client_gui.Board.Cell;

public class ClientSidePlayer {
    private final String name;
    private final Cell color;  // use your client-side enum Cell (RED, BLUE, EMPTY)

    public ClientSidePlayer(String name, Cell color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Cell getColor() {
        return color;
    }
}
