package com.chess.view.nodes;

import com.chess.presenter.GameMediator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.adapter.JavaBeanDoubleProperty;
import javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GameNode extends Pane {
    private static final double TIME_BOX_SPACE_PERCENTAGE = 0.3;
    private HBox root;
    private BoardNode board;
    private TimerNode whiteTimer, blackTimer;
    private GameMediator gameMediator;

    public GameNode(GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        board = new BoardNode(gameMediator.getBoardPresenter());
        whiteTimer = new TimerNode(gameMediator.getWhiteTimerPresenter());
        blackTimer = new TimerNode(gameMediator.getBlackTimerPresenter());
        build();
    }

    public void build() {
        root = new HBox();
        getChildren().add(root);
        root.minWidthProperty().bind(widthProperty());
        root.maxWidthProperty().bind(widthProperty());
        root.minHeightProperty().bind(heightProperty());
        root.maxHeightProperty().bind(heightProperty());

        // add a pane for the board to live in, add a VBox for the timers
        board.minWidthProperty().bind(widthProperty().multiply(1 - TIME_BOX_SPACE_PERCENTAGE));
        board.maxWidthProperty().bind(widthProperty().multiply(1 - TIME_BOX_SPACE_PERCENTAGE));
        root.getChildren().add(board);

        VBox timerBox = new VBox();
        Pane emptySpace = new Pane();
        root.getChildren().add(timerBox);
        timerBox.getChildren().add(blackTimer);
        timerBox.getChildren().add(emptySpace);
        timerBox.getChildren().add(whiteTimer);
        timerBox.minHeightProperty().bind(heightProperty());
        timerBox.maxHeightProperty().bind(heightProperty());
        timerBox.setAlignment(Pos.CENTER);
        timerBox.paddingProperty().bind(Bindings.createObjectBinding(() -> new Insets(heightProperty().divide(8).doubleValue()), heightProperty().divide(8)));
        timerBox.minWidthProperty().bind(widthProperty().multiply(TIME_BOX_SPACE_PERCENTAGE));
        timerBox.maxWidthProperty().bind(widthProperty().multiply(TIME_BOX_SPACE_PERCENTAGE));

        VBox.setVgrow(emptySpace, Priority.ALWAYS);
        HBox.setHgrow(board, Priority.ALWAYS);
    }
}
