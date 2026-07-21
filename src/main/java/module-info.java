module com.example.miniproyecto_batalla_naval {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyecto_batalla_naval to javafx.fxml;
    exports com.example.miniproyecto_batalla_naval;
}