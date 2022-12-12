package com.example.auctionportal.dto;

public class NewsTimelineResponse {

    private String title;
    private String text;
    private boolean isRedacterd;
    private String filePath;

    public NewsTimelineResponse(String title, String text, boolean isRedacterd, String filePath) {
        this.title = title;
        this.text = text;
        this.isRedacterd = isRedacterd;
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

    public boolean isRedacterd() {
        return isRedacterd;
    }

    public void setRedacterd(boolean redacterd) {
        isRedacterd = redacterd;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
