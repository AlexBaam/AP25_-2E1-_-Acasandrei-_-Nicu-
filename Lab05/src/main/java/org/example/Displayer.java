package org.example;
import java.io.IOException;

public class Displayer extends Commands {
    public Displayer(String[] args, Repository repo) {
        super(args, repo);
    }

    @Override
    public void executeCommand() throws IOException {
        repo.display(args[0]);
    }

}
