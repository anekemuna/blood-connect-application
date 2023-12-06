package com.example.bloodconnect.dao;

import com.example.bloodconnect.model.BloodDonation;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodDonationDAO {
    private Connection connection;

    /**
     * initiate connection
     * @param connection
     */
    public BloodDonationDAO(Connection connection) {
        this.connection = connection;
    }


    /**
     *
     * @return Return a list of all the donors
     */
    public List<BloodDonation> getAllDonation() {
        List<BloodDonation> donations = new ArrayList<>();
        String query = "SELECT * FROM blood_donation";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int donationId = resultSet.getInt("donor_id");
                Date date = resultSet.getDate("donation_date");
                String bloodGroup = resultSet.getString("blood_group");
                int donorId = resultSet.getInt("donor_id");
                int bankId = resultSet.getInt("blood_bank_id");

                BloodDonation donation = new BloodDonation(donationId, date, bloodGroup, donorId, bankId);
                donations.add(donation);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception
        }

        return donations;
    }

    public boolean donationExists(int donationId) {
        String query = "SELECT COUNT(*) FROM blood_donation WHERE donation_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, donationId);

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
}