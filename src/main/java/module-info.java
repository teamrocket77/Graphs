module com.example.displaygraph {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.displaygraph to javafx.fxml;
    exports com.example.displaygraph;
    exports TestingFiles;
    opens TestingFiles to javafx.fxml;
}