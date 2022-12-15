package com.example.auctionportal.dto;

public class NewsTimelineResponse {

    private String title;
    private String text;
    private String dateOfCreation;
    private boolean isRedactered;
    private String filePath;

    public NewsTimelineResponse(String title, String text, String dateOfCreation, boolean isRedactered, String filePath) {
        this.title = title;
        this.text = text;
        this.dateOfCreation = dateOfCreation;
        this.isRedactered = isRedactered;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public boolean isRedactered() {
        return isRedactered;
    }

    public void setRedactered(boolean redacterd) {
        isRedactered = redacterd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
