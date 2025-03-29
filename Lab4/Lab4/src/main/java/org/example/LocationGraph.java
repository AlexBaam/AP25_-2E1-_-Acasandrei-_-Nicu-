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

    public LocationGraph() {
        graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    }

    public void addLocation(Location location) {
        graph.addVertex(location);
    }

    public void addConnection(Location location1, Location location2, double weight) {
        if(location1.equals(location2)) {
            return;
        } else {
            DefaultWeightedEdge edge1 = graph.addEdge(location1, location2);
            DefaultWeightedEdge edge2 = graph.addEdge(location2, location1);
            if(edge1 != null) {
                graph.setEdgeWeight(location1, location2, weight);
            }
            if(edge2 != null) {
                graph.setEdgeWeight(location2, location1, weight);
            }
        }
    }

    public void addRandomConnection() {
        List<Location> allVertex = graph.vertexSet().stream().collect(Collectors.toList());

        while(true){
            double[] randomValues =new double[3];
            Location loc1 = allVertex.get(rand.nextInt(allVertex.size()));
            Location loc2 = allVertex.get(rand.nextInt(allVertex.size()));

            if(loc1.getType() == LocationType.FRIENDLY){
                randomValues = new double[]{1.0, 2.0, 3.0};
            } else if(loc1.getType() == LocationType.NEUTRAL){
                randomValues =  new double[]{4.0, 5.0, 6.0};
            } else if(loc1.getType() == LocationType.ENEMY){
                randomValues =  new double[]{7.0, 8.0, 9.0};
            }

            if(!loc1.equals(loc2)) {
                if(!graph.containsEdge(loc1, loc2)) {
                    addConnection(loc1, loc2, randomValues[rand.nextInt(randomValues.length)]);
                    break;
                }
            }
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

    private Map<String, EnumMap<LocationType, Integer>> computeSafestPaths(){
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

    public void computePaths(){
        computeSafestPaths();
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

    public void printGraph() {
        System.out.println("Graph: ");
        for(Location loc : graph.vertexSet()) {
            System.out.println("Location: " + loc.getCodeName());

            for(DefaultWeightedEdge edge : graph.outgoingEdgesOf(loc)){
                Location target = graph.getEdgeTarget(edge);
                double weight = graph.getEdgeWeight(edge);
                System.out.println(" --> " + target.getCodeName() + " --> " + weight);
            }
        }
    }

    public void averageFastestPath() {

        List<Double> pathsWeights = new ArrayList<>();
        DijkstraShortestPath<Location, DefaultWeightedEdge> disktra = new DijkstraShortestPath(graph);

        for(Location start : graph.vertexSet()) {
            for(Location end : graph.vertexSet()) {
                if(!start.equals(end)) {
                    if(disktra.getPath(start,end) != null) {
                        pathsWeights.add(getFastestRouteBetween(start, end));
                    }
                }
            }
        }

        int maxEdgeNumber = (graph.vertexSet().size() * (graph.vertexSet().size() - 1)) /2;
        int existingEdgesNumber = graph.edgeSet().size()/2;
        int nullEdges = maxEdgeNumber - graph.edgeSet().size()/2;
        double average = pathsWeights.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double maxTime = pathsWeights.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double minTime = pathsWeights.stream().mapToDouble(Double::doubleValue).min().orElse(0.0);
        System.out.println("Average fastest path time: " + average + "\nMax time: " + maxTime
                + "\nMin time: " + minTime + "\nMax edge number: " + maxEdgeNumber
                + "\nNumber of edges: " + existingEdgesNumber + "\nNumber of missing edges: " + nullEdges);
    }
}
