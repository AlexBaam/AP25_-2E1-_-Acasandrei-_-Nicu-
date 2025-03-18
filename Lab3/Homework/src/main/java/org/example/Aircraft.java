package org.example;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@ToString
public abstract class Aircraft implements Comparable<Aircraft> {
    private String name;
    private String model;
    private int tailNumber;

    protected Aircraft() {}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Aircraft aircraft)) return false;
        return Objects.equals(name, aircraft.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int compareTo(Aircraft other) {
        if(this.name == null && other.name == null){
            return 0;
        }
        if(this.name == null){
            return 1;
        }
        if(other.name == null){
            return -1;
        }

        return this.name.compareTo(other.name);
    }
}
