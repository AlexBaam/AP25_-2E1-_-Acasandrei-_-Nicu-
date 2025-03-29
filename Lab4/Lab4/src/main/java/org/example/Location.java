package org.example;
import com.github.javafaker.Code;
import com.github.javafaker.Faker;
import java.util.Objects;
import java.util.Random;


public class Location implements Comparable<Location> {
    private String name;
    private String codeName;

    private LocationType[] types = LocationType.values();
    private LocationType type;

    private Random random = new Random();
    private Faker faker = new Faker();

    public Location(CodeName codeNameGen) {
        this.type = types[random.nextInt(types.length)];
        this.codeName = codeNameGen.generator();
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
    public String toString() {
        return "Location: \n" +
                "Name: " + name +
                "\ncodeName: " + codeName +
                "\nType: " + type;
    }

    @Override
    public int compareTo(Location other) {
        return codeName.compareTo(other.codeName);
    }
}

enum LocationType {
    FRIENDLY,
    NEUTRAL,
    ENEMY;
}
