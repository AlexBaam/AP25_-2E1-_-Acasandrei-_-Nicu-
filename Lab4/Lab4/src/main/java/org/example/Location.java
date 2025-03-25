package org.example;
import com.github.javafaker.Faker;
import java.util.Objects;


public class Location implements Comparable<Location> {
    private String name;
    private String codeName;
    private LocationType type;

    public Location(String codeName, LocationType type) {
        this.type = type;
        this.codeName = codeName;

        Faker faker = new Faker();
        this.name = faker.name().firstName();
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    public String getCodeName() {
        return codeName;
    }

    @Override
    public int compareTo(Location otherLocation) {
        return name.compareTo(otherLocation.name);
    }

    @Override
    public String toString() {
        return "Location: \n" +
                "Name: " + name +
                "\ncodeName: " + codeName +
                "\nType: " + type;
    }
}

enum LocationType {
    FRIENDLY,
    NEUTRAL,
    ENEMY;
}
