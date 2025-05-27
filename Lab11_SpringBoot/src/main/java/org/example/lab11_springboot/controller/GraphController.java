package org.example.lab11_springboot.controller;

import org.example.lab11_springboot.client.GraphBuilder;
import org.example.lab11_springboot.client.GraphColoring;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graph")
public class GraphController {

    private final GraphBuilder graphBuilder;

    public GraphController(GraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
    }

    @GetMapping
    public Map<String, List<String>> getGraph() {
        Graph<String, DefaultEdge> graph = graphBuilder.buildGraph();
        Map<String, List<String>> result = new HashMap<>();

        for (String country : graph.vertexSet()) {
            List<String> neighbors = Graphs.neighborListOf(graph, country);
            result.put(country, neighbors);
        }

        return result;
    }
}
