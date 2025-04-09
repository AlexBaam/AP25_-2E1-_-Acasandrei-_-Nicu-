//Cod pentru butoanele de sus

package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;
    JLabel label;
    JSpinner spinner; // Numarul de puncte ce vor fi selectate (Spinner este butonul cu up/down la modificarea valorii din el)
    JButton button; // Generate dots button


    DrawingPanel canvas;

    private List<Point> dots  = new ArrayList<>(); // Lista cu punctele ce vor fi generate
    private Random random = new Random();

    public ConfigPanel(MainFrame frame, DrawingPanel canvas) {
        this.frame = frame;
        this.canvas = canvas;
        init();
    }

    private void init() {
        //Number of dots button:
        label = new JLabel("Number of dots:"); // Text de langa spinner
        spinner = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));

        add(label); //JPanel uses FlowLayout by default
        add(spinner);

        //New game button:
        button = new JButton("Generate Dots");
        add(button);

        button.addActionListener(this::generateDots); // Adaugarea unei actiuni pentru buton
    }

    private void generateDots(ActionEvent e) {
        dots.clear(); // Stergem vechea lista de puncte
        dotsLocation(); // Generam locatii random ptr puncte
        canvas.fillWithDots(dots); // Generam puncte
        canvas.resetGame(); // Reset the scores
    }

    private void dotsLocation() {
        int x = 0, y = 0;

        int dotsNumber = (int) spinner.getValue();// obtinem nr de puncte din spinner
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

}
