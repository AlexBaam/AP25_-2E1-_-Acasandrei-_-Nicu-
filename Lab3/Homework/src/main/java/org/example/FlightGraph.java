package org.example;
import java.util.*;

public class FlightGraph {
    private int[][] adjacencyMatrix;
    private List<Flight> flightList;
    private Map<Flight, Integer> runwayAssign;

    public FlightGraph(Set<Flight> flights){
        int n = flights.size();
        adjacencyMatrix = new int[n][n];
        flightList = new ArrayList<>(flights);
        runwayAssign = new HashMap<>();

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
             if(checkConflict(flightList.get(i),flightList.get(j))){
                 adjacencyMatrix[i][j] = 1;
                 adjacencyMatrix[j][i] = 1;
             }
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(i == j) {
                    adjacencyMatrix[i][j] = 0;
                }
            }
        }
    }

    private boolean checkConflict(Flight flight1, Flight flight2){
        return !(flight1.getSchedule().getSecond().isBefore(flight2.getSchedule().getFirst()) ||
                flight1.getSchedule().getFirst().isAfter(flight2.getSchedule().getSecond()));
    }

    public int[][] getAdjacencyMatrix(){
        return adjacencyMatrix;
    }

    public List<Flight> getFlightList(){
        return flightList;
    }

    @Override
    public String toString() {
        return "FlightGraph: \n" +
                "Adjacency Matrix: \n" + Arrays.toString(adjacencyMatrix);
    }

    public void printAdjacencyMatrixWithLabels() {
        System.out.print("    ");
        for (Flight flight : flightList) {
            System.out.print(flight.getFlightNumber() + " ");
        }
        System.out.println();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.out.print(flightList.get(i).getFlightNumber() + " ");
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Map<Flight, Integer> dSaturColoring(){
        int flightNumber = flightList.size();
        int[] assignedRunwaysArray = new int[flightNumber];
        Arrays.fill(assignedRunwaysArray, -1); // -1 is uncolored

        int[] saturDegree = new int[flightNumber];

        for(int count = 0; count < flightNumber; count++){
            int maxSat = -1;
            int selectedFlightIndex = -1;

            for(int i = 0; i < flightNumber; i++){
                if(assignedRunwaysArray[i] == -1){
                    if(saturDegree[i] > maxSat){
                        maxSat = saturDegree[i];
                        selectedFlightIndex = i;
                    }
                }
            }

            Set<Integer> usedRunway = new HashSet<>();
            for(int j = 0; j < flightNumber; j++){
                if((adjacencyMatrix[selectedFlightIndex][j] == 1) && (assignedRunwaysArray[j] != -1)){
                    usedRunway.add(assignedRunwaysArray[j]);
                }
            }

            int assignedRunway = 0;
            while(usedRunway.contains(assignedRunway)){
                assignedRunway++;
            }

            assignedRunwaysArray[selectedFlightIndex] = assignedRunway;

            for(int j = 0; j < flightNumber; j++){
                if((adjacencyMatrix[selectedFlightIndex][j] == 1) && (assignedRunwaysArray[j]) == -1){
                    saturDegree[j]++;
                }
            }
        }

        for(int i = 0; i < flightNumber; i++){
            runwayAssign.put(flightList.get(i), i);
        }

        return runwayAssign;
    }
}
