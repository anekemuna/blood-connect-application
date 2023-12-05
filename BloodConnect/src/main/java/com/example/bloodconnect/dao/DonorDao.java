package com.example.bloodconnect.dao;

public class DonorDao {
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
}
