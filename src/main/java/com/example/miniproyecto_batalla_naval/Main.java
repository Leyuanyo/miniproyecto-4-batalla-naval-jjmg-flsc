package com.example.miniproyecto_batalla_naval;

import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main entry point of the Battleship application.
 *
 * This class extends {@link Application} and is responsible
 * for initializing the primary application stage, configuring
 * its appearance, and displaying the main menu when the
 * application starts.
 *
 * @author Juan José Morera Gómez
 * @author Frank Leonardo Silva Castillo
 * @version 1.0
 * @since 1.0
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * Initializes the primary stage, loads the main menu,
     * configures the window title and icon, disables resizing,
     * and displays the application window.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        GameStage.init(stage);
        GameStage.goToMainMenu();
        stage.setTitle("Batalla Naval");
        stage.getIcons().add(new Image(getClass().getResourceAsStream(
                "/com/example/miniproyecto_batalla_naval/images/gameIcon.png")));
        stage.setResizable(false);
        stage.show();
    }
}