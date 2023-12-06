package com.example.bloodconnect;

import com.example.bloodconnect.dao.DonorDAO;
import com.example.bloodconnect.model.Donor;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class DonorPageController {

    @FXML
    private TableView<Donor> donorTable;
    @FXML
    TableColumn<Donor, String> donorIdColumn;
    @FXML
    TableColumn<Donor, String> nameColumn;
    @FXML
    TableColumn<Donor, String> bloodGroupColumn;
    @FXML
    TableColumn<Donor, String> addressColumn;
    @FXML
    TableColumn<Donor, String> contactNoColumn;
    private DonorDAO donorDAO;
    private Stage stage;
    private Scene scene;

    @FXML
    private void initialize() {
        // Initialize the table columns
        //TableColumn<Donor, String> donorIdColumn = new TableColumn<>("ID");
        donorIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId())));

        //TableColumn<Donor, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        //TableColumn<Donor, String> bloodGroupColumn = new TableColumn<>("Blood Group");
        bloodGroupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBloodGroup()));

        //TableColumn<Donor, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        //TableColumn<Donor, String> contactNoColumn = new TableColumn<>("Contact No");
        contactNoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContactNo()));

        //donorTable.getColumns().addAll(donorIdColumn, nameColumn, bloodGroupColumn, addressColumn, contactNoColumn);
    }

    public void setDonorDAO(DonorDAO donorDAO) {
        this.donorDAO = donorDAO;
        viewDonors();
    }

    private void viewDonors() {
        if (donorDAO != null) {
            donorTable.getItems().setAll(donorDAO.getAllDonors());
        }
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        // Return to the dashboard
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DashboardView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Blood Connect - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
