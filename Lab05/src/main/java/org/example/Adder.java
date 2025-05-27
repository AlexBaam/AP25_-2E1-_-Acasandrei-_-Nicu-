package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adder extends Commands {
    private Random rand = new Random();

    public Adder(String[] args, Repository repo) {
        super(args, repo);
    }

    @Override
    public void executeCommand() {
        LocalDate date = LocalDate.now();
        String[] tags = {"Cute", "Funny", "Animal", "Stupid", "Silly", "Baby"};
        List<String> tagsList = tagsGen(tags);
        Image img = new Image(args[0], date , tagsList ,args[1]);
        repo.add(img);
    }

    private List<String> tagsGen(String[] tags){
        List<String> tagsList = new ArrayList<>();

        int randomIndex = rand.nextInt(1,tags.length);
        int i = 0;
        while(i < randomIndex){
           String randomTag = tags[rand.nextInt(tags.length)];

           if(!tagsList.contains(randomTag)) {
               tagsList.add(randomTag);
               i++;
           }
        }

        return tagsList;
    }
}
