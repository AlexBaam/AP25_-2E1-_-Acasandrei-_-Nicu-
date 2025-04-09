package org.example;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GraphForGame {
    private List<Point> dots = new ArrayList<Point>();
    private List<Line> lines = new ArrayList<>();
    Graph<Point, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

    public GraphForGame(List<Point> dots, List<Line> lines) {
        this.dots = dots;
        this.lines = lines;

        for(Point p : dots){
            graph.addVertex(p);
        }
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;

        for(Line line : lines){
            if(!graph.containsEdge(line.start, line.end)){
                graph.addEdge(line.start, line.end);
                graph.setEdgeWeight(graph.getEdge(line.start, line.end), line.getDistance());
            }
        }
    }

    public boolean isConnected() {
        ConnectivityInspector<Point, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.isConnected();
    }
}
