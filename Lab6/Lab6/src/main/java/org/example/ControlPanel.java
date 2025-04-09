//Exit, Export, Save, Load

package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

import java.util.List; // Corect pentru a folosi List din java.util
import java.awt.Point; // Pentru clasa Point din AWT


public class ControlPanel extends JPanel {
    private final MainFrame frame;
    JButton exitButton = new JButton("Exit");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JButton exportButton = new JButton("Export");

    public ControlPanel(MainFrame frame) {
        this.frame = frame;
        init();
    }

    private void init(){
        //change the default layout manager (just for fun)
        setLayout(new GridLayout(1, 4));
        add(loadButton);
        add(saveButton);
        add(exportButton);
        add(exitButton);

        //configure listeners for all buttons
        exitButton.addActionListener(this::exitGame); // Actiune buton
        exportButton.addActionListener(this::exportImage);
        saveButton.addActionListener(this::saveGame);
        loadButton.addActionListener(this::loadGame);
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
            out.writeObject(drawingPanel.getLines());

            // salvam scorurile + logica jocului
            out.writeObject(frame.getScoreboard());
            out.writeObject(drawingPanel.getGameLogic());

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
            List<Line> loadedLines = (List<Line>) in.readObject();

            // incarcam scorurile si logica jocului
            Scoreboard loadedScoreboard = (Scoreboard) in.readObject();
            GameLogic loadedGameLogic = (GameLogic) in.readObject();

            // actualizam DrawingPanel cu punctele si liniile incarcate
            drawingPanel.fillWithDots(loadedDots);

            // adauga liniile in DrawingPanel
            drawingPanel.setLines(loadedLines);  /// ne asiguram ca liniile sunt incarcate !!

            // actualizeaza scorurile
            scoreboard.updateRedScore(loadedGameLogic.getRedScore());
            scoreboard.updateBlueScore(loadedGameLogic.getBlueScore());

            JOptionPane.showMessageDialog(frame, "Game loaded successfully!", "Load", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(frame, "Error while loading game: " + ex.getMessage(), "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
