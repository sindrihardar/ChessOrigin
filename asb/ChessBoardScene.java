package vidmot;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;


import presenter.GameMediator;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import vidmot.nodes.GameNode;
import vidmot.nodes.TopBarNode;

public class ChessBoardScene extends Scene {

    private TopBarNode topBar;

    @FXML
    private AnchorPane fxChessBoard;
    private Pane game;

    public ChessBoardScene(GameMediator gameMediator) {
        super(new AnchorPane(), 400, 400);
        initializeComponents();
        constructSceneGraph(gameMediator);
        buildComponents();
    }

    private void initializeComponents() {
        fxChessBoard = (AnchorPane) getRoot();
        fxChessBoard.setPadding(new Insets(0, 0, 10, 10));
    }

    private void constructSceneGraph(GameMediator gameMediator) {
        game = new GameNode(gameMediator);
        fxChessBoard.getChildren().add(game);
    }

    private void buildComponents() {
        buildBoardComponent();
    }

    private void buildBoardComponent() {
        AnchorPane parent = (AnchorPane) game.getParent();
        game.minWidthProperty().bind(parent.widthProperty());
        game.maxWidthProperty().bind(parent.widthProperty());
    }
}
