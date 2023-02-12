package com.chess.view.scenes;

import com.chess.presenter.BoardPresenter;
import com.chess.presenter.GameMediator;
import com.chess.view.nodes.GameNode;
import com.chess.view.nodes.TopBarNode;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ChessBoardScene extends Scene {
    private static final String TOP_BAR_STYLE = "-fx-background-color: rgb(73, 204, 132);",
                                HIGHLIGHTED_TOP_BAR_BUTTON_STYLE = "-fx-background-color: rgb(43, 174, 102);",
                                HOME_ICON_FILE_PATH = "file:./src/main/java/com/chess/view/resources/home_icon.png";

    private static final int TOP_BAR_HEIGHT = 40;
    private static final Insets TOP_BAR_PADDING = new Insets(5, 5, 5, 5);
    private VBox root;
    private TopBarNode topBar;
    private Pane game;
    private Button homeButton;

    public ChessBoardScene(double width, double height, GameMediator gameMediator) {
        super(new VBox(), width, height);
        initializeComponents();
        constructSceneGraph(gameMediator);
        buildComponents();
    }

    private void initializeComponents() {
        root = (VBox) getRoot();
        topBar = new TopBarNode();
        topBar.minWidthProperty().bind(widthProperty());
        topBar.maxWidthProperty().bind(widthProperty());
        topBar.setMaxHeight(40);
        topBar.setMinHeight(40);
    }

    private void constructSceneGraph(GameMediator gameMediator) {
        game = new GameNode(gameMediator);
        root.getChildren().addAll(topBar, game);
    }

    private void buildComponents() {
        buildBoardComponent();
    }

    private void buildBoardComponent() {
        VBox parent = (VBox) game.getParent();
        VBox.setVgrow(game, Priority.ALWAYS);
        game.minWidthProperty().bind(parent.widthProperty());
        game.maxWidthProperty().bind(parent.widthProperty());
    }
}
