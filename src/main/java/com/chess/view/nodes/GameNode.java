package com.chess.view.nodes;

import com.chess.presenter.GameMediator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class GameNode extends Pane {
    private static final double TIME_BOX_SPACE_PERCENTAGE = 0.4;
    private VBox infoBox;
    private Pane root;
    private BoardNode board;
    private TimerNode whiteTimer, blackTimer;
    private NotationNode notationNode;
    private GameMediator gameMediator;

    public GameNode(GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        board = new BoardNode(gameMediator.getBoardPresenter());
        build();
    }

    public void build() {
        root = new Pane();
        getChildren().add(root);
        root.minWidthProperty().bind(widthProperty());
        root.maxWidthProperty().bind(widthProperty());
        root.minHeightProperty().bind(heightProperty());
        root.maxHeightProperty().bind(heightProperty());

        // add a pane for the board to live in, add a VBox for the timers
        board.minWidthProperty().bind(widthProperty().multiply(1 - TIME_BOX_SPACE_PERCENTAGE));
        board.maxWidthProperty().bind(widthProperty().multiply(1 - TIME_BOX_SPACE_PERCENTAGE));
        root.getChildren().add(board);

    }
}
