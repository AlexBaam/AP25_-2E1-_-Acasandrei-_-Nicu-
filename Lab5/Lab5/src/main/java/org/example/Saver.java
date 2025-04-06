package org.example;
import java.io.IOException;

public class Saver extends Commands {
    private String format;
    public Saver(String fileName, Repository repo, String format) {
        super(fileName, repo);
        this.format = format;
    }

    @Override
    public void executeCommand() throws IOException, InvalidDataException {
        repo.save(fileName, format);
    }
}
