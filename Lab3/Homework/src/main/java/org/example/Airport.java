package org.example;
import java.util.*;
import lombok.*;

@Setter
@Getter
public class Airport {
    private String name;
    private List<Runaway> runaways;
    private List<Flight> airportFlights;

    public Airport(String name, int runwayNumber) {
        setName(name);
        this.runaways = new ArrayList<>();
        this.airportFlights = new ArrayList<>();

        for(int i = 0; i < runwayNumber; i++){
            runaways.add(new Runaway("Runaway " + i));
        }
    }

    public boolean scheduleFlight(Flight flight){
        for(Runaway runaway : runaways){
            if(runaway.canAddFlight(flight)){
                runaway.addFlight(flight);
                airportFlights.add(flight);
                return true;
            }
        }
        return false;
    }

    public void printSchedule(){
        System.out.println("Flights schedule for: " + name + " airport");
        for(Runaway runaway : runaways){
            System.out.print(runaway);
        }
    }
}
