package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.persistence.GameSerializer;
import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private TextField nicknameField;

    @FXML
    private Button continueButton;

    private final GameSerializer serializer = new GameSerializer();

    @FXML
    public void initialize() {
        continueButton.setDisable(!serializer.hasSavedGame());
    }

    @FXML
    private void handleNewGame() {
        String nickname = nicknameField.getText();
        if (nickname == null || nickname.isBlank()) {
            showAlert("Ingresa un nickname para continuar.");
            return;
        }
        GameStage.goToPlacement(nickname);
    }

    @FXML
    private void handleContinue() {
        GameStage.goToSavedGame();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    @FXML
    private void handleHowToPlay() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Como jugar");
        alert.setHeaderText("Batalla Naval");
        alert.setContentText(
                "Coloca tu flota en el tablero de posicion.\n\n" +
                        "Luego dispara en el tablero principal (el de la maquina).\n\n" +
                        "Agua: pasa el turno.\n" +
                        "Tocado: sigue disparando.\n" +
                        "Hundido: sigue disparando hasta hundir toda la flota enemiga.\n\n" +
                        "Gana quien hunda primero toda la flota del rival."
        );
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        var iconStream = getClass().getResourceAsStream(
                "/com/example/miniproyecto_batalla_naval/images/helpImage.png");
        if (iconStream != null) {
            alertStage.getIcons().add(new Image(iconStream));
        }

        alert.showAndWait();
    }
}
