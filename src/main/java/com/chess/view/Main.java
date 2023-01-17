package com.chess.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new ChessGameScene();
        stage.setTitle("Chess");
        stage.setScene(scene);
        setUpStage(stage);
        stage.show();
    }

    public void setUpStage(Stage stage) {
        stage.setMaxHeight(600);
        stage.setMaxWidth(600);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
    }

    public static void main(String[] args) {
        launch();
    }
}