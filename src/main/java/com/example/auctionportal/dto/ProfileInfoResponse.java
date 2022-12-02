package com.example.auctionportal.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileInfoResponse {
    private String firstName;
    private String lastName;
    private int loginsCount;
    private String dateAndTime;

    public ProfileInfoResponse(String firstname, String lastName, int loginsCount) {
        this.firstName = firstname;
        this.lastName = lastName;
        this.loginsCount = loginsCount;

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd в HH:mm:ss");
        this.dateAndTime = simpleDateFormat.format(date);
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }
}
