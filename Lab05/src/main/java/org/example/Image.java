package org.example;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record Image(String filename, LocalDate localDate, List<String> tags , String path) implements Serializable {
}
