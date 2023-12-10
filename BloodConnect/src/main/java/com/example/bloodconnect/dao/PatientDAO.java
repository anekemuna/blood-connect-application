package com.example.bloodconnect.dao;

import com.example.bloodconnect.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private final Connection connection;

    public PatientDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public List<Patient> getALlPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patient";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int patientId = resultSet.getInt("patient_id");
                String name = resultSet.getString("p_name");
                String bloodGroup = resultSet.getString("p_blood_group");
                String disease = resultSet.getString("disease");
                Integer donationId = resultSet.getInt("donation_id");

                Patient patient = new Patient(patientId, name, bloodGroup, disease, donationId);
                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception
        }

        return patients;
    }

    /**
     * @return the number of total patients in the database
     */
    public int getTotalPatients() {
        String query = "SELECT COUNT(*) FROM patient";
        int totalPatients = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                totalPatients = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return totalPatients;
    }

    /**
     * @return the number of patients with no donation attached to them
     */
    public int getPatientsWithNullDonationIdCount() {
        String query = "SELECT COUNT(*) FROM patient WHERE donation_id IS NULL";
        int patientsWithNullDonationId = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                patientsWithNullDonationId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return patientsWithNullDonationId;
    }

    /**
     * delete an existing patient
     * @param patient
     */
    public void deletePatient(Patient patient) {
        String query = "DELETE FROM patient WHERE patient_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patient.getPatientId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /**
     * adds a new patient
     * @param patient
     */
    public void addPatient(Patient patient) {
        String query = "INSERT INTO patient (patient_id, p_name, p_blood_group, disease) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patient.getPatientId());
            statement.setString(2, patient.getName());
            statement.setString(3, patient.getBloodGroup());
            statement.setString(4, patient.getDisease());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    /**
     *
     * @param patientId
     * @return True if the patientID is in the database
     */

    public boolean patientExists(int patientId) {
        String query = "SELECT COUNT(*) FROM patient WHERE patient_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return false;
    }

    /**
     * update a patient info
     * @param patient
     */
    public void updatePatient(Patient patient) {
        String query = "UPDATE patient SET p_name=?, p_blood_group=?, disease=?, donation_id=? WHERE patient_id=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patient.getName());
            statement.setString(2, patient.getBloodGroup());
            statement.setString(3, patient.getDisease());
            statement.setInt(4, patient.getDonationId());
            statement.setInt(5, patient.getPatientId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    // Method to search patients by ID
    public List<Patient> searchPatientsById(String patientIdQuery) {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patient WHERE patient_id = ?"; // Using exact match instead of LIKE for ID

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(patientIdQuery)); // Assuming patient_id is an integer

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int patientId = resultSet.getInt("patient_id");
                    String name = resultSet.getString("p_name");
                    String bloodGroup = resultSet.getString("p_blood_group");
                    String disease = resultSet.getString("disease");
                    int donationId = resultSet.getInt("donation_id");

                    patients.add(new Patient(patientId, name, bloodGroup, disease, donationId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions
        }
        return patients;
    }


}