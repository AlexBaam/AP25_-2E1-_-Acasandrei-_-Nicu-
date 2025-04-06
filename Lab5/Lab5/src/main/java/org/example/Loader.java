package org.example;

import java.io.IOException;

public class Loader extends Commands {
    private String format;

    public Loader(String fileName, Repository repo, String format) {
        super(fileName, repo);
        this.format = format;
    }

    @Override
    public void executeCommand() throws IOException {
        repo.load(fileName, format);
    }
}
