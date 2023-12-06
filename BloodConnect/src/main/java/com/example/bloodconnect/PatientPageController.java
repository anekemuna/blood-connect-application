package com.example.bloodconnect;

import com.example.bloodconnect.dao.PatientDAO;
import com.example.bloodconnect.model.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class PatientPageController {

    @FXML
    TableColumn<Patient, String> patientIdColumn;
    @FXML
    TableColumn<Patient, String> nameColumn;
    @FXML
    TableColumn<Patient, String> bloodGroupColumn;
    @FXML
    TableColumn<Patient, String> diseaseColumn;
    @FXML
    TableColumn<Patient, String> donationIdColumn;

    @FXML
    TableColumn<Patient, Void> deleteColumn;
    @FXML
    private TableView<Patient> patientTable;

    private PatientDAO patientDAO;
    private Stage stage;
    private Scene scene;

    @FXML
    private void initialize() {
        // Initialize the table columns
        patientIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPatientId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        bloodGroupColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBloodGroup()));
        diseaseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDisease()));
        donationIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDonationId())));

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    confirmAndDelete(patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    /**
     * Confirm and delete patient from table (dialog box)
     * @param patient
     */
    private void confirmAndDelete(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Patient Record");
        alert.setContentText("Are you sure you want to delete this patient record?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                deletePatient(patient);
            }
        });
    }

    /**
     * Delete and Update the table
     * @param patient
     */
    private void deletePatient(Patient patient) {
        if (patientDAO != null) {
            patientDAO.deletePatient(patient);
            viewPatients(); // Refresh the table after deletion
        }
    }

    public void setPatientDAO(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
        viewPatients();
    }

    /**
     * Sets the tables
     */
    private void viewPatients() {
        if (patientDAO != null) {
            patientTable.getItems().setAll(patientDAO.getALlPatients());
        }
    }

    /**
     * add patient
     */

    /*
    @FXML
    private void handleAddPatient() {
        String patientIdText = patientIdField.getText();
        String name = nameField.getText();

        // Validate input
        if (patientIdText.isBlank() || name.isBlank()) {
            showAlert("Error", "Patient ID and Name cannot be blank.");
            return;
        }

        // Convert patient ID to an integer
        int patientId = Integer.parseInt(patientIdText);

        // Check if a patient with the same ID already exists
        if (patientExists(patientId)) {
            showAlert("Error", "A patient with the same ID already exists.");
            return;
        }

        // Create a new Patient object
        Patient newPatient = new Patient(patientId, name, bloodGroupField.getText(), diseaseField.getText(), null);

        // Show confirmation dialog
        boolean confirmed = showAddConfirmation(newPatient);

        // If the user confirms, add the patient to the database
        if (confirmed) {
            patientDAO.addPatient(newPatient);

            // Refresh the table
            viewPatients();
        }
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

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
*/
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Returns true if patient exists
     * @param patientId
     * @return
     */
    private boolean patientExists(int patientId) {
        // PatientDAO has a method to check if a patient exists
        return patientDAO != null && patientDAO.patientExists(patientId);
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
