module org.example.lab10_client_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lab10_client_gui to javafx.fxml;
    exports org.example.lab10_client_gui;
    exports org.example.lab10_client_gui.Board;
    opens org.example.lab10_client_gui.Board to javafx.fxml;
    exports org.example.lab10_client_gui.MainMenu;
    opens org.example.lab10_client_gui.MainMenu to javafx.fxml;
    exports org.example.lab10_client_gui.Connect;
    opens org.example.lab10_client_gui.Connect to javafx.fxml;
}