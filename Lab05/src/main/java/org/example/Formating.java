package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Formating {
    private final Scanner sc;
    private final Repository repo;

    public Formating(Repository repo) {
        this.sc = new Scanner(System.in);
        this.repo = repo;
    }

    public void start() throws IOException, InvalidDataException {
        String format;
        System.out.println("Select format: \n"
                + "JSON\n"
                + "Binary\n"
                + "Text\n");
        format = sc.nextLine();
        format = format.toLowerCase();

        try {
            switch (format) {
                case "json": {
                    String command2 = "";
                    System.out.println("Selected format: JSON\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    command2 = command2.toLowerCase();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver jsonSave = new Saver(fileName, repo, format);
                            jsonSave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader jsonLoad = new Loader(fileName, repo, format);
                            jsonLoad.executeCommand();
                            break;
                        }
                    }
                    break;
                }
                case "binary": {
                    String command2 = "";
                    System.out.println("Selected format: Binary\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    command2 = command2.toLowerCase();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver binarySave = new Saver(fileName, repo, format);
                            binarySave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader binaryLoad = new Loader(fileName, repo, format);
                            binaryLoad.executeCommand();
                            break;
                        }
                    }
                    break;
                }
                case "text": {
                    String command2 = "";
                    System.out.println("Selected format: Text\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    command2 = command2.toLowerCase();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver textSave = new Saver(fileName, repo, format);
                            textSave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader textLoad = new Loader(fileName, repo, format);
                            textLoad.executeCommand();
                            break;
                        }
                    }
                    break;
                }
                default: {
                    throw new InvalidFormatException("Invalid format: " + format);
                }
            }
        } catch (InvalidFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
