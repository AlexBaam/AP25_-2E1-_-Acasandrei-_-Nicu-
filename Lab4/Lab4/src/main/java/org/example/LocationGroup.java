package org.example;
import java.util.*;
import java.util.stream.Collectors;

public class LocationGroup {

    public LocationGroup() {}

    public Map<LocationType, List<Location>> groupLocationByType(List<Location> locations){
        return locations.stream().collect(Collectors.groupingBy(Location::getType));
    }

    public void printGroupLocations(Map<LocationType, List<Location>> locations) {
        locations.forEach((locationType, locationList) -> {
            System.out.println(locationType + " Locations:");
            locationList.forEach(location -> System.out.println(location.getCodeName()));
            System.out.println();
        });
    }
}
