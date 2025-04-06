package org.example;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.Scanner;

public class Shell {

    public Shell(){}; //Empty default constructor

    public void start() throws IOException, InvalidCommandException {
        Scanner sc = new Scanner(System.in);
        Repository repo = new Repository();

        while(true){
            System.out.println("Shell commands:\n"
                    + "1. exit\n"
                    + "2. add\n"
                    + "3. addAll\n"
                    + "4. remove\n"
                    + "5. format\n"
                    + "6. update\n"
                    + "7. report\n"
                    + "8. display\n"
                    + "9. bonus\n");

            String command = sc.nextLine();

            try{
            switch (command) {
                case "exit":
                    System.exit(0);
                    break;

                case "add": {
                    String[] argsAdd = new String[2];
                    System.out.println("Enter picture name: ");
                    argsAdd[0] = sc.nextLine();
                    System.out.println("Enter path: ");
                    argsAdd[1] = sc.nextLine();

                    Adder adder = new Adder(argsAdd, repo);
                    adder.executeCommand();
                    break;
                }

                case "addAll": {
                    System.out.println("Adding all the images to the repository...");

                    AllAdd everything = new AllAdd(repo);
                    everything.executeCommand();
                    break;
                }

                case "remove": {
                    String[] argsRemove = new String[1];
                    System.out.println("Enter picture name: ");
                    argsRemove[0] = sc.nextLine();

                    Remover rem = new Remover(argsRemove[0], repo);
                    rem.executeCommand(argsRemove[0]);
                    break;
                }

                case "format": {
                    Formating form = new Formating(repo);
                    form.start();
                    break;
                }

                case "update": {
                    String[] argsUpdate = new String[2];
                    System.out.println("Enter picture name: ");
                    argsUpdate[0] = sc.nextLine();
                    System.out.println("Enter new picture name: ");
                    argsUpdate[1] = sc.nextLine();

                    Updater upd = new Updater(argsUpdate, repo);
                    upd.executeCommand();
                    break;
                }

                case "report": {
                    String[] argsReport = new String[1]; // poate fi gol, dar e aici pt. consistență
                    ReportCmd report = new ReportCmd(argsReport, repo);
                    report.executeCommand();
                    break;
                }

                case "display": {
                    String[] argsDisplay = new String[1];
                    System.out.println("Enter picture name: ");
                    argsDisplay[0] = sc.nextLine();

                    Displayer displayer = new Displayer(argsDisplay, repo);
                    displayer.executeCommand();
                    break;

                }

                case "bonus": {
                    MaximalGroups maximalGroups = new MaximalGroups(repo);
                    Graph<Image, DefaultEdge> graph = maximalGroups.buildGraph();
                    int maxCliqueSize = maximalGroups.findMaxCliqueSize(graph);
                    maximalGroups.findAndDisplayCliquesOfSize(graph, maxCliqueSize);
                    break;
                }

                default:
                    throw new InvalidCommandException("Invalid command: " + command);
            }
        } catch (InvalidCommandException | InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }
}
}
