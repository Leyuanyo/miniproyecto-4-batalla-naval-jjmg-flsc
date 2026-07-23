package com.example.miniproyecto_batalla_naval;

import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

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
