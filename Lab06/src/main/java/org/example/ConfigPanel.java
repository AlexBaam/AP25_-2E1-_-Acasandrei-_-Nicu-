//Cod pentru butoanele de sus

package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;
    JButton newGameButton; // New game button
    DrawingPanel canvas;
    JLabel currentTurnLabel;

    private List<Point> dots  = new ArrayList<>(); // Lista cu punctele ce vor fi generate
    private Random random = new Random();
    private GameSound gameSound = new GameSound();

    public ConfigPanel(MainFrame frame, DrawingPanel canvas) {
        this.frame = frame;
        this.canvas = canvas;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //New game button:
        newGameButton = new JButton("New Game");
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.addActionListener(e -> new NewGame(frame, canvas)); // Adaugarea unei actiuni pentru buton
        add(newGameButton);

        //Player turn label:
        currentTurnLabel = new JLabel("Current Turn: Red");
        currentTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTurnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(currentTurnLabel);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void generateNewGame(int dotCount, boolean isAI, int difficulty) {
        dots.clear();
        dotsLocation(dotCount);
        canvas.fillWithDots(dots);
        canvas.resetGame();
        frame.setupForAI(isAI, difficulty);
        gameSound.play("sounds/reset.wav");
    }

    private void dotsLocation(int dotCount) {
        int x = 0;
        int y = 0;

        int dotsNumber = dotCount;// obtinem nr de puncte din spinner
        for(int i = 0; i < dotsNumber; i++) {
            boolean cantBeAdded = true; // Verificare sa nu se suprapuna exact (dar ele tot se pot suprapune pe margini
            // Fiindca eu verific pixel cu pixel ca ele nu se suprapun, nu pe o anumita raza
            while(cantBeAdded) {
                cantBeAdded = false;

                //Generez coordonate random pentru un punct
                x = random.nextInt(20,780);
                y = random.nextInt(20,630);

                // Iterez prin toate punctele, daca se suprapun nu mai poate fi adaugat
                for(Point dot : dots){
                    if(dot.x == x && dot.y == y){
                        cantBeAdded = true;
                    }
                }
            }
            dots.add(new Point(x,y));
        }
    }

    public void updateCurrentPlayer(int currentPlayer) {
        currentTurnLabel.setText("Current turn: " + (currentPlayer == 0 ? "Red" : "Blue"));
    }

}
