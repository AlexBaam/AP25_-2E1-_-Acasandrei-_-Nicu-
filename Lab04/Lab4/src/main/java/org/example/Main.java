package org.example;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        LocationGenerator gen = new LocationGenerator(100);
        gen.locationGenerate();

        LocationGraph graph = new LocationGraph();

        gen.createGraph(graph);

        gen.generateConnection(graph);

        //gen.getFastestPathFromNodeOne(graph);
        // Obtidem cel mai rapid drum dintr-un nod de start (Primul nod) in orice nod;

        LocationGroup group = new LocationGroup();

        //gen.printGroupLocations(group);
        //POSIBIL SA NU EXISTE LOCATII DE UN TIP;

        /*
        printFastestForLocations(graph, gen.locationsGrouping(), LocationType.FRIENDLY);
        printFastestForLocations(graph, gen.locationsGrouping(), LocationType.NEUTRAL);
        printFastestForLocations(graph, gen.locationsGrouping(), LocationType.ENEMY);
        */
        graph.computePaths();
        //graph.printSafestPaths();

        graph.averageFastestPath();
    }

    public static void printFastestForLocations(LocationGraph graph, Map<LocationType, List<Location>> groupedLocations, LocationType type) {
        List<Location> locations = groupedLocations.get(type);

        System.out.println("\nFastest Time for "+type+" locations: ");

        if(locations == null){
            System.out.println("\nNone");
        }
        else{
            for(Location location : locations) {
                if(!locations.get(0).equals(location)) {
                    double fastestTime = graph.getFastestRouteBetween(locations.get(0), location);
                    System.out.println("\nFastest time from " + locations.get(0).getCodeName() + " to " + location.getCodeName() + " is " + fastestTime);
                }
            }
        }
    }
}