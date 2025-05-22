package org.example.lab10_client_gui.MainMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.lab10_client_gui.Board.BoardPane;
import org.example.lab10_client_gui.Board.Cell;
import org.example.lab10_client_gui.Board.HexClickHandle;
import org.example.lab10_client_gui.Connect.ClientSidePlayer;
import org.example.lab10_client_gui.Connect.TCPConnection;

public class MainFrame extends Application {
    // Keep references for use across methods
    private ClientSidePlayer localPlayer;
    private HexClickHandle clickHandler;
    private TCPConnection conn;
    private BoardPane boardPane;
    private Label gameIdFooter;
    private Label currentTurnLabel;
    private Label playerInfoLabel;

    @Override
    public void start(Stage primaryStage) {
        // 1) TCP setup
        conn = new TCPConnection();
        conn.setOnError(ex ->
                Platform.runLater(() ->
                        new Alert(Alert.AlertType.ERROR, "Connection failed: " + ex.getMessage())
                                .showAndWait()
                )
        );
        conn.connect("127.0.0.1", 5000);

        // 2) Board & footer
        boardPane = new BoardPane();
        boardPane.setVisible(false);
        gameIdFooter = new Label("Game ID: -----");
        gameIdFooter.setPadding(new Insets(8));
        gameIdFooter.setStyle("-fx-font-size: 14px;");

        currentTurnLabel = new Label("Current Turn: ---");
        currentTurnLabel.setPadding(new Insets(8));
        currentTurnLabel.setStyle("-fx-font-size: 14px;");

        playerInfoLabel = new Label("Player: ---");
        playerInfoLabel.setPadding(new Insets(8));
        playerInfoLabel.setStyle("-fx-font-size: 14px;");

        // 3) Menu (self-contained)
        MainMenuPane menuPane = new MainMenuPane(conn, boardPane, gameIdFooter);
        VBox bottomBox = new VBox(playerInfoLabel, currentTurnLabel);

        // 4) Layout
        BorderPane root = new BorderPane();
        root.setLeft(menuPane);
        root.setCenter(boardPane);
        root.setBottom(gameIdFooter);
        root.setRight(bottomBox);

        // 5) Setup local player when game is created or joined
        conn.setOnMessage(msg -> {
            System.out.println("[Server message] " + msg); // <-- Terminal debug print for all messages

            Platform.runLater(() -> {
                if (msg.startsWith("Game created")) {
                    String id = msg.split("ID: ")[1].trim();
                    System.out.println("Game created with ID: " + id);
                    gameIdFooter.setText("Game ID: " + id);
                    localPlayer = new ClientSidePlayer("HostPlayer", Cell.RED);
                    playerInfoLabel.setText("Player: " + localPlayer.getName());
                    currentTurnLabel.setText("Current Turn: " + localPlayer.getName() + "(RED)");
                    boardPane.setGameId(id);
                    boardPane.setVisible(true);
                    setupClickHandler();
                }
                else if (msg.startsWith("Game joined")) {
                    System.out.println("Game joined");
                    playerInfoLabel.setText("Player: JoinedPlayer (BLUE)");
                    currentTurnLabel.setText("Current Turn: HostPlayer (RED)"); // Host starts
                    boardPane.setVisible(true);
                    gameIdFooter.setText("Game ID: " + boardPane.getGameId());
                    localPlayer = new ClientSidePlayer("JoinedPlayer", Cell.BLUE);
                    setupClickHandler();
                }
                else if (msg.startsWith("Move accepted")) {
                    System.out.println("Move accepted");
                    String lowerMsg = msg.toLowerCase();
                    if (lowerMsg.contains("red")) {
                        clickHandler.setCurrentTurn(Cell.RED);
                        System.out.println("Set turn to RED");
                        currentTurnLabel.setText("Current Turn: HostPlayer (RED)");
                    } else if (lowerMsg.contains("blue")) {
                        clickHandler.setCurrentTurn(Cell.BLUE);
                        System.out.println("Set turn to BLUE");
                        currentTurnLabel.setText("Current Turn: JoinedPlayer (BLUE)");
                    } else {
                        System.out.println("Warning: Unknown turn color in server message");
                    }
                }
                else if (msg.startsWith("Game over")) {
                    System.out.println("Game over");
                    clickHandler.setCurrentTurn(null);
                    new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
                }
                else if (msg.startsWith("Invalid move")) {
                    System.out.println("Invalid move received");
                    new Alert(Alert.AlertType.WARNING, msg).showAndWait();
                }
            });
        });

        // 6) Show
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hex Game Client");
        primaryStage.setMaximized(true);

        primaryStage.setOnCloseRequest(evt -> {
            conn.disconnect();    // close socket
            Platform.exit();      // stop JavaFX
        });

        primaryStage.show();
    }

    // Create HexClickHandle and wire the move submission callback to send moves to the server
    private void setupClickHandler() {
        if (localPlayer == null || boardPane == null) return;
        clickHandler = new HexClickHandle(boardPane.getModel(), boardPane, localPlayer);
        clickHandler.setCurrentTurn(Cell.RED); // default first turn is RED
        clickHandler.setMoveSubmitCallback((row, col) -> {
            String cmd = "move " + row + " " + col;
            System.out.println("Sending move command: " + cmd);
            conn.sendCommand(cmd);
        });
    }
}