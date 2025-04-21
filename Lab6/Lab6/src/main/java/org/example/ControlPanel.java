//Exit, Export, Save, Load

package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.ArrayList;
import java.util.List; // Corect pentru a folosi List din java.util
import java.awt.Point; // Pentru clasa Point din AWT


public class ControlPanel extends JPanel {
    private final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JButton exportButton = new JButton("Export");

    JButton settingsButton = new JButton("Settings");
    JButton historyButton = new JButton("History");

    private final GameSound gameSound = new GameSound();

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init(){
        setLayout(new GridLayout(1, 2));
        add(historyButton);
        add(settingsButton);

        //Action listener pentru history
        historyButton.addActionListener(e -> {
            List<String> history = frame.getDrawingPanel().getGameLogic().getHistory();
            new GameHistory(frame, history);
        });

        //Action listener pentru settings (aici vom gasi export, save, load si exit)
        settingsButton.addActionListener(e-> openSettingsWindow());
    }

    private void openSettingsWindow() {
        JDialog settingsDialog = new JDialog(frame, "Settings", true);
        settingsDialog.setLayout(new GridLayout(2,2,10,10));
        settingsDialog.setSize(300,150);

        for (ActionListener al : loadButton.getActionListeners()){
            loadButton.removeActionListener(al);
        }
        for (ActionListener al : saveButton.getActionListeners()){
            saveButton.removeActionListener(al);
        }
        for (ActionListener al : exportButton.getActionListeners()){
            exportButton.removeActionListener(al);
        }
        for (ActionListener al : exitButton.getActionListeners()){
            exitButton.removeActionListener(al);
        }

        settingsDialog.add(loadButton);
        settingsDialog.add(saveButton);
        settingsDialog.add(exportButton);
        settingsDialog.add(exitButton);

        //configure listeners for all buttons
        exitButton.addActionListener(this::exitGame); // Actiune buton
        exportButton.addActionListener(this::exportImage);
        saveButton.addActionListener(this::saveGame);
        loadButton.addActionListener(this::loadGame);

        settingsDialog.setLocationRelativeTo(frame);
        settingsDialog.setVisible(true);
    }

    // Ce face butonul
    private void exitGame(ActionEvent e) {
        frame.dispose();
    }

    private void exportImage(ActionEvent e) {
        DrawingPanel drawingPanel = frame.getDrawingPanel(); // Obtinem panoul de desen (DrawingPanel) din fereastra principala
        try {

            File file = new File("game_board.png"); // creaza fisierul png unde vom salva imaginea

            boolean success = drawingPanel.exportToPNG(file);  // salveaza imaginea ca fisier png

            if (success) {
                JOptionPane.showMessageDialog(frame, "Game board exported successfully!", "Export", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to export the game board.", "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error while exporting image: " + ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveGame(ActionEvent e) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game_state.ser"))) {
            DrawingPanel drawingPanel = frame.getDrawingPanel();

            // salvam punctele
            out.writeObject(drawingPanel.getDotsLocations());

            // salvam liniile + culoare
            List<Line> redLines = drawingPanel.getGameLogic().getRedLines();
            List<Line> blueLines = drawingPanel.getGameLogic().getBlueLines();
            out.writeObject(redLines);
            out.writeObject(blueLines);

            // salvam scorurile jucatorilor
            out.writeDouble(frame.getDrawingPanel().getGameLogic().getRedScore());
            out.writeDouble(frame.getDrawingPanel().getGameLogic().getBlueScore());

            // salvam tipul de jucator cu care jucam
            out.writeBoolean(frame.isSecondPlayerAI());

            // salvam dificultatea AI-ului
            out.writeInt(frame.getAiDifficulty());

            // salvam ce player urmeaza
            out.writeInt(drawingPanel.getCurrentPlayer());

            gameSound.play("sounds/save_load.wav");
            JOptionPane.showMessageDialog(frame, "Game saved successfully!", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error while saving game: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGame(ActionEvent e) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game_state.ser"))) {
            DrawingPanel drawingPanel = frame.getDrawingPanel();
            Scoreboard scoreboard = frame.getScoreboard();

            // incarcam punctele
            List<Point> loadedDots = (List<Point>) in.readObject();

            // incarcam liniile si culorile acestora
            List<Line> loadedRedLines = (List<Line>) in.readObject();
            List<Line> loadedBlueLines = (List<Line>) in.readObject();
            List<Line> allLines = new ArrayList<>();
            allLines.addAll(loadedRedLines);
            allLines.addAll(loadedBlueLines);


            // incarcam scorurile jucatorilor
            double redScore = in.readDouble();
            double blueScore = in.readDouble();

            // incarcam detaliile despre AI
            boolean isAI = in.readBoolean();
            int difficulty = in.readInt();
            int currentPlayer = in.readInt();

            // actualizam DrawingPanel cu punctele si liniile incarcate
            drawingPanel.fillWithDots(loadedDots);

            // adauga liniile in DrawingPanel
            drawingPanel.setLines(allLines);  /// ne asiguram ca liniile sunt incarcate !!

            GameLogic gameLogic = drawingPanel.getGameLogic();
            gameLogic.reset();
            for(Line line : loadedRedLines) {
                gameLogic.addLine(line.getStart(), line.getEnd(), 0); // RED
            }
            for(Line line : loadedBlueLines) {
                gameLogic.addLine(line.getStart(), line.getEnd(), 1); // BLUE
            }

            // actualizam scorurile jucatorilor
            scoreboard.updateRedScore(redScore);
            scoreboard.updateBlueScore(blueScore);

            // setam AI
            frame.setSecondPlayerAI(isAI);
            frame.setAiDifficulty(difficulty);
            if(isAI){
                frame.setGameAI(new GameAI(difficulty, drawingPanel));
            }

            drawingPanel.setCurrentPlayer(currentPlayer);

            gameSound.play("sounds/save_load.wav");
            JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Error while loading game: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
