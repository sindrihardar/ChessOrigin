package com.chess.view;

import com.chess.presenter.GameMediator;
import com.chess.presenter.MediatorConstructionFlags;
import com.chess.view.scenes.ChessBoardScene;
import com.chess.view.scenes.HomeScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int MAX_HEIGHT = 750, MAX_WIDTH = 1050, MIN_HEIGHT = 500, MIN_WIDTH = 700;

    @Override
    public void start(Stage stage) {
        Scene scene = new ChessBoardScene(700.0, 700.0, new GameMediator(MediatorConstructionFlags.TIMED_LOCAL));
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