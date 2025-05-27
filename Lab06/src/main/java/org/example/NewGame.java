package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewGame extends JDialog {
    private final MainFrame frame;
    private final DrawingPanel canvas;

    JSpinner dotSpinner;
    JComboBox<String> playerType;
    JComboBox<String> playerDifficulty;

    public NewGame(MainFrame frame, DrawingPanel canvas){
        super(frame, "New Game Setup", true);
        this.frame = frame;
        this.canvas = canvas;
        initUI();
    }

    private void initUI(){
        setLayout(new GridLayout(4,2,10,10));

        //Number of dots button:
        dotSpinner = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));
        add(new JLabel("Number of dots:")); // Label ptr nr de puncte
        add(dotSpinner);

        //Player two:
        playerType = new JComboBox<>(new String[]{"Human", "AI"});
        add( new JLabel("Player two:")); // Label pentru nr de jucatori
        add(playerType);

        //AI difficulty:
        playerDifficulty = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        add(new JLabel("AI difficulty:")); // Label pentru dificultate
        add(playerDifficulty);

        // Sa apara sau nu dificultatea bazat pe tipul de player:
        playerType.addActionListener(e -> {
            boolean isHuman = playerType.getSelectedItem().equals("Human"); // verificam daca player este om
            playerDifficulty.setEnabled(!isHuman); // Daca este om dezactivam acel drop down
        });
        playerDifficulty.setEnabled(false); // Initial este true (presupunem ca e AI)

        // Buton de start dupa setup:
        JButton startButton = new JButton("Start");
        startButton.addActionListener(this::startGame);
        add(new JLabel());
        add(startButton);

        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void startGame(ActionEvent actionEvent) {
        int dots = (Integer) dotSpinner.getValue();
        boolean isHuman = playerType.getSelectedItem().equals("Human");
        int difficulty = playerDifficulty.getSelectedIndex();

        canvas.resetGame();
        ((ConfigPanel) frame.getContentPane().getComponent(0)).generateNewGame(dots, !isHuman, difficulty);

        dispose();
    }
}
