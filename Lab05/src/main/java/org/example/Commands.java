package org.example;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public abstract class Commands {
    protected String[] args;
    protected String fileName;
    protected Repository repo;

    protected Commands(String[] args, Repository repo) {
        this.args = args;
        this.repo = repo;
    }

    protected Commands(String fileName, Repository repo) {
        this.fileName = fileName;
        this.repo = repo;
    }

    protected void executeCommand() throws IOException, InvalidDataException {
        System.out.println("Invalid command!");
    }
}
