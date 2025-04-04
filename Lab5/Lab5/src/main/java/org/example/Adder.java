package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

public class Adder extends Commands {
    private Random rand = new Random();

    public Adder(String[] args, Repository repo) {
        super(args, repo);
    }

    @Override
    public void executeCommand() {
        LocalDate date = LocalDate.now();
        String[] tags = {"Cute", "Funny", "Animal", "Stupid", "Silly"};
        Image img = new Image(args[0], date ,tags[rand.nextInt(tags.length)] ,args[1]);
        repo.add(img);
    }

    public void display(String fileName) throws IOException {
        if(!fileName.equals(args[0])) {
            System.out.println("Image not added!");
        } else {
            repo.display(fileName);
        }
    }
}
