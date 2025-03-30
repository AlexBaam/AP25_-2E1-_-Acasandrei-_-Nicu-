package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPanel extends JPanel {
    private final MainFrame frame;
    private final int canvasWidth = 400;
    private final int canvasHeight = 400;
    private List<Point> dotsLocations = new ArrayList<>();

    public DrawingPanel(MainFrame frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        paintLines(g);
    }

    private void paintDots(Graphics2D g, int width, int height) {
        g.setColor(Color.BLACK);
        g.fillOval(width, height, 9, 9);
    }

    private void paintLines(Graphics2D g) {
    }

    public void fillWithDots(List<Point> dots) {
        Graphics graphics = getGraphics();
        Graphics2D g = (Graphics2D) graphics;
        this.dotsLocations = dots;

        for (Point dotLocation : dotsLocations) {
            paintDots(g, dotLocation.x, dotLocation.y);
        }
    }

    public void hideDots(List<Point> dots) {
        Graphics graphics = getGraphics();
        Graphics2D g = (Graphics2D) graphics;

        this.dotsLocations = dots;
        for (Point dotLocation : dotsLocations) {
            paintWhiteDots(g, dotLocation.x, dotLocation.y);
        }
    }

    private void paintWhiteDots(Graphics2D g, int width, int height) {
        g.setColor(Color.WHITE);
        g.fillOval(width-1, height-1, 11, 11);
    }
}
