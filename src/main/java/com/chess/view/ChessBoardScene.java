package com.chess.view;

import com.chess.presenter.ChessBoardPresenter;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ChessBoardScene extends Scene implements Observer {
    private StackPane boardContainer;
    private TilePane board;
    private ChessBoardPresenter presenter;

    public ChessBoardScene() {
        super(new Pane());
        Pane root = (Pane) getRoot();
        presenter = new ChessBoardPresenter();
        setUpBoard(root);
        addSpacesToBoard();
        root.getChildren().add(board);
    }

    public void setUpBoard(Pane root) {
        board = new TilePane();
        board.setPrefColumns(8);
        board.prefTileHeightProperty().bind(Bindings.min(root.heightProperty(), root.widthProperty()).divide(8));
        board.prefTileWidthProperty().bind(board.prefTileHeightProperty());
        board.layoutYProperty().bind(root.heightProperty().divide(2).subtract(board.prefTileHeightProperty().multiply(4)));
        board.layoutXProperty().bind(root.widthProperty().divide(2).subtract(board.prefTileWidthProperty().multiply(4)));
    }

    public void addSpacesToBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessBoardTileNode spaceNode = new ChessBoardTileNode(i, j, presenter.getSpacePresenter(i, j), presenter);
                board.getChildren().add(spaceNode);
                spaceNode.minHeightProperty().bind(board.prefTileHeightProperty());
                spaceNode.minWidthProperty().bind(board.prefTileWidthProperty());
            }
        }
    }

    @Override
    public void update() {

    }
}
