package com.chess.view.nodes;

import com.chess.view.scenes.HomeScene;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TopBarNode extends Pane {
    private HBox root;
    private Button homeButton;
    private static final int TOP_BAR_HEIGHT = 40;
    private static final String TOP_BAR_STYLE = "-fx-background-color: rgb(73, 204, 132);",
            HIGHLIGHTED_TOP_BAR_BUTTON_STYLE = "-fx-background-color: rgb(43, 174, 102);",
            HOME_ICON_FILE_PATH = "file:./src/main/java/com/chess/view/resources/home_icon.png";
    private static final Insets TOP_BAR_PADDING = new Insets(5, 5, 5, 5);

    public TopBarNode() {
        setMinHeight(TOP_BAR_HEIGHT);
        setMaxHeight(TOP_BAR_HEIGHT);

        root = new HBox();

        root.minHeightProperty().bind(heightProperty());
        root.maxHeightProperty().bind(heightProperty());
        root.minWidthProperty().bind(widthProperty());
        root.maxWidthProperty().bind(widthProperty());
        root.setStyle(TOP_BAR_STYLE);
        root.setPadding(TOP_BAR_PADDING);
        buildHomeButton();
        getChildren().add(root);
        root.getChildren().add(homeButton);
    }

    private void buildHomeButton() {
        homeButton = new Button();
        homeButton.minHeightProperty().bind(root.heightProperty().subtract(root.getPadding().getBottom() + root.getPadding().getTop()));
        homeButton.maxHeightProperty().bind(homeButton.minHeightProperty());
        homeButton.minWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.maxWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.setStyle(root.getStyle());

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
}
