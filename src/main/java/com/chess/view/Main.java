package com.chess.view;

import com.chess.view.scenes.HomeScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int MAX_HEIGHT = 600, MAX_WIDTH = 600, MIN_HEIGHT = 400, MIN_WIDTH = 400;

    @Override
    public void start(Stage stage) {
        Scene scene = new HomeScene(MIN_WIDTH, MIN_HEIGHT);
        stage.setTitle("Chess");
        stage.setScene(scene);
        setUpStage(stage);
        stage.show();
    }

    public void setUpStage(Stage stage) {
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
    }

    public static void main(String[] args) {
        launch();
    }
}