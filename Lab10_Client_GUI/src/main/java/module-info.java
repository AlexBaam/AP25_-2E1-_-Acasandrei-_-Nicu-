module org.example.lab10_client_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lab10_client_gui to javafx.fxml;
    exports org.example.lab10_client_gui;
}