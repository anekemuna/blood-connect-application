package com.example.bloodconnect.model;

public class Patient {

    private int patientId;
    private String pName;
    private String pBloodGroup;
    private String disease;

    //???private int donationId;
    private Integer donationId; // Nullable, as donation_id can be null

    // Constructors, getters, and setters
    //??? public Patient(int patientId, String pName, String pBloodGroup, String disease, int donationId) {
    public Patient(int patientId, String pName, String pBloodGroup, String disease, Integer donationId) {
        this.patientId = patientId;
        this.pName = pName;
        this.pBloodGroup = pBloodGroup;
        this.disease = disease;
        this.donationId = donationId;
    }

    // Getters and setters

    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return pName;
    }

    public String getBloodGroup() {
        return pBloodGroup;
    }


    public String getDisease() {
        return disease;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public void setName(String pName) {
        this.pName = pName;
    }

    public void setBloodGroup(String pBloodGroup) {
        this.pBloodGroup = pBloodGroup;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }
}

