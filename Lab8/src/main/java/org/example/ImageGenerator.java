package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageGenerator {
    private final List<City> cities;
    private final Graph<City, DefaultEdge> graph;
    private final int width;
    private final int height;

    public ImageGenerator(List<City> cities, Graph<City, DefaultEdge> graph, int width, int height) {
        this.cities = cities;
        this.graph = graph;
        this.width = width;
        this.height = height;
    }

    public void drawToFile(String filename) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.LIGHT_GRAY);
        for (DefaultEdge edge : graph.edgeSet()) {
            City source = graph.getEdgeSource(edge);
            City target = graph.getEdgeTarget(edge);
            Point p1 = geoToPixel(source);
            Point p2 = geoToPixel(target);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        for (City city : cities) {
            Point p = geoToPixel(city);
            g.setColor(Color.RED);
            g.fillOval(p.x - 3, p.y - 3, 6, 6);
        }

        g.dispose();

        try {
            ImageIO.write(image, "png", new File(filename));
            System.out.println("Map saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Point geoToPixel(City city) {
        int x = (int) ((city.getLongitude() + 180) * (width / 360.0));
        int y = (int) ((90 - city.getLatitude()) * (height / 180.0));
        return new Point(x, y);
    }
}
