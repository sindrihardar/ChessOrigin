package com.chess.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeScene extends Scene {
    private VBox root;
    private Text title;
    private Button localChessGameButton;

    public HomeScene(double width, double height) {
        super(new VBox(), width, height);
        root = (VBox) getRoot();
        root.setSpacing(30);
        buildLocalChessGameButton();
        root.setAlignment(Pos.CENTER);
        buildTitleLabel();
        root.getChildren().add(title);
        root.getChildren().add(localChessGameButton);
    }

    private void buildTitleLabel() {
        title = new Text("Chess");
        title.setFont(new Font("Impact", 100));
        title.setStroke(new Color(.7, 0.2843, 0.6725, 1));
    }

    private void buildLocalChessGameButton() {
        localChessGameButton = new Button("Play Locally");
        localChessGameButton.setFont(new Font("Impact", 20));
        localChessGameButton.setMinWidth(200);
        localChessGameButton.setMaxWidth(200);
        localChessGameButton.setMinHeight(50);
        localChessGameButton.setMaxHeight(50);
        localChessGameButton.setStyle("-fx-background-color: rgb(73, 204, 132);");
        localChessGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(new ChessBoardScene(getWidth(), getHeight()));
                stage.show();
            }
        });
        localChessGameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                localChessGameButton.setStyle("-fx-background-color: rgb(53, 184, 112);");
            }
        });
        localChessGameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                localChessGameButton.setStyle("-fx-background-color: rgb(73, 204, 132);");
            }
        });
    }
}
