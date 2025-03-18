package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Location loc1 = new Location("Alpha_ONE", LocationType.FRIENDLY);
        Location loc2 = new Location("Alpha_TWO", LocationType.NEUTRAL);
        Location loc3 = new Location("Alpha_THREE", LocationType.ENEMY);

        Location loc4 = new Location("Beta_ONE", LocationType.FRIENDLY);
        Location loc5 = new Location("Beta_TWO", LocationType.NEUTRAL);
        Location loc6 = new Location("Beta_THREE", LocationType.ENEMY);

        Location loc7 = new Location("Gamma_ONE", LocationType.FRIENDLY);
        Location loc8 = new Location("Gamma_TWO", LocationType.NEUTRAL);
        Location loc9 = new Location("Gamma_THREE", LocationType.ENEMY);

        ArrayList<Location> allLocations = new ArrayList<>();

        allLocations.add(loc1);
        allLocations.add(loc2);
        allLocations.add(loc3);
        allLocations.add(loc4);
        allLocations.add(loc5);
        allLocations.add(loc6);
        allLocations.add(loc7);
        allLocations.add(loc8);
        allLocations.add(loc9);

        TreeSet<Location> friendlyLocations =
                allLocations.stream().filter(loc -> loc.getType()
                        == LocationType.FRIENDLY).collect(Collectors.toCollection(TreeSet::new));

        System.out.println("Friendly locations: \n");
        friendlyLocations.forEach(System.out::println);

        LinkedList<Location> enemyLocation =
                allLocations.stream().filter(loc -> loc.getType()
                        == LocationType.ENEMY).collect(Collectors.toCollection(LinkedList::new));

        enemyLocation.sort(Comparator.comparing(Location::getType).thenComparing(Location::getName));

        System.out.println("Enemy locations: \n");
        enemyLocation.forEach(System.out::println);
    }
}