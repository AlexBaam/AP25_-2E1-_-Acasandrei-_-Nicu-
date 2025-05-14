package org.example.lab10_client_gui;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;


public class TCPConnection {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Consumer<String> messageHandler;
    private Consumer<IOException> errorHandler;

    public void setOnMessage(Consumer<String> handler) {
        this.messageHandler = handler;
    }

    public void setOnError(Consumer<IOException> handler) {
        this.errorHandler = handler;
    }

    public void connect(String host, int port) {
        new Thread(() -> {
            try {
                socket = new Socket(host, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    String msg = line.strip();
                    if (msg.isEmpty()) continue;
                    if (messageHandler != null) {
                        Platform.runLater(() -> messageHandler.accept(msg));
                    }
                }
            } catch (IOException ex) {
                if (errorHandler != null) {
                    Platform.runLater(() -> errorHandler.accept(ex));
                }
            }
        }, "Connection-Thread").start();
    }

    public void sendCommand(String cmd) {
        if (out != null) {
            out.println(cmd);
        }
    }

    public void disconnect() {
        try {
            if (socket != null) socket.close();
        } catch (IOException ignored) {
        }
    }
}
