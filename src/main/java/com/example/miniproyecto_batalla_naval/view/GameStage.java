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

public final class GameStage {

    private static final String BASE_PATH = "/com/example/miniproyecto_batalla_naval/";
    private static Stage primaryStage;

    private GameStage() {
    }

    public static void init(Stage stage) {
        primaryStage = stage;
    }

    public static void goToMainMenu() {
        Parent root = load("main-menu-view.fxml", (Object controller) -> { });
        primaryStage.setScene(new Scene(root));
    }

    public static void goToPlacement(String nickname) {
        Parent root = load("placement-view.fxml", (PlacementController controller) ->
                controller.startNewGame(nickname));
        primaryStage.setScene(new Scene(root));
    }

    public static void goToSavedGame() {
        GameSerializer serializer = new GameSerializer();
        GameModel savedModel = serializer.load();
        Parent root = load("game-view.fxml", (GameController controller) ->
                controller.resumeGame(savedModel));
        primaryStage.setScene(new Scene(root));
    }

    public static void goToGame(GameModel model) {
        Parent root = load("game-view.fxml", (GameController controller) ->
                controller.startGame(model));
        primaryStage.setScene(new Scene(root));
    }

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
