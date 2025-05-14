package org.example.lab10_client_gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainFrame extends Application {

    private TextField addressField;
    private TextField portField;
    private Button connectButton;
    private Label statusLabel;
    private Pane boardPane;

    private TextField nameField;
    private TextField gameIdField;
    private Button createButton;
    private Button joinButton;

    private TCPConnection conn;

    @Override
    public void start(Stage primaryStage) {
        addressField = new TextField("127.0.0.1");
        portField = new TextField("5000");
        connectButton = new Button("Connect");
        statusLabel = new Label("Not connected");

        HBox topBar = new HBox(8,
                new Label("Server:"), addressField,
                new Label("Port:"), portField,
                connectButton,
                statusLabel
        );
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));

        boardPane = new Pane();
        boardPane.setPrefSize(600, 600);
        boardPane.setStyle("-fx-background-color: #eef;");

        nameField    = new TextField();
        nameField.setPromptText("Your name");
        gameIdField  = new TextField();
        gameIdField.setPromptText("Game ID");
        createButton = new Button("Create Game");
        createButton.setDisable(true);
        joinButton   = new Button("Join Game");
        joinButton.setDisable(true);

        HBox bottomBar = new HBox(8,
                new Label("Name:"),    nameField,
                createButton,
                new Label("Game ID:"), gameIdField,
                joinButton
        );
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(boardPane);
        root.setBottom(bottomBar);

        connectButton.setOnAction(e -> {
            connectButton.setDisable(true);
            statusLabel.setText("Connecting...");

            conn = new TCPConnection();
            conn.setOnMessage(this::handleServerMessage);
            conn.setOnError(ex -> Platform.runLater(() -> {
                statusLabel.setText("Connection failed: " + ex.getMessage());
                connectButton.setDisable(false);
            }));

            conn.connect(addressField.getText(), Integer.parseInt(portField.getText()));
        });

        createButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                conn.sendCommand("create " + name);
            }
        });

        joinButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String id   = gameIdField.getText().trim();
            if (!name.isEmpty() && !id.isEmpty()) {
                conn.sendCommand("join " + name + " " + id);
            }
        });

        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Hex Game Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleServerMessage(String msg) {
        statusLabel.setText(msg);
        // TODO: parse board dumps and update boardPane
        // TODO: update clocks and other UI elements
    }

    public static void main(String[] args) {
        launch(args);
    }
}