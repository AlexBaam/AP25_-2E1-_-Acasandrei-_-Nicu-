package org.example.lab11_springboot.client;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphColoring {
    private final GraphBuilder graphBuilder;

    public GraphColoring(GraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
    }

    public Map<String, String> colorGraph() {
        Graph<String, DefaultEdge> graph = graphBuilder.buildGraph();
        Map<String, String> colorMap = new HashMap<>();

        List<String> colors = List.of("red", "green", "blue", "yellow");

        for(String country : graph.vertexSet()) {
            Set<String> neighbors = graph.edgesOf(country).stream()
                    .map(edge -> {
                        String src = graph.getEdgeSource(edge);
                        String tgt = graph.getEdgeTarget(edge);
                        return src.equals(country) ? tgt : src;
                    })
                    .collect(Collectors.toSet());

            Set<String> usedColors = neighbors.stream()
                    .map(colorMap::get).filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            for(String color : colors) {
                if(!usedColors.contains(color)) {
                    colorMap.put(country, color);
                    break;
                }
            }
        }

        return colorMap;
    }
}
