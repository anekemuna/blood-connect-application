package com.example.bloodconnect.dao;

import com.example.bloodconnect.model.BloodBank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BloodBankDAO {
    private Connection connection;

    /**
     * initiate connection
     * @param connection
     */
    public BloodBankDAO(Connection connection) {
        this.connection = connection;
    }


    /**
     *
     * @return Return a list of all the donors
     */
    public List<BloodBank> getAllBank() {
        List<BloodBank> banks = new ArrayList<>();
        String query = "SELECT * FROM blood_bank";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int bankId = resultSet.getInt("blood_bank_id");
                String name = resultSet.getString("b_name");
                String address = resultSet.getString("b_address");
                String contactNo = resultSet.getString("b_contact_no");

                BloodBank bank = new BloodBank(bankId, name, address, contactNo);
                banks.add(bank);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception
        }

        return banks;
    }
}