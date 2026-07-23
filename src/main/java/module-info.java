module com.example.miniproyecto_batalla_naval {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.miniproyecto_batalla_naval to javafx.fxml;
    opens com.example.miniproyecto_batalla_naval.controller to javafx.fxml;
    opens com.example.miniproyecto_batalla_naval.model to javafx.fxml;

    exports com.example.miniproyecto_batalla_naval;
}