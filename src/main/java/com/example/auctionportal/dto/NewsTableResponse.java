package com.example.auctionportal.dto;

public class NewsTableResponse {

    private Long id;
    private String title;
    private String text;
    private String dateOfCreation;
    private boolean isRedactered;
    private String fileName;

    public NewsTableResponse(Long id, String title, String text, String dateOfCreation, boolean isRedactered, String fileName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.dateOfCreation = dateOfCreation;
        this.isRedactered = isRedactered;
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setRedactered(boolean redactered) {
        isRedactered = redactered;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
