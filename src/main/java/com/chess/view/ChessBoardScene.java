package com.chess.view;

import com.chess.presenter.ChessBoardPresenter;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ChessBoardScene extends Scene implements Observer {
    private static final int BOARD_PADDING = 20;
    private StackPane boardContainer, stalemateMessageNode, whiteWonMessageNode, blackWonMessageNode;
    private TilePane board;
    private ChessBoardPresenter presenter;
    private Color messageColor = new Color(0.8275, 0.3176, 0.6, 0.5);

    public ChessBoardScene() {
        super(new Pane());
        Pane root = (Pane) getRoot();
        presenter = new ChessBoardPresenter();
        buildBoardContainer(root);
        setUpBoard(boardContainer);
        addSpacesToBoard();
        boardContainer.getChildren().add(board);
        root.getChildren().add(boardContainer);
        buildStalemateMessageNode(boardContainer);
        buildWhiteWonMessageNode(boardContainer);
        buildBlackWonMessageNode(boardContainer);
        presenter.attach(this);
    }

    public void buildBoardContainer(Pane root) {
        boardContainer = new StackPane();
        boardContainer.minHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()).subtract(2 * BOARD_PADDING));
        boardContainer.minWidthProperty().bind(boardContainer.minHeightProperty());
        boardContainer.maxWidthProperty().bind(boardContainer.minWidthProperty());
        boardContainer.maxHeightProperty().bind(boardContainer.minHeightProperty());
        boardContainer.layoutXProperty().bind(Bindings.max(BOARD_PADDING,widthProperty().divide(2).subtract(boardContainer.widthProperty().divide(2))));
        boardContainer.layoutYProperty().bind(root.heightProperty().divide(2).subtract(boardContainer.heightProperty().divide(2)));
    }

    public void buildStalemateMessageNode(StackPane parent) {
        stalemateMessageNode = new StackPane();
        Rectangle background = new Rectangle();
        background.setFill(new Color(0.8275, 0.3176, 0.6, 0.5));
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));
        Label label = new Label("Stalemate!");
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), ";", "-fx-font-family: Impact;"));
        stalemateMessageNode.getChildren().add(background);
        stalemateMessageNode.getChildren().add(label);
    }

    public void buildWhiteWonMessageNode(StackPane parent) {
        whiteWonMessageNode = new StackPane();
        Rectangle background = new Rectangle();
        background.setFill(new Color(0.8275, 0.3176, 0.6, 0.5));
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));
        Label label = new Label("White won!");
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), ";", "-fx-font-family: Impact;"));
        whiteWonMessageNode.getChildren().add(background);
        whiteWonMessageNode.getChildren().add(label);
    }

    public void buildBlackWonMessageNode(StackPane parent) {
        blackWonMessageNode = new StackPane();
        Rectangle background = new Rectangle();
        background.setFill(new Color(0.8275, 0.3176, 0.6, 0.5));
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));
        Label label = new Label("Black won!");
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), ";", "-fx-font-family: Impact;"));
        blackWonMessageNode.getChildren().add(background);
        blackWonMessageNode.getChildren().add(label);
    }

    public void setUpBoard(StackPane root) {
        board = new TilePane();
        board.setPrefColumns(8);
        board.setPrefRows(8);
        board.minWidthProperty().bind(root.widthProperty());
        board.minHeightProperty().bind(root.heightProperty());
        board.maxWidthProperty().bind(root.widthProperty());
        board.maxHeightProperty().bind(root.heightProperty());
        board.prefTileHeightProperty().bind(board.heightProperty().subtract(board.getPrefColumns()).divide(8));
        board.prefTileWidthProperty().bind(board.prefTileHeightProperty());
    }

    public void addSpacesToBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessBoardTileNode spaceNode = new ChessBoardTileNode(i, j, presenter.getSpacePresenter(i, j), presenter);
                board.getChildren().add(spaceNode);
                spaceNode.minHeightProperty().bind(board.prefTileHeightProperty());
                spaceNode.minWidthProperty().bind(board.prefTileWidthProperty());
                spaceNode.maxHeightProperty().bind(board.prefTileHeightProperty());
                spaceNode.maxWidthProperty().bind(board.prefTileWidthProperty());
            }
        }
    }

    @Override
    public void update() {
        if (presenter.isGameInStalemate()) {
            if (!boardContainer.getChildren().contains(stalemateMessageNode))
                boardContainer.getChildren().add(stalemateMessageNode);
        } else if (presenter.didWhiteWin()) {
            if (boardContainer.getChildren().contains(blackWonMessageNode))
                boardContainer.getChildren().remove(blackWonMessageNode);
            if (!boardContainer.getChildren().contains(whiteWonMessageNode))
                boardContainer.getChildren().add(whiteWonMessageNode);
        } else if (!presenter.didWhiteWin() && presenter.isPlayerInCheckmate()) {
            if (!boardContainer.getChildren().contains(blackWonMessageNode))
                boardContainer.getChildren().add(blackWonMessageNode);
        }
    }
}
