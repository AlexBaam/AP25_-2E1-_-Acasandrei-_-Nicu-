package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.sql.Connection;
import java.util.*;

public class SisterCityGraph {
    private final Graph<City, DefaultEdge> graph;
    private final Map<Integer, City> idToCity;

    public SisterCityGraph(List<City> cities, Connection con) {
        this.graph = new SimpleGraph<>(DefaultEdge.class);
        this.idToCity = new HashMap<>();

        for (City city : cities) {
            graph.addVertex(city);
            idToCity.put(city.getId(), city);
        }

        // Load sister connections from DB and add edges
        SisterCitiesDAO sisterDAO = new SisterCitiesDAO();
        Map<Integer, Set<Integer>> sisterMap = sisterDAO.getAllRelations(con);

        for (Map.Entry<Integer, Set<Integer>> entry : sisterMap.entrySet()) {
            City source = idToCity.get(entry.getKey());
            for (Integer neighborId : entry.getValue()) {
                City target = idToCity.get(neighborId);
                if (source != null && target != null && !graph.containsEdge(source, target)) {
                    graph.addEdge(source, target);
                }
            }
        }
    }

    public Graph<City, DefaultEdge> getGraph() {
        return graph;
    }

    public Map<Integer, City> getIdToCityMap() {
        return idToCity;
    }

    public Set<City> getAllCities() {
        return graph.vertexSet();
    }

    public Set<DefaultEdge> getAllEdges() {
        return graph.edgeSet();
    }
}
