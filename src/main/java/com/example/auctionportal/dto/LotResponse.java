package com.example.auctionportal.dto;

public class LotResponse {

    private Long id;
    private String lotTitle;
    private String lotDescription;
    private int startingPrice;
    private int minimalStep;
    private String fileName;
    private String fileContentType;

    public LotResponse(Long id, String lotTitle, String lotDescription, int startingPrice, int minimalStep,
                       String fileName, String fileContentType) {
        this.id = id;
        this.lotTitle = lotTitle;
        this.lotDescription = lotDescription;
        this.startingPrice = startingPrice;
        this.minimalStep = minimalStep;
        this.fileName = fileName;
        this.fileContentType = fileContentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotTitle() {
        return lotTitle;
    }

    public void setLotTitle(String lotTitle) {
        this.lotTitle = lotTitle;
    }

    public String getLotDescription() {
        return lotDescription;
    }

    public void setLotDescription(String lotDescription) {
        this.lotDescription = lotDescription;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getMinimalStep() {
        return minimalStep;
    }

    public void setMinimalStep(int minimalStep) {
        this.minimalStep = minimalStep;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
