package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel;
    ControlPanel controlPanel;
    DrawingPanel drawingPanel;

    public MainFrame() {
        super("My Drawing Application");
        init();
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setSize(800, 650);

        drawingPanel = new DrawingPanel(this);
        configPanel = new ConfigPanel(this, drawingPanel);
        controlPanel = new ControlPanel(this);

        JPanel toCenterPanel = new JPanel(new GridBagLayout());
        toCenterPanel.add(drawingPanel);

        add(configPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
        add(toCenterPanel, BorderLayout.CENTER);
    }
}
