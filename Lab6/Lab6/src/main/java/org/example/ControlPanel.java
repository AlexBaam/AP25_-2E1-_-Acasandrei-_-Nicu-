package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JButton exportButton = new JButton("Export");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init(){
        //change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 4));
        add(loadButton);
        add(saveButton);
        add(exportButton);
        add(exitButton);

        //configure listeners for all buttons
        exitButton.addActionListener(this::exitGame);
    }

    private void exitGame(ActionEvent e) {
        frame.dispose();
    }
}
