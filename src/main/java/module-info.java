module com.example.miniproyecto_batalla_naval {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.junit.jupiter.api;
    requires org.apiguardian.api;

    opens com.example.miniproyecto_batalla_naval to javafx.fxml;
    opens com.example.miniproyecto_batalla_naval.controller to javafx.fxml;
    opens com.example.miniproyecto_batalla_naval.model to javafx.fxml;

    exports com.example.miniproyecto_batalla_naval;
}