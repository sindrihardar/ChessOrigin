package vidmot.nodes;

//import com.chess.view.scenes.HomeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.*;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import vidmot.MediaManager;
import vidmot.TimaController;
import vidmot.View;
import vidmot.ViewSwitcher;

import java.io.IOException;

import java.util.Optional;


public class TopBarNode extends Pane {
    private final HBox root;
    private Button homeButton;
    private Button soundButton;
    private MenuBar themeMenu;
    private final MediaPlayer mediaPlayer = MediaManager.getMediaPlayer();
    private String selectedStylesheet = getClass().getResource("vidmot/stylesheets/classic-styles.css").toExternalForm();
    private static final int TOP_BAR_HEIGHT = 40;
    private static final int TOP_BAR_WIDTH = 618;


    private static final Insets TOP_BAR_PADDING = new Insets(5, 5, 5, 5);
    private final TimaController timaController = (TimaController) ViewSwitcher.lookup(View.TIMAMORK);


    public TopBarNode() {
        setMinHeight(TOP_BAR_HEIGHT);
        setMaxHeight(TOP_BAR_HEIGHT);
        setMinWidth(TOP_BAR_WIDTH);
        setMaxWidth(TOP_BAR_WIDTH);

        root = new HBox();

        root.minHeightProperty().bind(heightProperty());
        root.maxHeightProperty().bind(heightProperty());
        root.minWidthProperty().bind(widthProperty());
        root.maxWidthProperty().bind(widthProperty());
        root.setId("fxtopBar");
        root.getStylesheets().add(selectedStylesheet);
        root.setPadding(TOP_BAR_PADDING);
        root.setAlignment(Pos.CENTER);
        buildHomeButton();
        getChildren().add(root);
        root.getChildren().add(homeButton);
        Label title = new Label("Skák");
        title.setId("title");
        title.getStylesheets().add(selectedStylesheet);
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        HBox.setMargin(title, new Insets(0, 0, 0, -100));
        title.minWidthProperty().bind(root.widthProperty().subtract(homeButton.widthProperty()));
        title.maxWidthProperty().bind(root.widthProperty().subtract(homeButton.widthProperty()));
        HBox.setHgrow(title, Priority.ALWAYS);
        root.getChildren().add(title);
        buildSoundButton();
        root.getChildren().add(soundButton);
        buildThemeMenu();
        root.getChildren().add(themeMenu);
    }

    private void buildThemeMenu() {
        themeMenu = new MenuBar();
        themeMenu.setLayoutX(80.0);
        themeMenu.setLayoutY(13.0);
        themeMenu.setMinHeight(0.0);
        themeMenu.setMinWidth(0.0);
        themeMenu.setPickOnBounds(false);
        themeMenu.setPrefHeight(28.0);
        themeMenu.setPrefWidth(43.0);
        themeMenu.getStylesheets().add("vidmot/stylesheets/upphaf-styles.css");

        Menu fxTheme = new Menu();
        fxTheme.setId("fxTheme");
        fxTheme.setText("Þema");

        MenuItem fxClassicItem = new MenuItem();
        fxClassicItem.setText("Klassískt");
        fxClassicItem.setOnAction(this::fxClassicHandler);

        MenuItem fxCottonCandyItem = new MenuItem();
        fxCottonCandyItem.setText("Cotton candy");
        fxCottonCandyItem.setOnAction(this::fxCottonCandyHandler);

        MenuItem fxTropicalItem = new MenuItem();
        fxTropicalItem.setText("Tropical");
        fxTropicalItem.setOnAction(this::fxTropicalHandler);

        fxTheme.getItems().addAll(fxClassicItem, fxCottonCandyItem, fxTropicalItem);

        themeMenu.getMenus().add(fxTheme);

        themeMenu.setPadding(new Insets(-5.0, -5.0, 0.0, 0.0));

    }

    public void fxClassicHandler(ActionEvent actionEvent) {
        String newStylesheet = getClass().getResource("vidmot/stylesheets/classic-styles.css").toExternalForm();
        if (!selectedStylesheet.equals(newStylesheet)) {
            homeButton.getScene().getStylesheets().remove(selectedStylesheet);
            homeButton.getScene().getStylesheets().add(newStylesheet);
            selectedStylesheet = newStylesheet;
        }
    }

    public void fxCottonCandyHandler(ActionEvent actionEvent) {
        String newStylesheet = getClass().getResource("vidmot/stylesheets/cottoncandy-styles.css").toExternalForm();
        if (!selectedStylesheet.equals(newStylesheet)) {
            homeButton.getScene().getStylesheets().remove(selectedStylesheet);
            homeButton.getScene().getStylesheets().add(newStylesheet);
            selectedStylesheet = newStylesheet;
        }
    }

