package org.example;
import java.util.*;

public class LocationGenerator {
    private List<Location> allLocations;
    private int locationCounter;
    private int pathCounter;

    private String[] baseNames = {"Alpha", "Beta", "Gamma", "Delta", "Sigma"};
    private CodeName codeNameGen = new CodeName(baseNames);

    public LocationGenerator(int locationCounter, int pathCounter) {
        allLocations = new ArrayList<>();
        this.locationCounter = locationCounter;
        this.pathCounter = pathCounter;
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
}
