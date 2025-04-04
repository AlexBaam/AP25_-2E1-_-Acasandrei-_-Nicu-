package org.example;
import java.io.IOException;

public class Saver extends Commands {

    public Saver(String fileName, Repository repo) {
        super(fileName, repo);
    }

    @Override
    public void executeCommand() throws IOException, InvalidDataException {
        repo.save(fileName);
    }
}