    public void fxTropicalHandler(ActionEvent actionEvent) {
        String newStylesheet = getClass().getResource("vidmot/stylesheets/tropical-styles.css").toExternalForm();
        if (!selectedStylesheet.equals(newStylesheet)) {
            homeButton.getScene().getStylesheets().remove(selectedStylesheet);
            homeButton.getScene().getStylesheets().add(newStylesheet);
            selectedStylesheet = newStylesheet;
        }
    }

    private void buildHomeButton() {
        homeButton = new Button();
        homeButton.setId("fxhomeButton");
        homeButton.minHeightProperty().bind(root.heightProperty().subtract(root.getPadding().getBottom() + root.getPadding().getTop()));
        homeButton.maxHeightProperty().bind(homeButton.minHeightProperty());
        homeButton.minWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.maxWidthProperty().bind(homeButton.minHeightProperty());
        homeButton.getStylesheets().add(selectedStylesheet);

        // add image to the button
        ImageView imageView = new ImageView(new Image("vidmot/images/home_icon.png", 20, 20, false, false));
        imageView.minWidth(homeButton.getWidth());
        imageView.maxWidth(homeButton.getWidth());
        imageView.minHeight(homeButton.getHeight());
        imageView.maxHeight(homeButton.getHeight());
        homeButton.setGraphic(imageView);

        homeButton.setOnMouseClicked(mouseEvent -> {

            //ViewSwitcher.switchTo(View.UPPHAFSSENA);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            if (!mouseEvent.isConsumed()) {
                mouseEvent.consume();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Ertu viss?");
                alert.setHeaderText(null);
                alert.setContentText("Vilt þú fara til baka á upphafsskjá og hreinsa þema?");

                ButtonType yesButton = new ButtonType("Já", ButtonBar.ButtonData.OK_DONE);
                ButtonType noButton = new ButtonType("Nei", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(yesButton, noButton);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == yesButton) {
                    homeButton.getScene().getStylesheets().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vidmot/upphaf-view.fxml"));
                    try {
                        Parent root = loader.load();
                        Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
                        stage.setScene(scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    //ViewSwitcher.switchTo(View.UPPHAFSSENA);
                    timaController.setBot(null);
                }
            }

        }});
            homeButton.setOnMouseEntered(mouseEvent -> homeButton.setId("fxHiglightedHomeButton"));
            homeButton.setOnMouseExited(mouseEvent -> homeButton.setId("fxhomeButton"));
    }

    private void buildSoundButton() {
        soundButton = new Button();
        soundButton.setId("fxSoundButton");
        soundButton.minHeightProperty().bind(root.heightProperty().subtract(root.getPadding().getBottom() + root.getPadding().getTop()));
        soundButton.maxHeightProperty().bind(soundButton.minHeightProperty());
        soundButton.minWidthProperty().bind(soundButton.minHeightProperty());
        soundButton.maxWidthProperty().bind(soundButton.minHeightProperty());
        soundButton.getStylesheets().add(selectedStylesheet);

        // add image to the button
        ImageView imageView = new ImageView(new Image("file:./src/main/resources/vidmot/images/play.png", 20, 20, false, false));
        imageView.minWidth(soundButton.getWidth());
        imageView.maxWidth(soundButton.getWidth());
        imageView.minHeight(soundButton.getHeight());
        imageView.maxHeight(soundButton.getHeight());
        soundButton.setGraphic(imageView);

        // add event handler to play sound when button is clicked
        soundButton.setOnMouseClicked(mouseEvent -> {
            mediaPlayer.setMute(!mediaPlayer.isMute());

            ImageView image;
            if (mediaPlayer.isMute()) {
                image = new ImageView("file:./src/main/resources/vidmot/images/mute.png");
                image.setFitHeight(30);
                image.setFitWidth(25);
                image.setPreserveRatio(true);
            } else {
                image = new ImageView("file:./src/main/resources/vidmot/images/play.png");
                image.setFitHeight(30);
                image.setFitWidth(25);
                image.setPreserveRatio(true);
            }
            soundButton.setGraphic(image);
        });

        soundButton.setOnMouseEntered(mouseEvent -> soundButton.setId("fxHiglightedSoundButton"));
        soundButton.setOnMouseExited(mouseEvent -> soundButton.setId("fxSoundButton"));
    }
}
