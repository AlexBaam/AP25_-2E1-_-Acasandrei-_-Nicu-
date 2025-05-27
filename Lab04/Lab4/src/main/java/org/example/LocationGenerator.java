package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class LocationGenerator {
    private List<Location> allLocations;
    private int locationCounter;
    private int pathCounter;

    private String[] baseNames = {"Alpha", "Beta", "Gamma", "Delta", "Sigma"};
    private CodeName codeNameGen = new CodeName(baseNames);
    private Random rand = new Random();

    public LocationGenerator(int locationCounter) {
        allLocations = new ArrayList<>();
        this.locationCounter = locationCounter;

        int maxEdgeNr = (locationCounter*(locationCounter-1))/2;
        this.pathCounter = rand.nextInt((int)maxEdgeNr/4,maxEdgeNr);
    }

    public void locationGenerate() {
        for(int i = 0; i < locationCounter; i++) {
            Location loc = new Location(codeNameGen);

            if(loc != null){
                allLocations.add(loc);
            }
        }
    }

    public void printAllLocations() {
        if(allLocations != null){
            allLocations.forEach(loc -> System.out.println(loc.toString()));
        }
        else{
            System.out.println("No locations found");
        }
    }

    public void createGraph(LocationGraph graph) {
        for(Location loc : allLocations) {
            graph.addLocation(loc);
        }
    }

    public void generateConnection(LocationGraph graph) {
        for(int i = 0; i < pathCounter; i++) {
           graph.addRandomConnection();
        }
    }

    public void getFastestPathFromNodeOne(LocationGraph graph) {
        Location start = allLocations.get(0);
        graph.getFastestRoute(start);
    }

    public void printGroupLocations(LocationGroup locationGroup) {
        locationGroup.printGroupLocations(locationGroup.groupLocationByType(allLocations));
    }

    public Map<LocationType, List<Location>> locationsGrouping(){
        return allLocations.stream().collect(Collectors.groupingBy(Location::getType));
    }
}
