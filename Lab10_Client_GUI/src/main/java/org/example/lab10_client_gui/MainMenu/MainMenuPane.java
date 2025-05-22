package org.example.lab10_client_gui.MainMenu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import org.example.lab10_client_gui.Board.BoardPane;
import org.example.lab10_client_gui.Connect.TCPConnection;

public class MainMenuPane extends VBox {
    private final Button hostBtn   = new Button("Host Game");
    private final Button joinBtn   = new Button("Join Game");
    private final Button settingsBtn = new Button("Settings");
    private final Button exitBtn   = new Button("Exit");

    public MainMenuPane(TCPConnection conn, BoardPane boardPane, javafx.scene.control.Label gameIdFooter) {
        // styling
        setSpacing(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER_LEFT);
        setStyle("-fx-border-color: lightgray; -fx-border-width: 0 1px 0 0;");
        setPrefWidth(150);

        getChildren().addAll(hostBtn, joinBtn, settingsBtn, exitBtn);

        // HOST
        hostBtn.setOnAction(e -> {
            TextInputDialog dlg = new TextInputDialog();
            dlg.setHeaderText("Enter your name to host:");
            dlg.showAndWait().ifPresent(name -> {
                conn.sendCommand("create " + name);
            });
        });

        // JOIN
        joinBtn.setOnAction(e -> {
            TextInputDialog nameDlg = new TextInputDialog();
            nameDlg.setHeaderText("Enter your name to join:");
            nameDlg.showAndWait().ifPresent(name -> {
                TextInputDialog idDlg = new TextInputDialog();
                idDlg.setHeaderText("Enter Game ID:");
                idDlg.showAndWait().ifPresent(id -> {
                    conn.sendCommand("join " + name + " " + id);
                    boardPane.setGameId(id);
                });
            });
        });

        // SETTINGS
        settingsBtn.setOnAction(e ->
                new Alert(Alert.AlertType.INFORMATION, "Settings not implemented.").showAndWait()
        );

        // EXIT
        exitBtn.setOnAction(e -> {
            conn.disconnect();
            Platform.exit();
        });
    }
}
