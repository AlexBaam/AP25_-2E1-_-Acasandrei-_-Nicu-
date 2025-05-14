package org.example;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    // initialize socket and in output streams
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Scanner keyboardReader = null;

    // constructor to put ip address and port
    public GameClient(String address, int port)
    {
        // establish a connection
        try {

            socket = new Socket(address, port);
            /*
            Socketurile sunt end points ptr conexiune, ii spui la ce adresa si ce port sa se conecteze,
            iar acesta o va face
             */
            System.out.println("Connected to server on port " + port);

            keyboardReader = new Scanner(System.in);
            /*
            Ptr a citi de la tastatura folosim Scanner, l-am mai folosit anterior in Lab5, cand dadeam comenzi in terminal
            de la tastatura
            Mai exista optiunea cu BufferedReader, dar arunca exceptii si necesita verificari in plus
            Diferenta este si viteza, Scanner fiind mai lent, dar si faptul ca newLine poate da peste cap inputul
            Totusi Scanner este mai user friendly
             */

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(in.readLine());
            System.out.println(in.readLine());

            String userInput = "";

            while(true){
                System.out.print("Command: ");
                userInput = keyboardReader.nextLine().trim().toLowerCase();

                if(userInput.equals("exit")){
                    out.println(userInput);
                    break;
                }

                out.println(userInput);

                String responseLine;
                while((responseLine = in.readLine()) != null){
                    if(responseLine.isEmpty()){
                        break;
                    }
                    System.out.println("Server responseLine: " + responseLine);
                }
            }

            socket.close();
            out.close();
            in.close();
            keyboardReader.close();
        }

        catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {

        GameClient client = new GameClient("127.0.0.1", 5000);
    }
}
