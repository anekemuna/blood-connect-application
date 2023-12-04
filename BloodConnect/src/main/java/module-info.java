module com.example.bloodconnect {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bloodconnect to javafx.fxml;
    exports com.example.bloodconnect;
}