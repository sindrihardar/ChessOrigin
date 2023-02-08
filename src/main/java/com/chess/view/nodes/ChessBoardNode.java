package com.chess.view.nodes;

import com.chess.model.util.Colors;
import com.chess.presenter.ChessBoardPresenter;
import com.chess.view.Observer;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ChessBoardNode extends StackPane implements Observer {
    private static final int BOARD_PADDING = 20;
    private final String MESSAGE_FONT = "Impact";
    private TilePane board;
    private StackPane messageNode;
    private static final Color MESSAGE_COLOR = new Color(0.8275, 0.3176, 0.6, 0.8);
    private ChessBoardPresenter presenter;

    public ChessBoardNode(Pane parent, ChessBoardPresenter presenter) {
        parent.getChildren().add(this);
        this.presenter = presenter;
        this.presenter.attach(this);
        build();
    }

    public void build() {
        Pane parent = (Pane) getParent();
        minHeightProperty().bind(Bindings.min(parent.widthProperty(), parent.heightProperty()).subtract(2 * BOARD_PADDING));
        maxHeightProperty().bind(minHeightProperty());
        minWidthProperty().bind(minHeightProperty());
        maxWidthProperty().bind(minWidthProperty());
        layoutXProperty().bind(Bindings.max(BOARD_PADDING, parent.widthProperty().divide(2).subtract(widthProperty().divide(2))));
        layoutYProperty().bind(parent.heightProperty().divide(2).subtract(heightProperty().divide(2)));
        board = new TilePane();
        getChildren().add(board);
        buildBoard();
    }

    private void buildBoard() {
        StackPane parent = (StackPane) board.getParent();
        board.setPrefColumns(8);
        board.setPrefRows(8);
        board.minHeightProperty().bind(parent.maxWidthProperty());
        board.minWidthProperty().bind(parent.maxWidthProperty());
        board.maxHeightProperty().bind(parent.maxWidthProperty());
        board.maxWidthProperty().bind(parent.maxWidthProperty());
        board.prefTileHeightProperty().bind(board.heightProperty().subtract(board.getPrefColumns()).divide(8));
        board.prefTileWidthProperty().bind(board.prefTileHeightProperty());
        addTilesToBoard(board);
    }

    private void addTilesToBoard(TilePane board) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buildTile(i, j, presenter, board);
    }

    private void buildTile(int row, int col, ChessBoardPresenter presenter, TilePane board) {
        ChessBoardTileNode tileNode = new ChessBoardTileNode(row, col, presenter.getChessBoardTilePresenter(row, col), presenter);
        tileNode.minHeightProperty().bind(board.prefTileHeightProperty());
        tileNode.minWidthProperty().bind(board.prefTileHeightProperty());
        tileNode.maxHeightProperty().bind(board.prefTileHeightProperty());
        tileNode.maxWidthProperty().bind(board.prefTileHeightProperty());
        board.getChildren().add(tileNode);
    }

    private void buildMessageNode(StackPane parent, String message) {
        messageNode = new StackPane();
        Rectangle background = new Rectangle();

        // build background for the message
        background.setFill(MESSAGE_COLOR);
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));

        // build the message itself
        Label label = new Label(message);
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), "; -fx-font-family: " + MESSAGE_FONT + ";"));
        messageNode.getChildren().add(background);
        messageNode.getChildren().add(label);
        messageNode.setOpacity(0.0);
    }

    public void movePieceAnimation(int row1, int col1, int row2, int col2) {
        StackPane node1 = (StackPane) board.getChildren().get(row1 * 8 + col1), node2 = (StackPane) board.getChildren().get(row2 * 8 + col2);
        double startX = node1.getLayoutX() + node1.getWidth() / 2, startY = node1.getLayoutY() + node1.getHeight() / 2;
        double endX = node2.getLayoutX() + node2.getWidth() / 2, endY = node2.getLayoutY() + node2.getHeight() / 2;
        startX -= node1.getLayoutX();
        startY -= node1.getLayoutY();
        endX -= node1.getLayoutX();
        endY -= node1.getLayoutY();

        // create the path to be traveled (where is the node going)
        Path path = new Path();
        MoveTo endPoint = new MoveTo(startX, startY);
        path.getElements().add(endPoint);
        path.getElements().add(new LineTo(endX, endY));

        // create the path transition (where is the node starting and how long should it take)
        PathTransition animation = new PathTransition();
        animation.setDuration(Duration.millis(300));
        for (Node node : node1.getChildren())
            if (node instanceof ImageView)
                animation.setNode(node);
        node1.setViewOrder(-1);
        animation.getNode().setViewOrder(-1);
        animation.setPath(path);
        animation.setOnFinished(actionEvent -> {
            presenter.executeQueuedMovement();
            node1.setViewOrder(0);
            animation.getNode().setViewOrder(0);
        });

        animation.play();
    }

    @Override
    public void update() {
        if (presenter.isGameInStalemate()) {
            buildMessageNode(this, "Stalemate!");
        } else if (presenter.isPlayerInCheckmate() && presenter.getCurrentPlayersColor() == Colors.BLACK) {
            buildMessageNode(this, "White won!");
        } else if (presenter.isPlayerInCheckmate() && presenter.getCurrentPlayersColor() == Colors.WHITE) {
            buildMessageNode(this, "Black won!");
        } else {
            return; // if nothing was updated, don't play the animation
        }

        // create the fade-in animation for the message
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), messageNode);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setOnFinished(actionEvent -> messageNode.setOpacity(1.0));

        fadeTransition.play();

        getChildren().add(messageNode);
    }
}
