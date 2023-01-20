package com.chess.view;

import com.chess.presenter.ChessBoardTilePresenter;
import com.chess.presenter.ChessBoardPresenter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class ChessBoardTileNode extends StackPane implements Observer {
    private int row, col;
    private String style;
    private ImageView imageView;
    private ChessBoardTilePresenter tilePresenter;
    private ChessBoardPresenter gamePresenter;

    public ChessBoardTileNode(int row, int col, ChessBoardTilePresenter spacePresenter, ChessBoardPresenter gamePresenter) {
        setUpState(row, col, spacePresenter, gamePresenter);
        addImageView();
        addRank();
        addFile();
        setUpEventHandlers();
        update();
        spacePresenter.attach(this);
    }

    private void setUpState(int row, int col, ChessBoardTilePresenter spacePresenter, ChessBoardPresenter gamePresenter) {
        this.row = row;
        this.col = col;
        this.gamePresenter = gamePresenter;
        this.tilePresenter = spacePresenter;
    }

    private void addImageView() {
        imageView = new ImageView();
        imageView.fitHeightProperty().bind(minHeightProperty());
        imageView.fitWidthProperty().bind(minWidthProperty());
        getChildren().add(imageView);
    }

    private void addRank() {
        if (row != 7)
            return;

        Label rank = new Label(getRank(col));
        rank.setFont(new Font(9));
        rank.setPadding(new Insets(0, 2, 2, 0));
        StackPane.setAlignment(rank, Pos.BOTTOM_RIGHT);
        getChildren().add(rank);
    }

    private String getRank(int row) {
        return String.valueOf((char) ('a' + row));
    }

    private void addFile() {
        if (col != 0)
            return;

        Label file = new Label(getFile(row));
        file.setFont(new Font(9));
        file.setPadding(new Insets(2, 0, 0, 2));
        StackPane.setAlignment(file, Pos.TOP_LEFT);
        getChildren().add(file);
    }

    private String getFile(int col) {
        return String.valueOf(8 - col);
    }

    private void setUpEventHandlers() {
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
                mouseEvent.consume(); gamePresenter.hoverOutOf(row, col);
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
        style = tilePresenter.getStyle();
        imageView.setImage(tilePresenter.getImage());
        setStyle(style);
    }
}
