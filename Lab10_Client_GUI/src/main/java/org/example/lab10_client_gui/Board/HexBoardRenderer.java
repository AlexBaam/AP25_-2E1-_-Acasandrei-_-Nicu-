package org.example.lab10_client_gui.Board;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Renders a HexBoardModel onto a Canvas using pointy-top hexes.
 */
public class HexBoardRenderer {
    private final HexBoardModel model;
    private final Canvas        canvas;

    public HexBoardRenderer(HexBoardModel model, Canvas canvas) {
        this.model  = model;
        this.canvas = canvas;
    }

    /**
     * Clear and redraw the entire board centered in the canvas.
     */
    public void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double cw   = canvas.getWidth();
        double ch   = canvas.getHeight();
        double xOff = HexLayout.xOffset(cw);
        double yOff = HexLayout.yOffset(ch);

        gc.clearRect(0, 0, cw, ch);

        int size = model.getSize();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                double cx = xOff + HexLayout.hexX(row, col);
                double cy = yOff + HexLayout.hexY(row);
                Point2D[] pts = vertexes(cx, cy);

                // 1) Fill cell interior
                Cell cell = model.getCell(row, col);
                gc.setFill(cell == Cell.RED   ? Color.RED
                        : cell == Cell.BLUE  ? Color.BLUE
                        : Color.WHITE);
                double[] xs = new double[6], ys = new double[6];
                for (int i = 0; i < 6; i++) {
                    xs[i] = pts[i].getX();
                    ys[i] = pts[i].getY();
                }
                gc.fillPolygon(xs, ys, 6);

                // 2) Outline in black
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.strokePolygon(xs, ys, 6);

                // 3) Thicker perimeter edges
                // 3) Thicker perimeter edges
                gc.setLineWidth(3);

                // Top row → red (outer top edge is between pts[4]→pts[3])
                if (row == 0) {
                    gc.setStroke(Color.RED);
                    strokeEdge(gc, pts[4], pts[3]);
                }

                // Bottom row → red (outer bottom edge is between pts[1]→pts[0])
                if (row == size - 1) {
                    gc.setStroke(Color.RED);
                    strokeEdge(gc, pts[1], pts[0]);
                }

                // Left column → blue (outer left edge is between pts[2]→pts[1])
                if (col == 0) {
                    gc.setStroke(Color.BLUE);
                    strokeEdge(gc, pts[2], pts[1]);
                }

                // Right column → blue (outer right edge is between pts[5]→pts[4])
                if (col == size - 1) {
                    gc.setStroke(Color.BLUE);
                    strokeEdge(gc, pts[5], pts[4]);
                }

                // restore thin for next hex
                gc.setLineWidth(1);
            }
        }
    }

    private void strokeEdge(GraphicsContext gc, Point2D a, Point2D b) {
        gc.strokeLine(a.getX(), a.getY(), b.getX(), b.getY());
    }

    /**
     * Compute the 6 vertices of a pointy-top hex centered at (cx, cy).
     */
    private Point2D[] vertexes(double cx, double cy) {
        Point2D[] pts = new Point2D[6];
        double r = HexLayout.R;
        for (int i = 0; i < 6; i++) {
            double ang = Math.PI / 6 + Math.PI / 3 * i;
            pts[i] = new Point2D(
                    cx + r * Math.cos(ang),
                    cy + r * Math.sin(ang)
            );
        }
        return pts;
    }
}