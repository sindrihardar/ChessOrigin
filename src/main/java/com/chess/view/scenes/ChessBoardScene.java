package com.chess.view.scenes;

import com.chess.presenter.GameMediator;
import com.chess.view.nodes.GameNode;
import com.chess.view.nodes.TopBarNode;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import static javafx.geometry.HPos.*;
import static javafx.geometry.VPos.CENTER;

public class ChessBoardScene extends Scene {
    private Pane root;
    private TopBarNode topBar;
    private Pane game;

    public ChessBoardScene(double width, double height, GameMediator gameMediator) {
        super(new Pane(), width, height);
        initializeComponents();
        constructSceneGraph(gameMediator);
        buildComponents();
    }



    private void initializeComponents() {
        root = (Pane) getRoot();
    }

    private void constructSceneGraph(GameMediator gameMediator) {
        game = new GameNode(gameMediator);
        root.getChildren().addAll(game);
    }

    private void buildComponents() {
        buildBoardComponent();
    }

    private void buildBoardComponent() {
        Pane parent = (Pane) game.getParent();
        Pane.layoutInArea(game, 0, 0, parent.getWidth(), parent.getHeight(), 0, HPos, VPos.CENTER);
        game.minWidthProperty().bind(parent.widthProperty());
        game.maxWidthProperty().bind(parent.widthProperty());
    }
}
