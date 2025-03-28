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
