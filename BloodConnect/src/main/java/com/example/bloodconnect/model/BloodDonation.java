package com.example.bloodconnect.model;

import java.sql.Date;

public class BloodDonation {

    private int donationId;
    private Date donationDate;
    private String bloodGroup;
    private int donorId;
    private int bloodBankId;

    // Constructors, getters, and setters

    public BloodDonation(int donationId, Date donationDate, String bloodGroup, int donorId, int bloodBankId) {
        this.donationId = donationId;
        this.donationDate = donationDate;
        this.bloodGroup = bloodGroup;
        this.donorId = donorId;
        this.bloodBankId = bloodBankId;
    }

    // Getters and setters


    public int getDonorId() {
        return donorId;
    }

    public int getDonationId() {
        return donationId;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public int getBloodBankId() {
        return bloodBankId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }
}

