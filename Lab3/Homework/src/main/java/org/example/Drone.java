package org.example;
import java.util.*;
import lombok.*;

@Setter
@Getter
public class Drone extends Aircraft implements CargoCapable {
    private int batteryCapacity;
    private int cargoCapacity;

    public Drone(String name, String model, int tailNumber, int batteryCapacity, int  cargoCapacity) {
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
        setBatteryCapacity(batteryCapacity);
        setCargoCapacity(cargoCapacity);
    }

    @Override
    public String toString() {
        return "Airline: " + getName() + "\n" +
                " Model: " + getModel() + "\n" +
                " Tail Number: " + getTailNumber() + "\n" +
                " Battery: " + getBatteryCapacity() + "\n" +
                " Cargo Capacity: " + getCargoCapacity() + " kg" +"\n";
    }

    @Override
    public int getCargoCapacity(){
        return cargoCapacity;
    }
}
