package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameHistory extends JDialog {
    private JTextArea historyTextArea;

    public GameHistory(JFrame frame, List<String> history) {
        super(frame, "Game History", true);
        setSize(400, 500);
        setLocationRelativeTo(frame);
        initUI(history);
        setVisible(true);
    }

    private void initUI(List<String> history) {
        setLayout(new BorderLayout());

        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Adaugam fiecare intrare din lista in istoric
        for (String entry : history) {
            historyTextArea.append(entry + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(historyTextArea);
        add(scrollPane, BorderLayout.CENTER);
    }
}
