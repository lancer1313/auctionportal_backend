package com.example.auctionportal.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileInfoResponse {
    private String firstName;
    private String lastName;
    private int loginsCount;
    private String role;

    public ProfileInfoResponse(String firstName, String lastName, int loginsCount, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginsCount = loginsCount;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getLoginsCount() {
        return loginsCount;
    }

    public void setLoginsCount(int loginsCount) {
        this.loginsCount = loginsCount;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
