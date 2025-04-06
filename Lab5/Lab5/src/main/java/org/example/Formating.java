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

        try {
            switch (format) {
                case "JSON": {
                    System.out.println("Selected format: JSON\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    String command2 = null;
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver jsonSave = new Saver(fileName, repo);
                            jsonSave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader jsonLoad = new Loader(fileName, repo);
                            jsonLoad.executeCommand();
                            break;
                        }
                    }
                    break;
                }
                case "Binary": {
                    System.out.println("Selected format: JSON\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    String command2 = null;
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver binarySave = new Saver(fileName, repo);
                            binarySave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader binaryLoad = new Loader(fileName, repo);
                            binaryLoad.executeCommand();
                            break;
                        }
                    }
                    break;
                }
                case "Text": {
                    System.out.println("Selected format: JSON\n");
                    System.out.println("Select command: \n" +
                            "1. save\n" +
                            "2. load\n");
                    String command2 = null;
                    System.out.println("Enter command: ");
                    command2 = sc.nextLine();
                    switch (command2) {
                        case "save": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Saver textSave = new Saver(fileName, repo);
                            textSave.executeCommand();
                            break;
                        }
                        case "load": {
                            System.out.println("Enter picture name: ");
                            String fileName = sc.nextLine();

                            Loader textLoad = new Loader(fileName, repo);
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
