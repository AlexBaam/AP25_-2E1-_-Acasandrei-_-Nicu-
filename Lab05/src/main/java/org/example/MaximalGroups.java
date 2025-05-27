package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class MaximalGroups {

    private Repository repo;
    private List<Image> images;

    public MaximalGroups(Repository repo) {
        this.repo = repo;
        this.images = repo.getImages();
    }

    public Graph<Image, DefaultEdge> buildGraph() {
        Graph<Image, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (Image image : images) {
            graph.addVertex(image);
        }

        for (int i = 0; i < images.size(); i++) {
            for (int j = i + 1; j < images.size(); j++) {
                Image image1 = images.get(i);
                Image image2 = images.get(j);
                if (hasCommonTag(image1, image2)) {
                    graph.addEdge(image1, image2);
                }
            }
        }
        return graph;
    }

    public boolean hasCommonTag(Image image1, Image image2) {
        for (String tag1 : image1.tags()) {
            for (String tag2 : image2.tags()) {
                if (tag1.equals(tag2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int findMaxCliqueSize(Graph<Image, DefaultEdge> graph) {
        List<Set<Image>> cliques = findCliquesInGraph(graph);
        int maxSize = 0;
        for (Set<Image> clique : cliques) {
            maxSize = Math.max(maxSize, clique.size());
        }
        return maxSize;
    }

    public List<Set<Image>> findCliquesInGraph(Graph<Image, DefaultEdge> graph) {
        List<Set<Image>> cliques = new ArrayList<>();
        Set<Image> visited = new HashSet<>();

        for (Image node : graph.vertexSet()) {
            if (!visited.contains(node)) {
                Set<Image> clique = new HashSet<>();
                dfs(graph, node, clique, visited);
                cliques.add(clique);
            }
        }
        return cliques;
    }

    public void dfs(Graph<Image, DefaultEdge> graph, Image node, Set<Image> clique, Set<Image> visited) {
        clique.add(node);
        visited.add(node);
        for (DefaultEdge edge : graph.edgesOf(node)) {
            Image neighbor = graph.getEdgeTarget(edge);
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, clique, visited);
            }
        }
    }

    public void findAndDisplayCliquesOfSize(Graph<Image, DefaultEdge> graph, int size) {
        List<Set<Image>> cliques = findCliquesInGraph(graph);
        for (Set<Image> clique : cliques) {
            if (clique.size() == size) {
                System.out.println("Found clique:");
                for (Image image : clique) {
                    System.out.println("Image: " + image.filename());
                    System.out.println("Tags: " + image.tags());
                }
                System.out.println("----");
            }
        }
    }
}