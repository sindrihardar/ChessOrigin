package com.chess.view.scenes;

import com.chess.presenter.GameMediator;
import com.chess.presenter.MediatorConstructionFlags;
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
    private static final String TITLE = "Chess";
    private static final String FONT_NAME = "Impact";
    private Text title;
    private Button localChessGameButton, computerChessGameButton;

    public HomeScene(double width, double height) {
        super(new VBox(), width, height);
        VBox root = (VBox) getRoot();
        root.setSpacing(15);
        buildLocalChessGameButton();
        root.setAlignment(Pos.CENTER);
        buildTitleLabel();
        buildAIChessGameButton();
        root.getChildren().add(title);
        root.getChildren().add(localChessGameButton);
        root.getChildren().add(computerChessGameButton);
    }

    private void buildTitleLabel() {
        title = new Text(TITLE);
        title.setFont(new Font(FONT_NAME, 100));
        title.setStroke(new Color(.7, 0.2843, 0.6725, 1));
    }

    private void buildAIChessGameButton() {
        computerChessGameButton = new Button("Play Against the Computer");
        computerChessGameButton.setFont(new Font("Impact", 20));
        computerChessGameButton.setMinWidth(300);
        computerChessGameButton.setMaxWidth(300);
        computerChessGameButton.setMinHeight(50);
        computerChessGameButton.setMaxHeight(50);
        computerChessGameButton.setStyle("-fx-background-color: rgb(73, 204, 132);");
        computerChessGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(new UpgradedHomeScene(getWidth(), getHeight()));
                //stage.setScene(new ChessBoardScene(getWidth(), getHeight(), new GameMediator(MediatorConstructionFlags.TIMED_AI)));
                stage.show();
            }
        });
        computerChessGameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                computerChessGameButton.setStyle("-fx-background-color: rgb(53, 184, 112);");
            }
        });
        computerChessGameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                computerChessGameButton.setStyle("-fx-background-color: rgb(73, 204, 132);");
            }
        });
    }

    private void buildLocalChessGameButton() {
        localChessGameButton = new Button("Play Locally");
        localChessGameButton.setFont(new Font("Impact", 20));
        localChessGameButton.setMinWidth(300);
        localChessGameButton.setMaxWidth(300);
        localChessGameButton.setMinHeight(50);
        localChessGameButton.setMaxHeight(50);
        localChessGameButton.setStyle("-fx-background-color: rgb(73, 204, 132);");
        localChessGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(new ChessBoardScene(getWidth(), getHeight(), new GameMediator(MediatorConstructionFlags.TIMED_LOCAL)));
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
