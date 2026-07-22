package com.example.miniproyecto_batalla_naval;

import com.example.miniproyecto_batalla_naval.view.GameStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        GameStage.init(stage);
        GameStage.goToMainMenu();
        stage.setTitle("Batalla Naval");
        stage.show();
    }
}
