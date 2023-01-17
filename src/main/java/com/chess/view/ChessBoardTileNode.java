package com.chess.view;

import com.chess.presenter.ChessBoardTilePresenter;
import com.chess.presenter.ChessBoardPresenter;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class ChessBoardTileNode extends StackPane implements Observer {
    private int row, col;
    private String style;
    private ImageView imageView;
    private ChessBoardTilePresenter spacePresenter;
    private ChessBoardPresenter gamePresenter;

    public ChessBoardTileNode(int row, int col, ChessBoardTilePresenter spacePresenter, ChessBoardPresenter gamePresenter) {
        setUpState(row, col, spacePresenter, gamePresenter);
        addImageView();
        setUpEventHandlers();
        update();
        spacePresenter.attach(this); // adds this node as an observer of the presenter
    }

    public void setUpState(int row, int col, ChessBoardTilePresenter spacePresenter, ChessBoardPresenter gamePresenter) {
        this.row = row;
        this.col = col;
        this.gamePresenter = gamePresenter;
        this.spacePresenter = spacePresenter;
    }

    public void addImageView() {
        imageView = new ImageView();
        imageView.fitHeightProperty().bind(minHeightProperty());
        imageView.fitWidthProperty().bind(minWidthProperty());
        getChildren().add(imageView);
    }

    public void setUpEventHandlers() {
        setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.click(row, col);
            }
        });

        setOnMouseEntered(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.hoverInTo(row, col);
            }
        });

        setOnMouseExited(new EventHandler<>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gamePresenter.hoverOutOf(row, col);
            }
        });
    }

    /*
     *  Updates the style and image of the node based on the state of the ChessBoardTilePresenter.
     *
     *  This is called whenever the state of the ChessBoardTilePresenter is changed.
     */
    @Override
    public void update() {
        style = spacePresenter.getStyle();
        imageView.setImage(spacePresenter.getImage());
        setStyle(style);
    }
}
