package org.example;
import java.time.LocalDate;

public record Image(String filename, LocalDate date, String path) {
}
