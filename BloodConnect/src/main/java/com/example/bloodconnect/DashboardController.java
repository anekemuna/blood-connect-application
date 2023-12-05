package com.example.bloodconnect;

import com.example.bloodconnect.dao.BloodBankDAO;
import com.example.bloodconnect.dao.BloodDonationDAO;
import com.example.bloodconnect.dao.DonorDAO;
import com.example.bloodconnect.dao.PatientDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardController {

    @FXML
    private Label patientTotalLabel;

    @FXML
    private Label patientUnassignedLabel;

    @FXML
    private Button patientButton;
    @FXML
    private Button donorButton;
    @FXML
    private Button donationButton;
    @FXML
    private Button bloodBankButton;

    private Stage stage;
    private Scene scene;

    private final PatientDAO patientDAO = new PatientDAO(DatabaseConnector.getConnection());

    public DashboardController() throws SQLException {
    }


    // Initialize data on the dashboard
    public void initialize() {
        int totalPatients = patientDAO.getTotalPatients();
        int patientsWithNullDonationId = patientDAO.getPatientsWithNullDonationIdCount();

        // Update UI elements
        patientTotalLabel.setText(""+totalPatients);
        patientUnassignedLabel.setText(""+patientsWithNullDonationId);
    }
    @FXML
    public void openDonorPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("DonorPageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load(), 600, 400);
            stage.setTitle("Blood Connect - Donor DB");


            DonorPageController donorPageController = loader.getController();
            donorPageController.setDonorDAO(new DonorDAO(DatabaseConnector.getConnection()));

            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    public void openPatientPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("PatientPageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load(), 600, 400);
            stage.setTitle("Blood Connect - Patient DB");

            PatientPageController patientPageController = loader.getController();
            patientPageController.setPatientDAO(new PatientDAO(DatabaseConnector.getConnection()));

            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    public void openDonationPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("DonationPageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load(), 600, 400);
            stage.setTitle("Blood Connect - Donation DB");


            DonationPageController donationPageController = loader.getController();
            donationPageController.setDonationDAO(new BloodDonationDAO(DatabaseConnector.getConnection()));

            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    public void openBankPage(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("BankPageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(loader.load(), 600, 400);
            stage.setTitle("Blood Connect - Bank DB");


            BankPageController bankPageController = loader.getController();
            bankPageController.setBankDAO(new BloodBankDAO(DatabaseConnector.getConnection()));

            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}
