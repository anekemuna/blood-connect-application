package com.example.bloodconnect.model;

public class Donor {
    private int donorId;
    private String dName;
    private String dBloodGroup;
    private String dAddress;
    private String dContactNo;

    public Donor(int donorId, String dName, String dBloodGroup, String dAddress, String dContactNo) {
        this.donorId = donorId;
        this.dName = dName;
        this.dBloodGroup = dBloodGroup;
        this.dAddress = dAddress;
        this.dContactNo = dContactNo;
    }

    public int getId() {
        return donorId;
    }

    public String getName() {
        return dName;
    }

    public String getBloodGroup() {
        return dBloodGroup;
    }

    public String getAddress() {
        return dAddress;
    }

    public String getContactNo() {
        return dContactNo;
    }
}
