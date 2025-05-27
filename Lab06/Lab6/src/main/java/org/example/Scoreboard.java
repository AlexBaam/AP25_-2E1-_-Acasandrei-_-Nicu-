package org.example;

import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {
    private JLabel redScoreLabel;
    private JLabel blueScoreLabel;

    public Scoreboard() {
        setLayout(new GridLayout(4,1));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,10));

        Font scoreFont = new Font("Arial", Font.BOLD, 16);
        JLabel scoreLabel = new JLabel("Scoreboard");

        scoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(scoreLabel);

        redScoreLabel = new JLabel("Red Score: 0.00");
        redScoreLabel.setForeground(Color.RED);
        redScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        redScoreLabel.setFont(scoreFont);
        add(redScoreLabel);

        blueScoreLabel = new JLabel("Blue Score: 0.00");
        blueScoreLabel.setForeground(Color.BLUE);
        blueScoreLabel.setHorizontalAlignment(SwingConstants.LEFT);
        blueScoreLabel.setFont(scoreFont);
        add(blueScoreLabel);

        add(new JLabel(""));
    }

    public void updateRedScore(double redScore) {
        redScoreLabel.setText(String.format("Red Score: %.2f", redScore));
    }

    public void updateBlueScore(double blueScore) {
        blueScoreLabel.setText(String.format("Blue Score: %.2f", blueScore));
    }
}
