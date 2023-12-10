package com.example.bloodconnect;

import com.example.bloodconnect.dao.BloodDonationDAO;
import com.example.bloodconnect.model.BloodDonation;
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


public class DonationPageController {

    @FXML
    TableColumn<BloodDonation, String> donationIdColumn;
    @FXML
    TableColumn<BloodDonation, String> dateColumn;
    @FXML
    TableColumn<BloodDonation, String> bloodGroupColumn;
    @FXML
    TableColumn<BloodDonation, String> donorIdColumn;
    @FXML
    TableColumn<BloodDonation, String> bloodBankIdColumn;
    @FXML
    private TableView<BloodDonation> donationTable;

    private BloodDonationDAO donationDAO;
    private Stage stage;
    private Scene scene;

    @FXML
    private void initialize() {
        // Initialize the table columns
        donationIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDonationId())));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDonationDate())));
        bloodGroupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBloodGroup()));
        donorIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDonorId())));
        bloodBankIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBloodBankId())));
    }

    public void setDonationDAO(BloodDonationDAO donationDAO) {
        this.donationDAO = donationDAO;
        viewDonations();
    }

    private void viewDonations() {
        if (donationDAO != null) {
            donationTable.getItems().setAll(donationDAO.getAllDonation());
        }
    }

    @FXML
    private void backToDashboard(ActionEvent event) throws IOException {
        // Return to the dashboard
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("DashboardView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Blood Connect - Dashboard");
        stage.setScene(scene);
        stage.show();
    }

}
