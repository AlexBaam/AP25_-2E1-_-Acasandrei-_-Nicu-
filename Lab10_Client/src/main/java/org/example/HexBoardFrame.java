package org.example;

import javax.swing.*;

public class HexBoardFrame extends JFrame {
    private final HexBoardPanel boardPanel;

    public HexBoardFrame(int size) {
        setTitle("Hex Board Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        boardPanel = new HexBoardPanel(size);
        add(boardPanel);
        pack();
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    public void updateCell(int row, int col, String colorName) {
        boardPanel.updateCell(row, col, colorName);
    }

    public void setGameOver(String winnerMessage) {
        JOptionPane.showMessageDialog(this, winnerMessage, "Game Over", JOptionPane.INFORMATION_MESSAGE);
        boardPanel.setEnabled(false); // Optional: if board supports disabling
    }

}
