
// BoardPane.java
package org.example.lab10_client_gui.Board;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

/**
 * JavaFX Pane that displays and manages the hex board.
 */
public class BoardPane extends StackPane {
    private final Canvas           canvas;
    private final HexBoardModel    model;
    private final HexBoardRenderer renderer;
    private String                  gameId = "";

    public BoardPane() {
        this.canvas   = new Canvas(800, 800);
        this.model    = new HexBoardModel(HexLayout.BOARD_SIZE);
        this.renderer = new HexBoardRenderer(model, canvas);

        getChildren().add(canvas);

        // Redraw whenever the canvas resizes
        canvas.widthProperty().addListener(e -> renderer.redraw());
        canvas.heightProperty().addListener(e -> renderer.redraw());

        // Initial paint
        renderer.redraw();
    }

    public void setGameId(String id) {
        this.gameId = id;
    }
    public String getGameId() {
        return gameId;
    }

    /**
     * Attempt to place a stone and trigger a redraw on success.
     */
    public boolean placeStone(int row, int col, Cell color) {
        boolean ok = model.placeStone(row, col, color);
        if (ok) renderer.redraw();
        return ok;
    }

    public Node getCanvas() {
        return canvas;
    }

    public int[] pixelToHex(double x, double y) {
        // 1) Translate pixel by subtracting board offset
        double xLocal = x - HexLayout.xOffset(canvas.getWidth());
        double yLocal = y - HexLayout.yOffset(canvas.getHeight());

        // 2) Convert pixel to fractional axial coordinates (q, r)
        double q = (Math.sqrt(3) / 3 * xLocal - 1.0 / 3 * yLocal) / HexLayout.R;
        double r = (2.0 / 3 * yLocal) / HexLayout.R;

        // 3) Convert axial (q,r) to cube coordinates (x, y, z)
        double cubeX = q;
        double cubeZ = r;
        double cubeY = -cubeX - cubeZ;

        // 4) Round cube coordinates to nearest hex
        int rx = (int) Math.round(cubeX);
        int ry = (int) Math.round(cubeY);
        int rz = (int) Math.round(cubeZ);

        double xDiff = Math.abs(rx - cubeX);
        double yDiff = Math.abs(ry - cubeY);
        double zDiff = Math.abs(rz - cubeZ);

        if (xDiff > yDiff && xDiff > zDiff) {
            rx = -ry - rz;
        } else if (yDiff > zDiff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }

        // 5) Convert cube coords back to axial (col = rx, row = rz)
        int col = rx;
        int row = rz;

        // 6) Check if coords are inside board bounds
        if (row < 0 || row >= HexLayout.BOARD_SIZE || col < 0 || col >= HexLayout.BOARD_SIZE) {
            return null;  // outside board
        }

        return new int[] { row, col };
    }

    public HexBoardModel getModel() {
        return model;
    }
}
