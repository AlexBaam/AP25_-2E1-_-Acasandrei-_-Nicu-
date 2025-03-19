package org.example;

import java.util.Objects;

public class Location implements Comparable<Location> {
    private String name;
    private LocationType type;

    public Location(String name, LocationType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Location: \n" +
                "Name: " + name +
                "\nType: " + type;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location location)) return false;
        return Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(Location otherLocation) {
        return name.compareTo(otherLocation.name);
    }


}

enum LocationType {
    FRIENDLY,
    NEUTRAL,
    ENEMY;
}
