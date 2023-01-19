package com.chess.view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScene extends Scene {
    private VBox root;
    private Button localChessGameButton;

    public HomeScene() {
        super(new VBox());
        root = (VBox) getRoot();
        buildLocalChessGameButton();
        root.getChildren().add(localChessGameButton);
    }

    private void buildLocalChessGameButton() {
        localChessGameButton = new Button("Play Locally");
        localChessGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(new ChessBoardScene());
                stage.show();
            }
        });
    }
}
