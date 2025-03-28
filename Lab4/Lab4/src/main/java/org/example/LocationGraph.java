package org.example;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import java.util.*;
import java.util.stream.Collectors;

public class LocationGraph {
    private Graph<Location, DefaultWeightedEdge> graph;
    Random rand = new Random();
    double[] randomValues = {1.0, 2.0, 3.0};

    public LocationGraph() {
        graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }

    public void addLocation(Location location) {
        graph.addVertex(location);
    }

    public void addConnection(Location location1, Location location2, double weight) {
        DefaultWeightedEdge edge = graph.addEdge(location1, location2);
        DefaultWeightedEdge edge2 = graph.addEdge(location2, location1);
        if(edge != null) {
            graph.setEdgeWeight(location1, location2, weight);
        }
        if(edge2 != null) {
            graph.setEdgeWeight(location2, location1, weight);
        }
    }

    public void addRandomConnection() {
        List<Location> allVertex = graph.vertexSet().stream().collect(Collectors.toList());
        Location loc1 = allVertex.get(rand.nextInt(allVertex.size()));
        Location loc2 = allVertex.get(rand.nextInt(allVertex.size()));

        if(!loc1.equals(loc2)) {
            if(!graph.containsEdge(loc1, loc2)) {
                addConnection(loc1, loc2, randomValues[rand.nextInt(randomValues.length)]);
            }
            else {
                addRandomConnection();
            }
        } else {
            addRandomConnection();
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
        System.out.println("Graph: ");
        for(Location loc : graph.vertexSet()) {
            System.out.println("Location: " + loc.getCodeName());

            for(DefaultWeightedEdge edge : graph.edgesOf(loc)){
                Location target = graph.getEdgeTarget(edge);
                double weight = graph.getEdgeWeight(edge);
                System.out.println(" --> " + target.getCodeName() + " --> " + weight);
            }
        }
    }

    private EnumMap<LocationType, Integer> countTypes(List<Location> path) {
        EnumMap<LocationType, Integer> map = new EnumMap<>(LocationType.class);
        for(LocationType type : LocationType.values()) {
            map.put(type, 0);
        }
        for(Location loc : path) {
            map.put(loc.getType(), map.get(loc.getType()) + 1);
        }
        return map;
    }

    public Map<String, EnumMap<LocationType, Integer>> computeSafestPaths(){
        Map<String, EnumMap<LocationType, Integer>> result = new HashMap<>();
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);

        for(Location start : graph.vertexSet()) {
            for(Location end : graph.vertexSet()) {
                if(!start.equals(end)) {
                    GraphPath<Location, DefaultWeightedEdge> path = dijkstra.getPath(start, end);
                    if(path != null) {
                        List<Location> pathList = path.getVertexList();
                        EnumMap<LocationType, Integer> counts = countTypes(pathList);

                        String key = start.getCodeName() + " -> " + end.getCodeName();
                        result.put(key, counts);
                    }
                }
            }
        }

        return result;
    }

    public void printSafestPaths() {
        Map<String, EnumMap<LocationType, Integer>> data = computeSafestPaths();

        for(Map.Entry<String, EnumMap<LocationType, Integer>> entry : data.entrySet()) {
            System.out.println("Path: " + entry.getKey());
            EnumMap<LocationType, Integer> counts = entry.getValue();
            for(LocationType type : LocationType.values()) {
                System.out.println(" " + type + " " + counts.get(type));
            }
            System.out.println();
        }
    }
}
