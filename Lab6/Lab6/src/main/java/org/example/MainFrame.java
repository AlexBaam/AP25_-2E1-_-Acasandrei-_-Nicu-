//Cod pentru fereastra in care se afla jocul
package org.example;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    ConfigPanel configPanel; // Panou de configurare al unui joc (New Game)
    ControlPanel controlPanel; // Load, Save, Export, Exit
    DrawingPanel drawingPanel; // Canva
    Scoreboard scoreboard; // Panou cu scorurile jucatorilor

    private boolean secondPlayerIsAI;
    private int aiDifficulty;
    private GameAI gameAI;

    public MainFrame() {
        super("My Drawing Application"); // App title
        init(); // Start app cand se da run la cod
    }

    private void init() {
        setupWindow(); // Setari pentru MainFrame
        createComponents(); // Creare componente din MainFrame
        layoutComponents(); // Aranjare componente din MainFrame
        setVisible(true); // Face fereastra vizibila
    }

    private void setupWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Nu se poate da resize la fereastra prin tras de margini
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen (MAXIMIZED_BOTH este un macro ce maxeaza si orizontal si vertical)
        setUndecorated(false); // Ptr a nu fi borderless fullscreen
    }

    private void createComponents() {
        drawingPanel = new DrawingPanel(this);
        configPanel = new ConfigPanel(this, drawingPanel);
        controlPanel = new ControlPanel(this);
        scoreboard = new Scoreboard();
    }

    private void layoutComponents() {
        JPanel toCenterPanel = new JPanel(new GridBagLayout()); //Pentru a pune pe centru canva-ul
        toCenterPanel.add(drawingPanel);
        add(configPanel, BorderLayout.NORTH); // Butoane de sus
        add(controlPanel, BorderLayout.SOUTH); // Butoane de jos// Scor
        add(toCenterPanel, BorderLayout.CENTER); // Canva
        add(scoreboard, BorderLayout.WEST);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setupForAI(boolean isAI, int difficulty) {
        this.secondPlayerIsAI = isAI;
        this.aiDifficulty = difficulty;
        if(isAI){
            this.gameAI = new GameAI(difficulty, drawingPanel);
        }
    }

    public boolean isSecondPlayerAI() {
        return secondPlayerIsAI;
    }

    public GameAI getGameAI() {
        return gameAI;
    }

    public int getAiDifficulty() {
        return aiDifficulty;
    }

    public void setSecondPlayerAI(boolean isAI) {
        this.secondPlayerIsAI = isAI;
    }

    public void setAiDifficulty(int aiDifficulty) {
        this.aiDifficulty = aiDifficulty;
    }

    public void setGameAI(GameAI gameAI) {
        this.gameAI = gameAI;
    }

    public ConfigPanel getConfigPanel() {
        return configPanel;
    }
}
