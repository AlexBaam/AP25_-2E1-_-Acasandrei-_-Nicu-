package org.example;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
            }
        });
    }
}

/*
* Serializarea obiectelor
- transformarea unui obiect într-un flux de byte
- acest flux poate fi apoi salvat intr-un fisier si folosit pentru a restaura obiectul mai tarziu
*


* */