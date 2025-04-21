package org.example;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SpanningTreeGenerator {

    public static List<List<Line>> generateTrees(List<Point> dots, List<Line> lines) {
        List<List<Line>> spanningTrees = new ArrayList<List<Line>>();

        // Cream un graf
        Graph<Point, DefaultWeightedEdge> graph = new SimpleWeightedGraph<Point, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        for(Point p : dots) {
            graph.addVertex(p);
        }

        // Facem graful complet ca sa avem prin ce itera (Si sa cautam multiple posibilitati)
        for(int i = 0; i < dots.size(); i++) {
            for(int j = i + 1; j < dots.size(); j++) {
                Point p1 = dots.get(i);
                Point p2 = dots.get(j);
                DefaultWeightedEdge edge = graph.addEdge(p1, p2);
                if(edge != null) {
                    double weight = euclideanDistance(p1, p2);
                    graph.setEdgeWeight(edge, weight);
                }
            }
        }

        //Minimum Spanning Trees (MST) computed by Kruskal Algorithm
        KruskalMinimumSpanningTree<Point, DefaultWeightedEdge> kruskal =
                new KruskalMinimumSpanningTree<>(graph);
        Set<DefaultWeightedEdge> mstEdges = kruskal.getSpanningTree().getEdges();

        List<Line> mstLines = new ArrayList<>();
        for(DefaultWeightedEdge edge : mstEdges) {
            Point source = graph.getEdgeSource(edge);
            Point target = graph.getEdgeTarget(edge);
            mstLines.add(new Line(source, target, Color.BLUE));
        }
        spanningTrees.add(mstLines);

        // Cream mai multi spanning trees scotand cate o muchie din cel minim pe rand
        List<DefaultWeightedEdge> allEdges = new ArrayList<>(graph.edgeSet());
        for(DefaultWeightedEdge toRemoveEdge : mstEdges) {
            List<Line> alternativeTree = createNewMST(graph, dots, allEdges, toRemoveEdge);
            if(!alternativeTree.isEmpty()) {
                spanningTrees.add(alternativeTree);
            }
        }

        // Sortam toti MST dupa cost (crescator)
        spanningTrees.sort(Comparator.comparingDouble(SpanningTreeGenerator::totalCost));
        
        return spanningTrees;
    }

    private static List<Line> createNewMST(Graph<Point, DefaultWeightedEdge> originalGraph,
                                           List<Point> dots,
                                           List<DefaultWeightedEdge> allEdges,
                                           DefaultWeightedEdge toRemoveEdge) {
        Graph<Point, DefaultWeightedEdge> tempGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (Point p : dots) {
            tempGraph.addVertex(p);
        }

        for (DefaultWeightedEdge edge : allEdges) {
            if (edge.equals(toRemoveEdge)) continue;

            Point source = originalGraph.getEdgeSource(edge);
            Point target = originalGraph.getEdgeTarget(edge);
            DefaultWeightedEdge newEdge = tempGraph.addEdge(source, target);
            if (newEdge != null) {
                tempGraph.setEdgeWeight(newEdge, originalGraph.getEdgeWeight(edge));
            }
        }

        Set<DefaultWeightedEdge> newEdges =
                new KruskalMinimumSpanningTree<>(tempGraph).getSpanningTree().getEdges();

        if (newEdges.size() != dots.size() - 1) return Collections.emptyList();

        List<Line> newTree = new ArrayList<>();
        for (DefaultWeightedEdge edge : newEdges) {
            Point source = tempGraph.getEdgeSource(edge);
            Point target = tempGraph.getEdgeTarget(edge);
            newTree.add(new Line(source, target, Color.BLUE));
        }
        return newTree;
    }

    private static double totalCost(List<Line> lines) {
        return lines.stream().mapToDouble(Line::getDistance).sum();
    }
    private static double euclideanDistance(Point p1, Point p2) {
       return Math.sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y));
    }
}
