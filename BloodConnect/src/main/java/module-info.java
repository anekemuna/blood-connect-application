module com.example.bloodconnect {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bloodconnect to javafx.fxml;
    exports com.example.bloodconnect;
}