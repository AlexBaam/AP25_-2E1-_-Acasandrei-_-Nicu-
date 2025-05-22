package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexBoardPanel extends JPanel {
    private final int size;
    private final String[][] cellColors;
    private final int hexSize = 30;

    public HexBoardPanel(int size) {
        this.size = size;
        this.cellColors = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellColors[i][j] = "empty";
            }
        }

        int boardPixelSize = (int) ((size + size) * getHexHeight() * 0.75);
        setPreferredSize(new Dimension(boardPixelSize, boardPixelSize));
    }

    public void updateCell(int row, int col, String colorName) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            cellColors[row][col] = colorName.toLowerCase();
            repaint();
        }
    }

    private double getHexHeight() {
        return 2 * hexSize;
    }

    private double getHexWidth() {
        return Math.sqrt(3) * hexSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double hexHeight = getHexHeight(); // 2 * size
        double hexWidth = getHexWidth();   // sqrt(3) * size

        double verticalSpacing = hexHeight; // now correct for pointy-topped
        double horizontalSpacing = hexWidth;       // same for all columns

        double xOffset = 50;
        double yOffset = 50;

        double horizontalShift = hexWidth * 0.5;

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                double x = xOffset + col * horizontalSpacing + row * horizontalShift;
                double y = yOffset + row * verticalSpacing * 0.75;

                drawHex(g2, x, y, hexSize, cellColors[row][col]);
            }
        }
    }

    private void drawHex(Graphics2D g2, double x, double y, int size, String colorName) {
        Path2D hex = new Path2D.Double();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 30); // Pointy-topped
            double xi = x + size * Math.cos(angle);
            double yi = y + size * Math.sin(angle);
            if (i == 0) hex.moveTo(xi, yi);
            else hex.lineTo(xi, yi);
        }
        hex.closePath();

        Color color = switch (colorName) {
            case "red" -> Color.RED;
            case "blue" -> Color.BLUE;
            default -> Color.LIGHT_GRAY;
        };

        g2.setColor(color);
        g2.fill(hex);
        g2.setColor(Color.DARK_GRAY);
        g2.draw(hex);
    }
}