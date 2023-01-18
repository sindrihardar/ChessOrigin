module com.example.chess {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chess to javafx.fxml;
    exports com.chess.model;
    exports com.chess.view;
    exports com.chess.model.pieces;
    exports com.chess.model.util;
}