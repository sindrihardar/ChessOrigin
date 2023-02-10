package com.chess.view.scenes;

import com.chess.model.util.Colors;
import com.chess.presenter.AITimedChessBoardPresenter;
import com.chess.presenter.ChessBoardPresenter;
import com.chess.presenter.StandardTimedChessBoardPresenter;
import com.chess.view.nodes.ChessBoardNode;
import com.chess.view.nodes.FullChessGameNode;
import com.chess.view.nodes.TimerNode;
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
    private HBox topBar;
    private Pane boardPane;
    private Button homeButton;
    private HBox boardContainer;

    public ChessBoardScene(double width, double height, ChessBoardPresenter presenter) {
        super(new VBox(), width, height);
        initializeComponents();
        constructSceneGraph(presenter);
        buildComponents();
    }

    private void initializeComponents() {
        root = (VBox) getRoot();
        topBar = new HBox();
        homeButton = new Button();
        boardPane = new Pane();
    }

    private void constructSceneGraph(ChessBoardPresenter presenter) {
        root.getChildren().addAll(topBar, boardPane);
        topBar.getChildren().addAll(homeButton);
        if (presenter instanceof StandardTimedChessBoardPresenter)
            boardContainer = new FullChessGameNode(boardPane, (StandardTimedChessBoardPresenter) presenter);
        if (presenter instanceof AITimedChessBoardPresenter)
            boardContainer = new FullChessGameNode(boardPane, (AITimedChessBoardPresenter) presenter);
        boardPane.getChildren().add(boardContainer);
    }

    private void buildComponents() {
        buildTopBar();
        buildBoardComponent();
    }

    private void buildTopBar() {
        topBar.setMinHeight(TOP_BAR_HEIGHT);
        topBar.setMaxHeight(TOP_BAR_HEIGHT);
        topBar.minWidthProperty().bind(((VBox) topBar.getParent()).widthProperty());
        topBar.maxWidthProperty().bind(topBar.minWidthProperty());
        topBar.setStyle(TOP_BAR_STYLE);
        topBar.setPadding(TOP_BAR_PADDING);
        buildHomeButton();
    }

    private void buildHomeButton() {
        HBox parent = (HBox) homeButton.getParent();
        homeButton.minHeightProperty().bind(parent.heightProperty().subtract(parent.getPadding().getBottom() + parent.getPadding().getTop()));
        homeButton.maxHeightProperty().bind(homeButton.minHeightProperty());
        homeButton.minWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.maxWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.setStyle(parent.getStyle());

        // add image to the button
        ImageView imageView = new ImageView(new Image(HOME_ICON_FILE_PATH, 20, 20, false, false));
        imageView.minWidth(homeButton.getWidth());
        imageView.maxWidth(homeButton.getWidth());
        imageView.minHeight(homeButton.getHeight());
        imageView.maxHeight(homeButton.getHeight());
        homeButton.setGraphic(imageView);

        homeButton.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new HomeScene(getWidth(), getHeight()));
            stage.show();
        });
        homeButton.setOnMouseEntered(mouseEvent -> homeButton.setStyle(HIGHLIGHTED_TOP_BAR_BUTTON_STYLE));
        homeButton.setOnMouseExited(mouseEvent -> homeButton.setStyle(TOP_BAR_STYLE));
    }

    private void buildBoardComponent() {
        VBox parent = (VBox) boardPane.getParent();
        VBox.setVgrow(boardPane, Priority.ALWAYS);
        boardPane.minWidthProperty().bind(parent.widthProperty());
        boardPane.maxWidthProperty().bind(parent.widthProperty());
    }
}
