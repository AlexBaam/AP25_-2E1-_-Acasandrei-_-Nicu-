package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class ConfigPanel extends JPanel {
    private final MainFrame frame;
    JLabel label;
    JSpinner spinner;
    JButton button;

    DrawingPanel canvas;

    private List<Point> dots  = new ArrayList<>();
    private Random random = new Random();

    public ConfigPanel(MainFrame frame, DrawingPanel canvas) {
        this.frame = frame;
        this.canvas = canvas;
        init();
    }

    private void init() {
        //Number of dots button:
        label = new JLabel("Number of dots:");
        spinner = new JSpinner(new SpinnerNumberModel(15, 2, 30, 1));

        add(label); //JPanel uses FlowLayout by default
        add(spinner);

        //New game button:
        button = new JButton("Generate Dots");
        add(button);

        button.addActionListener(this::generateDots);
    }

    private void generateDots(ActionEvent e) {
        canvas.hideDots(dots);
        dots.clear();

        dotsLocation();
        canvas.fillWithDots(dots);
    }

    private void dotsLocation() {
        int x = 0, y = 0;

        int dotsNumber = (int) spinner.getValue();
        for(int i = 0; i < dotsNumber; i++) {
            boolean cantBeAdded = true;
            while(cantBeAdded) {
                cantBeAdded = false;

                x = random.nextInt(20,380);
                y = random.nextInt(20,380);

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
