package org.example;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class LocationGraph {
    private Graph<Location, DefaultWeightedEdge> graph;

    public LocationGraph() {
        graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }

    public void addLocation(Location location) {
        graph.addVertex(location);
    }

    public void addConnection(Location location1, Location location2, double weight) {
        DefaultWeightedEdge edge = graph.addEdge(location1, location2);
        if(edge != null) {
            graph.setEdgeWeight(location1, location2, weight);
        }
    }

    public void getFastestRoute(Location start) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath(graph);

        for(Location end : graph.vertexSet()) {
            if(!start.equals(end)) {
                double fastestTime = dijkstra.getPath(start, end).getWeight();
                List<Location> shortestPath = dijkstra.getPath(start, end).getVertexList();

                System.out.println("Fastest path from " + start.getCodeName() + " to " + end.getCodeName() + ":");
                System.out.println("Total weight: " + fastestTime);
                System.out.println("Path: ");
                shortestPath.forEach(location -> System.out.println(location.getCodeName()));
                System.out.println();
            }
        }
    }

    public double getFastestRouteBetween(Location start, Location end) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        return dijkstra.getPath(start, end).getWeight();
    }

    public void printGraph() {
        for(Location loc : graph.vertexSet()) {
            System.out.println("Location: " + loc.getName());

            for(DefaultWeightedEdge edge : graph.edgesOf(loc)){
                Location target = graph.getEdgeTarget(edge);
                double weight = graph.getEdgeWeight(edge);
                System.out.println(" --> " + target.getName() + " --> " + weight);
            }
        }
    }
}
