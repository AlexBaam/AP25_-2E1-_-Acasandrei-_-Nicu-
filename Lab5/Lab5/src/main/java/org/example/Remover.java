package org.example;

public class Remover extends Commands {

    public Remover(String fileName, Repository repo) {
        super(fileName, repo);
    }

    public void executeCommand(String fileName){
        repo.remove(fileName);
        System.out.println("Removed " + fileName);
    }
}
