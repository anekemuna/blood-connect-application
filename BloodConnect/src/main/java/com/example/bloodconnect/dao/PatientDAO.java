package com.example.bloodconnect.dao;

import com.example.bloodconnect.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    /**
     *
     * @return the number of total patients in the database
     */
    public int getTotalPatients() {
        String query = "SELECT COUNT(*) FROM patient";
        int totalPatients = 0;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
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

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
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
