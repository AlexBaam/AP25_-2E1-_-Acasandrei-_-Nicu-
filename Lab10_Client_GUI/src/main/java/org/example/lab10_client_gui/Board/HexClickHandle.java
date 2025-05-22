package org.example.lab10_client_gui.Board;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import org.example.lab10_client_gui.Connect.ClientSidePlayer;

public class HexClickHandle {
    private final HexBoardModel model;
    private final BoardPane boardPane;
    private final ClientSidePlayer localPlayer;
    private Cell currentTurn;
    private MoveSubmitCallback moveSubmitCallback;

    public HexClickHandle(HexBoardModel model, BoardPane boardPane, ClientSidePlayer localPlayer) {
        this.model = model;
        this.boardPane = boardPane;
        this.localPlayer = localPlayer;

        // Register mouse click listener on board's canvas
        boardPane.getCanvas().setOnMouseClicked(this::onMouseClicked);
    }

    public void setCurrentTurn(Cell turn) {
        this.currentTurn = turn;
        System.out.println("Set turn to " + currentTurn);

    }

    public void setMoveSubmitCallback(MoveSubmitCallback callback) {
        this.moveSubmitCallback = callback;
    }

    private void onMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();

        int[] hexCoords = boardPane.pixelToHex(x, y); // you need to implement this method or similar
        if (hexCoords == null) return; // Click outside the board

        int row = hexCoords[0];
        int col = hexCoords[1];

        if (localPlayer.getColor() != currentTurn) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Not your turn");
            alert.setHeaderText(null);
            alert.setContentText("Please wait for your turn.");
            alert.showAndWait();
            return;
        }

        // Submit the move
        if (moveSubmitCallback != null) {
            moveSubmitCallback.onMoveSubmit(row, col);
        }
    }

    // Functional interface for move submission callback
    public interface MoveSubmitCallback {
        void onMoveSubmit(int row, int col);
    }
}
