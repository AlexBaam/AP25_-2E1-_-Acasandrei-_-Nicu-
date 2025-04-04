package org.example;

public class Updater extends Commands {

    public Updater(String[] args,Repository repo) {
        super(args, repo);
    }

    @Override
    public void executeCommand() {
        repo.update(args[0], args[1]);
    }
}
