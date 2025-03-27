package org.example;

import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String args[]) throws IOException {
        Repository repo = new Repository();

        repo.add(new Image("Cat", LocalDate.now(),"C:/Users/AlexA/Downloads/images.jpeg"));

        repo.display("Cat");
    }
}