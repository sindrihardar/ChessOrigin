module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chess to javafx.fxml;
    exports com.chess.view;
    exports com.chess.model.pieces;
    exports com.chess.model.util;
    exports com.chess.model.ai;
    exports com.chess.model.game;
    exports com.chess.view.nodes;
    exports com.chess.view.scenes;
}