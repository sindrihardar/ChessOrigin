package com.chess.view.nodes;

import com.chess.presenter.AITimedChessBoardPresenter;
import com.chess.presenter.ChessBoardPresenter;
import com.chess.presenter.StandardTimedChessBoardPresenter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FullChessGameNode extends HBox {
    private ChessBoardNode board;
    private TimerNode whiteTimer, blackTimer;
    private ChessBoardPresenter gamePresenter;

    public FullChessGameNode(Pane parent, StandardTimedChessBoardPresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
        bindToParentsSize(parent);
        whiteTimer = new TimerNode(gamePresenter.getWhiteTimer());
        blackTimer = new TimerNode(gamePresenter.getBlackTimer());
        build();
    }

    public FullChessGameNode(Pane parent, AITimedChessBoardPresenter gamePresenter) {
        this.gamePresenter = gamePresenter;
        bindToParentsSize(parent);
        whiteTimer = new TimerNode(gamePresenter.getWhiteTimer());
        blackTimer = new TimerNode(gamePresenter.getBlackTimer());
        build();
    }

    public void bindToParentsSize(Pane parent) {
        minWidthProperty().bind(parent.widthProperty());
        maxWidthProperty().bind(parent.widthProperty());
        minHeightProperty().bind(parent.heightProperty());
        maxHeightProperty().bind(parent.heightProperty());
    }

    public void build() {
        // add a pane for the board to live in, add a VBox for the timers
        Pane boardPane = new Pane();
        boardPane.minWidthProperty().bind(widthProperty().multiply(2.0 / 3));
        boardPane.maxWidthProperty().bind(widthProperty().multiply(2.0 / 3));
        board = new ChessBoardNode(boardPane, gamePresenter);
        getChildren().add(boardPane);
        VBox timerBox = new VBox();
        getChildren().add(timerBox);
        timerBox.getChildren().add(blackTimer);
        timerBox.getChildren().add(whiteTimer);
        timerBox.minHeightProperty().bind(heightProperty());
        timerBox.maxHeightProperty().bind(heightProperty());
        timerBox.setAlignment(Pos.CENTER);
        whiteTimer.setPadding(new Insets(10, 10, 10, 10));
        blackTimer.setPadding(new Insets(10, 10, 10, 10));
        timerBox.minWidthProperty().bind(widthProperty().multiply(1.0 / 3));
        timerBox.maxWidthProperty().bind(widthProperty().multiply(1.0 / 3));

        HBox.setHgrow(boardPane, Priority.ALWAYS);
    }
}
