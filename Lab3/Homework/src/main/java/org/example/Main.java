package org.example;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Airline A1 = new Airline("A1", "Passenger", 1, 70, 100);
        Airline A2 = new Airline("A2", "Passenger", 2, 70, 100);
        Freighter F1 = new Freighter("F1", "Cargo", 1, 70, 300);
        Drone D1 = new Drone("D1", "Cargo", 1, 100, 10);

        Flight flight1 = new Flight("N1", A1, LocalTime.of(10, 0), LocalTime.of(10, 30));
        Flight flight2 = new Flight("N2", A2, LocalTime.of(10, 15), LocalTime.of(10, 45));
        Flight flight3 = new Flight("N3", F1, LocalTime.of(10, 45), LocalTime.of(11, 0));
        Flight flight4 = new Flight("N4", D1, LocalTime.of(10, 30), LocalTime.of(11, 0));

        Set<Flight> flightSet = new HashSet<Flight>();
        flightSet.add(flight1);
        flightSet.add(flight2);
        flightSet.add(flight3);
        flightSet.add(flight4);

        Airport Bacau = new Airport("Bacau", 3);

        Manager Nicu = new Manager(Bacau, flightSet);

        Nicu.solver();

        Nicu.printSolution();

        FlightGraph graph = new FlightGraph(flightSet);

        graph.printAdjacencyMatrixWithLabels();

        Nicu.bonusSolver();
    }
}