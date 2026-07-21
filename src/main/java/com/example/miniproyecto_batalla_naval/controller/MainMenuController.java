package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.persistence.GameSerializer;
import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
}
