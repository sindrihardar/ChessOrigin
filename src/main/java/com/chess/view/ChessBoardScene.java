package com.chess.view;

import com.chess.model.util.Colors;
import com.chess.presenter.ChessBoardPresenter;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChessBoardScene extends Scene implements Observer {
    private static final int BOARD_PADDING = 20;
    private VBox root;
    private HBox topBar;
    private Pane boardPane;
    private Button homeButton;
    private StackPane boardContainer, messageNode;
    private TilePane board;
    private ChessBoardPresenter presenter;
    private final Color messageColor = new Color(0.8275, 0.3176, 0.6, 0.5);
    private final String messageFont = "Impact";

    public ChessBoardScene(double width, double height) {
        super(new VBox(), width, height);
        initializeComponents();
        constructSceneGraph();
        buildComponents();
    }

    private void initializeComponents() {
        setUpPresenter();
        root = (VBox) getRoot();
        topBar = new HBox();
        homeButton = new Button();
        boardPane = new Pane();
        boardContainer = new StackPane();
        board = new TilePane();
    }

    private void constructSceneGraph() {
        root.getChildren().addAll(topBar, boardPane);
        topBar.getChildren().addAll(homeButton);
        boardPane.getChildren().addAll(boardContainer);
        boardContainer.getChildren().addAll(board);
    }

    private void buildComponents() {
        buildTopBar();
        buildBoardComponent();
    }

    private void setUpPresenter() {
        presenter = new ChessBoardPresenter();
        presenter.attach(this);
    }

    private void buildTopBar() {
        final int TOP_BAR_HEIGHT = 40;
        final String TOP_BAR_STYLE = "-fx-background-color: rgb(73, 204, 132);";
        final Insets TOP_BAR_PADDING = new Insets(5, 5, 5, 5);
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
        final String HOME_ICON_FILE_PATH = "file:./src/main/java/com/chess/view/resources/home_icon.png";
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
        homeButton.setOnMouseEntered(mouseEvent -> homeButton.setStyle("-fx-background-color: rgb(43, 174, 102);"));
        homeButton.setOnMouseExited(mouseEvent -> homeButton.setStyle(parent.getStyle()));
    }

    private void buildBoardComponent() {
        VBox parent = (VBox) boardPane.getParent();
        VBox.setVgrow(boardPane, Priority.ALWAYS);
        boardPane.minWidthProperty().bind(parent.widthProperty());
        boardPane.maxWidthProperty().bind(parent.widthProperty());
        buildBoardContainer();
    }


    private void buildBoardContainer() {
        Pane parent = (Pane) boardContainer.getParent();
        boardContainer.minHeightProperty().bind(Bindings.min(parent.widthProperty(), parent.heightProperty()).subtract(2 * BOARD_PADDING));
        boardContainer.minWidthProperty().bind(boardContainer.minHeightProperty());
        boardContainer.maxWidthProperty().bind(boardContainer.minWidthProperty());
        boardContainer.maxHeightProperty().bind(boardContainer.minHeightProperty());
        boardContainer.layoutXProperty().bind(Bindings.max(BOARD_PADDING,widthProperty().divide(2).subtract(boardContainer.widthProperty().divide(2))));
        boardContainer.layoutYProperty().bind(parent.heightProperty().divide(2).subtract(boardContainer.heightProperty().divide(2)));
        buildBoard();
    }

    private void buildBoard() {
        StackPane parent = (StackPane) board.getParent();
        board.setPrefColumns(8);
        board.setPrefRows(8);
        board.minHeightProperty().bind(parent.maxWidthProperty());
        board.minWidthProperty().bind(parent.maxWidthProperty());
        board.maxHeightProperty().bind(parent.maxWidthProperty());
        board.maxWidthProperty().bind(parent.maxWidthProperty());
        board.prefTileHeightProperty().bind(board.heightProperty().subtract(board.getPrefColumns()).divide(8));
        board.prefTileWidthProperty().bind(board.prefTileHeightProperty());
        addSpacesToBoard(board);
    }

    private void addSpacesToBoard(TilePane board) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buildTile(i, j, presenter, board);
    }

    private void buildTile(int row, int col, ChessBoardPresenter presenter, TilePane board) {
        ChessBoardTileNode spaceNode = new ChessBoardTileNode(row, col, presenter.getChessBoardTilePresenter(row, col), presenter);
        spaceNode.minHeightProperty().bind(board.prefTileHeightProperty());
        spaceNode.minWidthProperty().bind(board.prefTileHeightProperty());
        spaceNode.maxHeightProperty().bind(board.prefTileHeightProperty());
        spaceNode.maxWidthProperty().bind(board.prefTileHeightProperty());
        board.getChildren().add(spaceNode);
    }

    private void buildMessageNode(StackPane parent, String message) {
        messageNode = new StackPane();
        Rectangle background = new Rectangle();

        // build background for the message
        background.setFill(messageColor);
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));

        // build the message itself
        Label label = new Label(message);
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), "; -fx-font-family: " + messageFont + ";"));
        messageNode.getChildren().add(background);
        messageNode.getChildren().add(label);
    }

    @Override
    public void update() {
        if (presenter.isGameInStalemate()) {
            buildMessageNode(boardContainer, "Stalemate!");
            boardContainer.getChildren().add(messageNode);
        } else if (presenter.isPlayerInCheckmate() && presenter.getCurrentPlayersColor() == Colors.BLACK) {
            buildMessageNode(boardContainer, "White won!");
            boardContainer.getChildren().add(messageNode);
        } else if (presenter.isPlayerInCheckmate() && presenter.getCurrentPlayersColor() == Colors.WHITE) {
            buildMessageNode(boardContainer, "Black won!");
            boardContainer.getChildren().add(messageNode);
        }
    }
}
