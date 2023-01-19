package com.chess.view;

import com.chess.model.util.Colors;
import com.chess.presenter.ChessBoardPresenter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/*
 *  The ChessBoardScene class is a scene that displays a chess board. The constructor for the scene builds its components
 *  and puts together the scene graph. The boardContainer contains the board and a gameOverMessageNode if the game is over.
 *  The board is composed of ChessBoardTileNodes.
 *
 *  The scene also has a ChessBoardPresenter for the chess game, and each individual ChessBoardTileNode contains a ChessBoardTilePresenter.
 *  These presenters contain information on the strings, images, and styles that get presented in the view component.
 *
 *  This class is an Observer, so when it observes a change to an observable object that it's attached to, the update
 *  method is called. Each ChessBoardScene object is attached to its presenter, so each time the state of the presenter
 *  is updated, the view is updated as well.
 */
public class ChessBoardScene extends Scene implements Observer {
    private static final int BOARD_PADDING = 20;
    private VBox newRoot;
    private HBox topBar;
    private Pane root;
    private StackPane boardContainer, messageNode;
    private TilePane board;
    private ChessBoardPresenter presenter;
    private Color messageColor = new Color(0.8275, 0.3176, 0.6, 0.5);
    private String messageFont = "Impact";

    public ChessBoardScene(double width, double height) {
        super(new VBox(), width, height);
        newRoot = (VBox) getRoot();
        root = new Pane();
        setUpTopBar();
        newRoot.getChildren().add(topBar);
        setUpPresenter();
        buildBoardContainer();
        setUpBoard();
        newRoot.getChildren().add(root);
        root.minWidthProperty().bind(newRoot.widthProperty());
        root.maxWidthProperty().bind(newRoot.widthProperty());
        root.minHeightProperty().bind(newRoot.heightProperty().subtract(topBar.heightProperty()));
        root.maxHeightProperty().bind(newRoot.heightProperty().subtract(topBar.heightProperty()));
    }

    private void setUpTopBar() {
        topBar = new HBox();
        topBar.setMinHeight(40);
        topBar.setMaxHeight(40);
        topBar.minWidthProperty().bind(newRoot.widthProperty());
        topBar.maxWidthProperty().bind(newRoot.widthProperty());
        topBar.setStyle("-fx-background-color: rgb(73, 204, 132);");
        topBar.setPadding(new Insets(5, 5, 5, 5));
        ImageView imageView = new ImageView(new Image("file:./src/main/java/com/chess/view/resources/home_icon.png", 20, 20, false, false));
        Button homeButton = new Button("", imageView);
        homeButton.minHeightProperty().bind(topBar.heightProperty().subtract(10));
        homeButton.maxHeightProperty().bind(topBar.heightProperty().subtract(10));
        homeButton.minWidthProperty().bind(homeButton.heightProperty());
        homeButton.maxWidthProperty().bind(homeButton.heightProperty());
        imageView.minWidth(homeButton.getHeight());
        imageView.maxWidth(homeButton.getHeight());
        homeButton.setPadding(new Insets(10, 10, 10, 10));
        homeButton.setStyle(topBar.getStyle());
        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stage.setScene(new HomeScene(getWidth(), getHeight()));
                stage.show();
            }
        });
        topBar.getChildren().add(homeButton);
    }

    private void setUpPresenter() {
        presenter = new ChessBoardPresenter();
        presenter.attach(this);
    }

    private void buildBoardContainer() {
        boardContainer = new StackPane();
        boardContainer.minHeightProperty().bind(Bindings.min(root.widthProperty(), root.heightProperty()).subtract(2 * BOARD_PADDING));
        boardContainer.minWidthProperty().bind(boardContainer.minHeightProperty());
        boardContainer.maxWidthProperty().bind(boardContainer.minWidthProperty());
        boardContainer.maxHeightProperty().bind(boardContainer.minHeightProperty());
        boardContainer.layoutXProperty().bind(Bindings.max(BOARD_PADDING,widthProperty().divide(2).subtract(boardContainer.widthProperty().divide(2))));
        boardContainer.layoutYProperty().bind(root.heightProperty().divide(2).subtract(boardContainer.heightProperty().divide(2)));
        root.getChildren().add(boardContainer);
    }

    private void setUpBoard() {
        board = new TilePane();
        board.setPrefColumns(8);
        board.setPrefRows(8);
        bindWidthAndHeightToProperty(board, boardContainer.maxWidthProperty());
        board.prefTileHeightProperty().bind(board.heightProperty().subtract(board.getPrefColumns()).divide(8));
        board.prefTileWidthProperty().bind(board.prefTileHeightProperty());
        addSpacesToBoard();
        boardContainer.getChildren().add(board);
    }

    private void addSpacesToBoard() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                setUpTile(i, j, presenter);
    }

    private void setUpTile(int row, int col, ChessBoardPresenter presenter) {
        ChessBoardTileNode spaceNode = new ChessBoardTileNode(row, col, presenter.getChessBoardTilePresenter(row, col), presenter);
        bindWidthAndHeightToProperty(spaceNode, board.prefTileHeightProperty());
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

    private void bindWidthAndHeightToProperty(Pane node, Property property) {
        node.minHeightProperty().bind(property);
        node.minWidthProperty().bind(property);
        node.maxHeightProperty().bind(property);
        node.maxWidthProperty().bind(property);
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
