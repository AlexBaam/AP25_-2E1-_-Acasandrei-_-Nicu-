package org.example;
import java.time.LocalTime;
import lombok.*;

@Setter
@Getter
public class Flight {
    private String flightNumber;
    private Aircraft aircraft;
    private Pair<LocalTime, LocalTime> schedule;

    public Flight(String flightNumber, Aircraft aircraft, LocalTime departureTime, LocalTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.aircraft = aircraft;
        this.schedule = new Pair<>(departureTime, arrivalTime);
    }

    public boolean checkScheduleConflict(Flight otherFlight) {
        return this.schedule.getSecond().isBefore(otherFlight.getSchedule().getFirst())
                || this.schedule.getFirst().isAfter(otherFlight.getSchedule().getSecond());
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + '\n' +
                " Schedule: " + schedule + '\n';
    }
}
