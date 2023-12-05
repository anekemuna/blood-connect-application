package com.example.bloodconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("DashboardView.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.initialize(); // Initialize data on the dashboard

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Blood Connect - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}