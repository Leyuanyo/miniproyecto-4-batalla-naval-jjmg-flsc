package com.example.miniproyecto_batalla_naval.view;

import com.example.miniproyecto_batalla_naval.controller.GameController;
import com.example.miniproyecto_batalla_naval.controller.PlacementController;
import com.example.miniproyecto_batalla_naval.model.GameModel;
import com.example.miniproyecto_batalla_naval.persistence.GameSerializer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class responsible for managing scene navigation
 * throughout the Battleship application.
 *
 * This class loads the corresponding FXML views, initializes
 * their controllers when necessary, and replaces the current
 * scene displayed on the primary application stage.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public final class GameStage {

    /** Base path where all FXML view files are located. */
    private static final String BASE_PATH = "/com/example/miniproyecto_batalla_naval/";

    /** Primary stage used by the application. */
    private static Stage primaryStage;

    /**
     * Private constructor to prevent instantiation.
     * This class only provides static utility methods.
     */
    private GameStage() {
    }

    /**
     * Initializes the primary stage that will be used
     * for all scene transitions.
     *
     * @param stage the application's primary stage
     */
    public static void init(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Loads and displays the application's main menu.
     */
    public static void goToMainMenu() {
        Parent root = load("main-menu-view.fxml", (Object controller) -> { });
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Loads the ship placement screen and initializes it
     * with the specified player's nickname.
     *
     * @param nickname the nickname entered by the player
     */
    public static void goToPlacement(String nickname) {
        Parent root = load("placement-view.fxml", (PlacementController controller) ->
                controller.startNewGame(nickname));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Loads the previously saved game and displays
     * the gameplay screen.
     */
    public static void goToSavedGame() {
        GameSerializer serializer = new GameSerializer();
        GameModel savedModel = serializer.load();
        Parent root = load("game-view.fxml", (GameController controller) ->
                controller.resumeGame(savedModel));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Loads the gameplay screen and starts a new match
     * using the provided game model.
     *
     * @param model the game model representing the new match
     */
    public static void goToGame(GameModel model) {
        Parent root = load("game-view.fxml", (GameController controller) ->
                controller.startGame(model));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Loads an FXML file, obtains its controller, executes
     * an initialization callback, and returns the root node.
     *
     * @param <T> the controller type
     * @param fxmlFile the FXML file to load
     * @param onLoaded callback executed after the controller
     *                 has been created
     * @return the root node of the loaded FXML hierarchy
     * @throws RuntimeException if the FXML file cannot be loaded
     */
    @SuppressWarnings("unchecked")
    private static <T> Parent load(String fxmlFile, Consumer<T> onLoaded) {
        try {
            FXMLLoader loader = new FXMLLoader(GameStage.class.getResource(BASE_PATH + fxmlFile));
            Parent root = loader.load();
            T controller = loader.getController();
            onLoaded.accept(controller);
            return root;
        } catch (IOException e) {
            throw new RuntimeException("Could not load " + fxmlFile, e);
        }
    }
}