package com.example.bloodconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnector {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/BloodDonationDB";
    private static final String USER = "blooddonationdb";
    private static final String PASSWORD = "blooddonationdb";

    // Establishes a connection to the database

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    // Closes the provided connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
}
