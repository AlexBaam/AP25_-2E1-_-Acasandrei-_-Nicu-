package org.example;
import java.time.LocalDate;
import java.util.List;

public record Image(String filename, LocalDate localDate, List<String> tags , String path) {
}
