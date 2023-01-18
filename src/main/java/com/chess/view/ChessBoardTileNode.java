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
import javafx.scene.text.Text;

public class ChessBoardTileNode extends StackPane implements Observer {
    private int row, col;
    private String style;
    private ImageView imageView;
    private ChessBoardTilePresenter spacePresenter;
    private ChessBoardPresenter gamePresenter;

    public ChessBoardTileNode(int row, int col, ChessBoardTilePresenter spacePresenter, ChessBoardPresenter gamePresenter) {
        setUpState(row, col, spacePresenter, gamePresenter);
        addImageView();
        addRank();
        addFile();
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

    public void addFile() {
        if (col != 0)
            return;

        Label file = new Label(getFile(row));
        file.setFont(new Font(9));
        file.setPadding(new Insets(2, 0, 0, 2));
        StackPane.setAlignment(file, Pos.TOP_LEFT);
        getChildren().add(file);
    }

    public String getRank(int row) {
        return String.valueOf((char) ('a' + row));
    }

    public void addRank() {
        if (row != 7)
            return;

        Label rank = new Label(getRank(col));
        rank.setFont(new Font(9));
        rank.setPadding(new Insets(0, 2, 2, 0));
        StackPane.setAlignment(rank, Pos.BOTTOM_RIGHT);
        getChildren().add(rank);
    }

    public String getFile(int col) {
        return String.valueOf(8 - col);
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
