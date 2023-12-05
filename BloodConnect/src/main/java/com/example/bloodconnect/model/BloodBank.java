package com.example.bloodconnect.model;

public class BloodBank {

    private int bloodBankId;
    private String bName;
    private String bAddress;
    private String bContactNo;

    // Constructors, getters, and setters

    public BloodBank(int bloodBankId, String bName, String bAddress, String bContactNo) {
        this.bloodBankId = bloodBankId;
        this.bName = bName;
        this.bAddress = bAddress;
        this.bContactNo = bContactNo;
    }

    // Getters and setters

    public int getBloodBankId() {
        return bloodBankId;
    }

    public String getName() {
        return bName;
    }

    public String getAddress() {
        return bAddress;
    }

    public String getContactNo() {
        return bContactNo;
    }
}
