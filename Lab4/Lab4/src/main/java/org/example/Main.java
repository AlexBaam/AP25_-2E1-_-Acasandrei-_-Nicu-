package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        LocationGraph graph = new LocationGraph();

        Location loc1 = new Location("Alpha_ONE", LocationType.FRIENDLY);
        Location loc2 = new Location("Alpha_TWO", LocationType.NEUTRAL);
        Location loc3 = new Location("Alpha_THREE", LocationType.ENEMY);

        Location loc4 = new Location("Beta_ONE", LocationType.FRIENDLY);
        Location loc5 = new Location("Beta_TWO", LocationType.NEUTRAL);
        Location loc6 = new Location("Beta_THREE", LocationType.ENEMY);

        // Adding the nodes
        graph.addLocation(loc1);
        graph.addLocation(loc2);
        graph.addLocation(loc3);
        graph.addLocation(loc4);
        graph.addLocation(loc5);
        graph.addLocation(loc6);

        // Adding connections
        graph.addConnection(loc1,loc2, 3);
        graph.addConnection(loc2,loc3, 2);
        graph.addConnection(loc3,loc4, 3);
        graph.addConnection(loc4,loc5, 2);
        graph.addConnection(loc5,loc6, 1);
        graph.addConnection(loc5,loc1, 2);
        graph.addConnection(loc6,loc4, 2);

        graph.getFastestRoute(loc1);

        ArrayList<Location> allLocations = new ArrayList<>();
        allLocations.add(loc1);
        allLocations.add(loc2);
        allLocations.add(loc3);
        allLocations.add(loc4);
        allLocations.add(loc5);
        allLocations.add(loc6);

        LocationGroup group = new LocationGroup();
        Map<LocationType, List<Location>> groupedLocations = group.groupLocationByType(allLocations);
        group.printGroupLocations(groupedLocations);

        printFastestTimeForLocations(graph, groupedLocations, LocationType.FRIENDLY);
        printFastestTimeForLocations(graph, groupedLocations, LocationType.NEUTRAL);
        printFastestTimeForLocations(graph, groupedLocations, LocationType.ENEMY);
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