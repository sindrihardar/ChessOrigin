package vidmot.nodes;

import presenter.GameMediator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GameNode extends Pane {
    private static final double TIME_BOX_SPACE_PERCENTAGE = 0.4;
    private VBox infoBox;
    private HBox root;
    private BoardNode board;
    private TimerNode whiteTimer, blackTimer;
    private NotationNode notationNode;
    private GameMediator gameMediator;

    public GameNode(GameMediator gameMediator) {
        this.gameMediator = gameMediator;
        board = new BoardNode(gameMediator.getBoardPresenter());
        build();
    }


    public void build() {
        root = new HBox();
        getChildren().add(root);
        root.minWidthProperty().set(400);
        root.maxWidthProperty().set(400);
        root.minHeightProperty().set(400);
        root.maxHeightProperty().set(400);
        root.setPadding(new Insets(0, 0, 0, 0));
        root.setSpacing(0);

        // add a pane for the board to live in, add a VBox for the timers
        board.minWidthProperty().set(400);
        board.maxWidthProperty().set(400);
        board.minHeightProperty().set(400);
        board.maxHeightProperty().set(400);
        board.setPadding(new Insets(0, 0, 0, 0));
        root.getChildren().add(board);
        HBox.setMargin(board, new Insets(0, 0, 0, 0));
        HBox.setHgrow(board, Priority.ALWAYS);
    }
}
