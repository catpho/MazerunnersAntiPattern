module com.mazeruners.mazerunners {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.mazerunners to javafx.fxml;
    exports com.mazerunners;
}