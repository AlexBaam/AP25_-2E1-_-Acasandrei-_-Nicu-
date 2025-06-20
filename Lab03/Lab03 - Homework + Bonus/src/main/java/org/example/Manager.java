package org.example;
import javax.print.DocFlavor;
import  java.util.*;

public class Manager {
    private Airport airport;
    private Set<Flight> flights;
    private Map<Flight, Runaway> flightMap;

    public Manager(Airport airport, Set<Flight> flights) {
        this.airport = airport;
        this.flights = flights;
        this.flightMap = new HashMap<>();
    }

    public void solver(){
        List<Flight> flightList=  new ArrayList<>(flights);
        flightList.sort(Comparator.comparing(f -> f.getSchedule().getFirst()));

        for(Flight flight : flightList){
            boolean scheduled = false;

            for(Runaway runaway : airport.getRunaways()){
                if(runaway.canAddFlight(flight)){
                    runaway.addFlight(flight);
                    flightMap.put(flight, runaway);
                    scheduled = true;
                    break;
                }
            }

            if(!scheduled){
                System.out.println("Flight " + flight.getFlightNumber() + " not scheduled!");
            }
        }
    }

    public void printSolution(){
        System.out.println("Flight Schedule:");

        for(Map.Entry<Flight, Runaway> entry : flightMap.entrySet()) {
            Flight flight = entry.getKey();
            Runaway runaway = entry.getValue();
            System.out.print("Flight " + flight.getFlightNumber() + " : "
                    + " (" + flight.getAircraft().getName() + ") -> Assigned to " + runaway.getName() + '\n');
        }

        System.out.println('\n');

        Set<Flight> unscheduledFlights = new HashSet<>(flights);
        unscheduledFlights.removeAll(flightMap.keySet()); // To remove scheduled flights

        if(!unscheduledFlights.isEmpty()){
            System.out.println("Unscheduled flights: ");
            for(Flight flight : unscheduledFlights){
                System.out.println("Flight: " + flight.getFlightNumber() + " could not be scheduled!");
            }
        }
    }

    public void bonusSolver(){
        FlightGraph graph = new FlightGraph(flights);
        Map<Flight, Integer> assignedRunways = graph.dSaturColoring();

        for(Map.Entry<Flight, Integer> entry : assignedRunways.entrySet()){
            Flight flight = entry.getKey();
            int runwayIndex = entry.getValue();

            if(runwayIndex >= airport.getRunaways().size()){
                System.out.println("\nNot enough runways! Minimum needed: " + (runwayIndex + 1));
                return;
            }

            airport.getRunaways().get(runwayIndex).addFlight(flight);
            flightMap.put(flight, airport.getRunaways().get(runwayIndex));
        }

        printSolution();
    }
}
