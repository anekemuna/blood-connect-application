package com.example.bloodconnect;

import com.example.bloodconnect.dao.PatientDAO;
import com.example.bloodconnect.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class AddPatientFormController {

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField bloodGroupField;

    @FXML
    private TextField diseaseField;

    private PatientDAO patientDAO;

    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @FXML
    private void handleAdd() {
        // Validate input
        if (patientIdField.getText().isBlank() || nameField.getText().isBlank()) {
            showAlert("Error", "Patient ID and Name cannot be blank.");
            return;
        }

        // Convert patient ID to an integer
        int patientId = Integer.parseInt(patientIdField.getText());

        // Check if a patient with the same ID already exists
        if (patientExists(patientId)) {
            showAlert("Error", "A patient with the same ID already exists.");
            return;
        }

        // Create a new Patient object
        Patient newPatient = new Patient(patientId, nameField.getText(), bloodGroupField.getText(), diseaseField.getText(), null);

        // Show confirmation dialog
        boolean confirmed = showAddConfirmation(newPatient);

        // If the user confirms, add the patient to the database
        if (confirmed) {
            addPatientToDatabase(newPatient);

            // Close the pop-up form
            closeForm();
        }
    }

    private boolean patientExists(int patientId) {
        // Check if a patient with the given ID already exists in the database
        return patientDAO != null && patientDAO.patientExists(patientId);
    }

    private void addPatientToDatabase(Patient patient) {
        // Add the patient to the database using the PatientDAO
        if (patientDAO != null) {
            patientDAO.addPatient(patient);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean showAddConfirmation(Patient newPatient) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Confirm Addition");
        confirmationAlert.setContentText("Do you want to add the following patient?\n\n" +
                "Patient ID: " + newPatient.getPatientId() + "\n" +
                "Name: " + newPatient.getName() + "\n" +
                "Blood Group: " + newPatient.getBloodGroup() + "\n" +
                "Disease: " + newPatient.getDisease());

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        confirmationAlert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == okButton;
    }

    private void closeForm() {
        // Get the stage (window) that contains the form and close it
        Stage stage = (Stage) patientIdField.getScene().getWindow();
        stage.close();
    }
}

