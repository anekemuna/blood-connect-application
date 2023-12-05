package com.example.bloodconnect;

import com.example.bloodconnect.dao.PatientDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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

    private final PatientDAO patientDAO = new PatientDAO();

    // Initialize data on the dashboard
    public void initialize() {
        int totalPatients = patientDAO.getTotalPatients();
        int patientsWithNullDonationId = patientDAO.getPatientsWithNullDonationIdCount();

        // Update UI elements
        patientTotalLabel.setText(""+totalPatients);
        patientUnassignedLabel.setText(""+patientsWithNullDonationId);
    }
}