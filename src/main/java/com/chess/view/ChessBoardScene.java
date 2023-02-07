package com.chess.view;

import com.chess.model.util.Colors;
import com.chess.presenter.AIChessBoardPresenter;
import com.chess.presenter.ChessBoardPresenter;
import com.chess.presenter.StandardChessBoardPresenter;
import javafx.animation.PathTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChessBoardScene extends Scene implements Observer {
    private final Color MESSAGE_COLOR = new Color(0.8275, 0.3176, 0.6, 0.5);
    private final String MESSAGE_FONT = "Impact";
    private static final int BOARD_PADDING = 20;
    private VBox root;
    private HBox topBar;
    private Pane boardPane;
    private Button homeButton;
    private StackPane boardContainer, messageNode;
    private TilePane board;
    private ChessBoardPresenter presenter;

    public ChessBoardScene(double width, double height, ChessBoardPresenter presenter) {
        super(new VBox(), width, height);
        initializeComponents(presenter);
        constructSceneGraph();
        buildComponents();
    }

    private void initializeComponents(ChessBoardPresenter presenter) {
        setUpPresenter(presenter);
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

    private void setUpPresenter(ChessBoardPresenter presenter) {
        this.presenter = presenter;
        this.presenter.attach(this);
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
        addTilesToBoard(board);
    }

    private void addTilesToBoard(TilePane board) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                buildTile(i, j, presenter, board);
    }

    private void buildTile(int row, int col, ChessBoardPresenter presenter, TilePane board) {
        ChessBoardTileNode tileNode = new ChessBoardTileNode(row, col, presenter.getChessBoardTilePresenter(row, col), presenter);
        tileNode.minHeightProperty().bind(board.prefTileHeightProperty());
        tileNode.minWidthProperty().bind(board.prefTileHeightProperty());
        tileNode.maxHeightProperty().bind(board.prefTileHeightProperty());
        tileNode.maxWidthProperty().bind(board.prefTileHeightProperty());
        board.getChildren().add(tileNode);
    }

    private void buildMessageNode(StackPane parent, String message) {
        messageNode = new StackPane();
        Rectangle background = new Rectangle();

        // build background for the message
        background.setFill(MESSAGE_COLOR);
        background.widthProperty().bind(parent.widthProperty().divide(2));
        background.heightProperty().bind(parent.heightProperty().divide(3));

        // build the message itself
        Label label = new Label(message);
        label.styleProperty().bind(Bindings.concat("-fx-font-size: ", background.heightProperty().divide(4), "; -fx-font-family: " + MESSAGE_FONT + ";"));
        messageNode.getChildren().add(background);
        messageNode.getChildren().add(label);
    }

    public void movePieceAnimation(int row1, int col1, int row2, int col2) {
        StackPane node1 = (StackPane) board.getChildren().get(row1 * 8 + col1), node2 = (StackPane) board.getChildren().get(row2 * 8 + col2);
        double startX = node1.getLayoutX() + node1.getWidth() / 2, startY = node1.getLayoutY() + node1.getHeight() / 2;
        double endX = node2.getLayoutX() + node2.getWidth() / 2, endY = node2.getLayoutY() + node2.getHeight() / 2;
        startX -= node1.getLayoutX();
        startY -= node1.getLayoutY();
        endX -= node1.getLayoutX();
        endY -= node1.getLayoutY();

        // create the path to be traveled (where is the node going)
        Path path = new Path();
        MoveTo endPoint = new MoveTo(startX, startY);
        path.getElements().add(endPoint);
        path.getElements().add(new LineTo(endX, endY));

        // create the path transition (where is the node starting and how long should it take)
        PathTransition animation = new PathTransition();
        animation.setDuration(Duration.millis(1000));
        animation.setNode(node1.getChildren().get(0));
        animation.setPath(path);

        animation.play();
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
