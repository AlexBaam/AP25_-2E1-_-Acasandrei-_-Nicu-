package org.example;

import java.io.IOException;

public class Loader extends Commands {

    public Loader(String fileName, Repository repo) {
        super(fileName, repo);
    }

    @Override
    public void executeCommand() throws IOException {
        repo.load(fileName);
    }
}
