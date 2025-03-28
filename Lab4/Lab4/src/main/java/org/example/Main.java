package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        LocationGenerator gen = new LocationGenerator(6, 8);
        gen.locationGenerate();

        LocationGraph graph = new LocationGraph();

        gen.createGraph(graph);

        gen.generateConnection(graph);

        graph.printGraph();
    }

    public static void printFastestTimeForLocations(LocationGraph graph,Map<LocationType, List<Location>> groupedLocations, LocationType type) {
        List<Location> locations = groupedLocations.get(type);

        System.out.println("\nFastest Time for "+type+" locations: ");

        for(Location location : locations) {
            if(!locations.get(0).equals(location)) {
                double fastestTime = graph.getFastestRouteBetween(locations.get(0), location);
                System.out.println("\nFastest time from " + locations.get(0).getCodeName() + " to " + location.getCodeName() + " is " + fastestTime);
            }
        }
    }
}