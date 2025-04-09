package org.example;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private JLabel redScoreLabel;
    private JLabel blueScoreLabel;

    public Scoreboard() {
        setLayout(new GridLayout(2,1)); // 2 rows, 1 column

        redScoreLabel = new JLabel("Red Score: 0.00");
        redScoreLabel.setForeground(Color.RED);
        redScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        blueScoreLabel = new JLabel("Blue Score: 0.00");
        blueScoreLabel.setForeground(Color.BLUE);
        blueScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(redScoreLabel);
        add(blueScoreLabel);
    }

    public void updateRedScore(double redScore) {
        redScoreLabel.setText(String.format("Red Score: %.2f", redScore));
    }

    public void updateBlueScore(double blueScore) {
        blueScoreLabel.setText(String.format("Blue Score: %.2f", blueScore));
    }


}
