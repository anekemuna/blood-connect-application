package com.example.bloodconnect;
import com.example.bloodconnect.dao.BloodDonationDAO;
import com.example.bloodconnect.dao.PatientDAO;
import com.example.bloodconnect.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class UpdatePatientFormController {

    //@FXML
    //private TextField patientIdField;
    private Stage stage;

    @FXML
    private TextField nameField;

    @FXML
    private TextField bloodGroupField;

    @FXML
    private TextField diseaseField;

    @FXML
    private TextField donationIdField;

    private PatientDAO patientDAO;
    private BloodDonationDAO bloodDonationDAO; // Assuming you have a BloodDonationDAO

    private Patient selectedPatient;

    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public void setBloodDonationDAO(BloodDonationDAO bloodDonationDAO) {
        this.bloodDonationDAO = bloodDonationDAO;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;

        // Populate fields with the selected patient's data
        //patientIdField.setText(String.valueOf(selectedPatient.getPatientId()));
        nameField.setText(selectedPatient.getName());
        bloodGroupField.setText(selectedPatient.getBloodGroup());
        diseaseField.setText(selectedPatient.getDisease());

        // Check if donation_id is not null before setting in the TextField
        donationIdField.setText(selectedPatient.getDonationId() != null
                ? String.valueOf(selectedPatient.getDonationId())
                : "");
    }

    @FXML
    private void handleUpdate() {
        // Validate input
        if (nameField.getText().isBlank()) {
            showAlert("Error", "Patient name cannot be blank.");
            return;
        }

        // Update the selected patient's data
        selectedPatient.setName(nameField.getText());
        selectedPatient.setBloodGroup(bloodGroupField.getText());
        selectedPatient.setDisease(diseaseField.getText());

        // Set the donation_id, if provided
        try {
            String donationIdText = donationIdField.getText().trim();
            if (!donationIdText.isEmpty()) {
                int donationId = Integer.parseInt(donationIdText);

                // Check if the provided donation_id exists in the blood_donation table
                if (bloodDonationDAO != null && !bloodDonationDAO.donationExists(donationId)) {
                    showAlert("Error", "The provided Donation ID does not exist in the Blood Donation records.");
                    return;
                }

                selectedPatient.setDonationId(donationId);
            } else {
                // If the field is empty, set donation_id to null
                selectedPatient.setDonationId(null);
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Donation ID. Please enter a valid number.");
            return;
        }

        // Show confirmation dialog
        boolean confirmed = showUpdateConfirmation(selectedPatient);

        // If the user confirms, update the patient in the database
        if (confirmed) {
            updatePatientInDatabase(selectedPatient);

            // Close the pop-up form
            closeForm();
        }
    }

    private void updatePatientInDatabase(Patient patient) {
        // Update the patient in the database using the PatientDAO
        if (patientDAO != null) {
            patientDAO.updatePatient(patient);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showUpdateConfirmation(Patient updatedPatient) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirm Update");
        confirmationAlert.setContentText("Do you want to update the following patient?\n\n" +
                "Patient ID: " + updatedPatient.getPatientId() + "\n" +
                "Name: " + updatedPatient.getName() + "\n" +
                "Blood Group: " + updatedPatient.getBloodGroup() + "\n" +
                "Disease: " + updatedPatient.getDisease() + "\n" +
                "Donation ID: " + updatedPatient.getDonationId());

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == okButton;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private void closeForm() {
        // Get the stage (window) that contains the form and close it
        if(stage != null) {
            stage.close();
        }
    }
}
