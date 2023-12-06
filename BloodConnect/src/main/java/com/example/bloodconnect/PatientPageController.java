package com.example.bloodconnect;

import com.example.bloodconnect.dao.BloodDonationDAO;
import com.example.bloodconnect.dao.PatientDAO;
import com.example.bloodconnect.model.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


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
    private BloodDonationDAO bloodDonationDAO;
    private Stage stage;
    private Scene scene;

    @FXML
    private void initialize() throws SQLException {

        bloodDonationDAO = new BloodDonationDAO(DatabaseConnector.getConnection());

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
     *
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
     *
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

    @FXML
    private void handleAddPatient() {
        Stage addPatientStage = new Stage();
        addPatientStage.setTitle("Add New Patient");

        try {
            // Load the FXML file for the pop-up form
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("AddPatientFormView.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            // Set the scene
            addPatientStage.setScene(scene);

            // Set the PatientDAO in the AddPatientFormController
            AddPatientFormController addPatientFormController = loader.getController();
            addPatientFormController.setPatientDAO(patientDAO);

            // Set the stage as a modal window
            addPatientStage.initModality(Modality.APPLICATION_MODAL);

            // Set the stage as the owner of the pop-up form
            addPatientStage.initOwner(patientTable.getScene().getWindow());

            // Show the pop-up form
            addPatientStage.showAndWait();

            // After the form is closed, refresh the patient table
            viewPatients();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    @FXML
    private void handleUpdatePatient() {
        // Check if a patient is selected in the table
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            showAlert("Error", "Please select a patient to update.");
            return;
        }

        // Create a new Stage for the pop-up form
        Stage updatePatientStage = new Stage();
        updatePatientStage.setTitle("Update Patient");

        try {
            // Load the FXML file for the pop-up form
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("UpdatePatientFormView.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            // Set the scene
            updatePatientStage.setScene(scene);

            // Set the PatientDAO in the UpdatePatientFormController
            UpdatePatientFormController updatePatientFormController = loader.getController();
            updatePatientFormController.setPatientDAO(patientDAO);
            updatePatientFormController.setBloodDonationDAO(bloodDonationDAO);
            updatePatientFormController.setSelectedPatient(selectedPatient);

            // Set the stage as a modal window
            updatePatientStage.initModality(Modality.APPLICATION_MODAL);

            // Set the stage as the owner of the pop-up form
            updatePatientStage.initOwner(patientTable.getScene().getWindow());
            updatePatientFormController.setStage(updatePatientStage);
            // Show the pop-up form
            updatePatientStage.showAndWait();

            // After the form is closed, refresh the patient table
            viewPatients();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /**
     * Pop Dialog Box with content message
     * @param title
     * @param content
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
