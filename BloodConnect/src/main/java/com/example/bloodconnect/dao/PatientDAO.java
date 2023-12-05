package com.example.bloodconnect.dao;

import com.example.bloodconnect.DatabaseConnector;
import com.example.bloodconnect.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    private Connection connection;

    public PatientDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public List<Patient> getALlPatients()
    {
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
     *
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
     *
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
}
