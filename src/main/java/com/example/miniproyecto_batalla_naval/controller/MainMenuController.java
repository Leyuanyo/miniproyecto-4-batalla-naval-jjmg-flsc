package com.example.miniproyecto_batalla_naval.controller;

import com.example.miniproyecto_batalla_naval.persistence.GameSerializer;
import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Controller for the main menu of the Battleship game.
 * Manages the initial interactions with the user, including
 * starting a new game, continuing a previously saved game,
 * and displaying the game instructions.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class MainMenuController {

    /** Text field where the player enters the desired nickname. */
    @FXML
    private TextField nicknameField;

    /** Button used to continue a previously saved game. */
    @FXML
    private Button continueButton;

    /** Handles game persistence operations. */
    private final GameSerializer serializer = new GameSerializer();

    /**
     * Initializes the controller after the FXML components have been loaded.
     * Enables or disables the continue button depending on whether
     * a saved game is available.
     */
    @FXML
    public void initialize() {
        continueButton.setDisable(!serializer.hasSavedGame());
    }

    /**
     * Starts a new Battleship game.
     * Validates that the player has entered a nickname before
     * navigating to the ship placement screen.
     */
    @FXML
    private void handleNewGame() {
        String nickname = nicknameField.getText();
        if (nickname == null || nickname.isBlank()) {
            showAlert("Ingresa un nickname para continuar.");
            return;
        }
        GameStage.goToPlacement(nickname);
    }

    /**
     * Loads the previously saved game and navigates to the
     * corresponding gameplay screen.
     */
    @FXML
    private void handleContinue() {
        GameStage.goToSavedGame();
    }

    /**
     * Displays a warning dialog containing the specified message.
     *
     * @param message the warning message to be displayed
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    /**
     * Displays the game instructions in an informational dialog.
     * The dialog explains the objective of the game, the meaning
     * of each shot result, and the victory condition.
     */
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