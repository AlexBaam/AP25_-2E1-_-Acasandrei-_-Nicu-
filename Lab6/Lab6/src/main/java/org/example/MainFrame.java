//Cod pentru fereastra in care se afla jocul

package org.example;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel; // Select dots number, generate dots (new game basically)
    ControlPanel controlPanel; // Load, Save, Export, Exit
    DrawingPanel drawingPanel; // Canva
    Scoreboard scoreboard; // Scor

    public MainFrame() {
        super("My Drawing Application"); // App title
        init(); // Start app cand se da run la cod
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Nu se poate da resize la fereastra prin tras de margini

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen (MAXIMIZED_BOTH este un macro ce maxeaza si orizontal si vertical)
        setUndecorated(false); // Ptr a nu fi borderless fullscreen

        drawingPanel = new DrawingPanel(this);
        configPanel = new ConfigPanel(this, drawingPanel);
        controlPanel = new ControlPanel(this);
        scoreboard = new Scoreboard();

        JPanel toCenterPanel = new JPanel(new GridBagLayout()); //Pentru a pune pe centru canva-ul
        toCenterPanel.add(drawingPanel);

        add(configPanel, BorderLayout.NORTH); // Butoane de sus
        add(controlPanel, BorderLayout.SOUTH); // Butoane de jos// Scor
        add(toCenterPanel, BorderLayout.CENTER); // Canva
        add(scoreboard, BorderLayout.WEST);

        setVisible(true); // Face fereastra vizibila
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }
}
