package org.example;
import lombok.Getter;

import java.util.*;

@Getter
public class Runaway {
    private final String name;
    private List<Flight> flights;

    public Runaway(String name) {
        this.name = name;
        this.flights = new ArrayList<>();
    }

    public boolean canAddFlight(Flight otherFlight) {
        for(Flight flight : this.flights) {
            if(!flight.checkScheduleConflict(otherFlight)) {
                return false;
            }
        }
        return true;
    }

    public void addFlight(Flight flight) {
            this.flights.add(flight);
    }

    @Override
    public String toString() {
        return "Runaway: " +
                "Name: " + name + '\n' +
                " Flights: " + flights + '\n';
    }
}
