package org.example;
import java.util.*;
import lombok.*;

@Setter
@Getter
public class Freighter extends Aircraft implements CargoCapable {
    private int wingSpan;
    private int cargoCapacity;

    public Freighter(String name, String model, int tailNumber, int wingSpan, int cargoCapacity) {
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
        setWingSpan(wingSpan);
        setCargoCapacity(cargoCapacity);
    }

    @Override
    public String toString() {
        return "Airline: " + getName() + "\n" +
                " Model: " + getModel() + "\n" +
                " Tail Number: " + getTailNumber() + "\n" +
                " Wing Span: " + getWingSpan() + "\n" +
                " Cargo Capacity: " + getCargoCapacity() + " kg" +"\n";
    }

    @Override
    public int getCargoCapacity(){
        return cargoCapacity;
    }
}
