package com.chess.view;

import com.chess.presenter.ChessBoardSpacePresenter;
import com.chess.presenter.ChessGamePresenter;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ChessGameSpaceNode extends StackPane implements Observer {
    private int row, col;
    private String style;
    private ImageView imageView;
    private ChessBoardSpacePresenter spacePresenter;
    private ChessGamePresenter gamePresenter;

    public ChessGameSpaceNode(int row, int col, ChessBoardSpacePresenter spacePresenter, ChessGamePresenter gamePresenter) {
        setUpState(row, col, spacePresenter, gamePresenter);
        imageView = new ImageView();
        imageView.fitHeightProperty().bind(minHeightProperty());
        imageView.fitWidthProperty().bind(minWidthProperty());
        update();
        getChildren().add(imageView);
        setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.click(row, col);
            }
        });

        setOnMouseEntered(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.hoverOver(row, col);
            }
        });

        setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.hoverOutOf(row, col);
            }
        });
        spacePresenter.attach(this);
    }

    public void setUpState(int row, int col, ChessBoardSpacePresenter spacePresenter, ChessGamePresenter gamePresenter) {
        this.row = row;
        this.col = col;
        this.gamePresenter = gamePresenter;
        this.spacePresenter = spacePresenter;
    }

    @Override
    public void update() {
        style = spacePresenter.getStyle();
        imageView.setImage(spacePresenter.getImage());
        setStyle(style);
    }
}
