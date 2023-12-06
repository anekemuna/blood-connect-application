package com.example.bloodconnect.dao;

import com.example.bloodconnect.model.Donor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    private Connection connection;

    /**
     * initiate connection
     * @param connection
     */
    public DonorDAO(Connection connection) {
        this.connection = connection;
    }


    /**
     *
     * @return Return a list of all the donors
     */
    public List<Donor> getAllDonors() {
        List<Donor> donors = new ArrayList<>();
        String query = "SELECT * FROM donor";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int donorId = resultSet.getInt("donor_id");
                String name = resultSet.getString("d_name");
                String bloodGroup = resultSet.getString("d_blood_group");
                String address = resultSet.getString("d_address");
                String contactNo = resultSet.getString("d_contact_no");

                Donor donor = new Donor(donorId, name, bloodGroup, address, contactNo);
                donors.add(donor);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception
        }

        return donors;
    }
}
