// HexLayout.java
package org.example.lab10_client_gui.Board;

/**
 * Geometry helper for pointy-top hex coordinates and canvas centering.
 */
public class HexLayout {
    public static final int    BOARD_SIZE   = 11;
    public static final double R            = 28;                 // hex radius
    public static final double W            = Math.sqrt(3) * R;   // hex width
    public static final double H            = 2 * R;               // hex height
    public static final double VERT         = 3.0 / 2 * R;         // vertical step between rows

    // Vertex bounds
    private static double minCenterX() {
        return (BOARD_SIZE - 1) * W / 2;
    }
    private static double maxCenterX() {
        return (BOARD_SIZE - 1) * W;
    }

    private static double minVertexX() {
        return minCenterX() - R;
    }
    private static double maxVertexX() {
        return maxCenterX() + R;
    }

    private static double minVertexY() {
        return -R;
    }
    private static double maxVertexY() {
        return (BOARD_SIZE - 1) * VERT + R;
    }

    /**
     * Full board width in pixels (vertex to vertex).
     */
    public static double totalWidth() {
        return maxVertexX() - minVertexX();
    }

    /**
     * Full board height in pixels (vertex to vertex).
     */
    public static double totalHeight() {
        return maxVertexY() - minVertexY();
    }

    /**
     * X offset to center the board on a canvas of given width.
     */
    public static double xOffset(double canvasWidth) {
        // we want minVertexX + xOff = (canvasWidth - totalWidth)/2
        return (canvasWidth - totalWidth()) / 2 - minVertexX();
    }

    /**
     * Y offset to center the board on a canvas of given height.
     */
    public static double yOffset(double canvasHeight) {
        // minVertexY + yOff = (canvasHeight - totalHeight)/2
        return (canvasHeight - totalHeight()) / 2 - minVertexY();
    }

    /**
     * Pixel center X of hex at (row,col).
     */
    public static double hexX(int row, int col) {
        return col * W + row * W / 2;
    }

    /**
     * Pixel center Y of hex at row.
     */
    public static double hexY(int row) {
        return row * VERT;
    }
}