package org.example;
import java.time.LocalDate;

public record Image(String filename,LocalDate localDate,String tags , String path) {
}
