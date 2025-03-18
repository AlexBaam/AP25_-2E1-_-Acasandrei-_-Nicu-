package org.example;
import lombok.*;

@Getter
@Setter
public class Airline extends Aircraft implements PassengerCapable {
    private int wingSpan;
    private int passengerCapacity;

    public Airline(String name, String model, int tailNumber , int wingSpan, int passengerCapacity) {
        setName(name);
        setModel(model);
        setTailNumber(tailNumber);
        setWingSpan(wingSpan);
        setPassengerCapacity(passengerCapacity);
    }

    @Override
    public String toString() {
        return "Airline: " + getName() + "\n" +
                " Model: " + getModel() + "\n" +
                " Tail Number: " + getTailNumber() + "\n" +
                " Wing Span: " + getWingSpan() + "\n" +
                " Passenger Capacity: " + getPassengerCapacity() + " persons" + "\n";
    }

    @Override
    public int getPassengerCapacity(){
        return passengerCapacity;
    }
}
