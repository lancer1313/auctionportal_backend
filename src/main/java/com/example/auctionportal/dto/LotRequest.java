package com.example.auctionportal.dto;

import org.springframework.web.multipart.MultipartFile;

public class LotRequest {

    private String lotTitle;
    private String lotDescription;
    private int startingPrice;
    private int minimalStep;
    private MultipartFile file;

    public LotRequest(String lotTitle, String lotDescription, int startingPrice, int minimalStep, MultipartFile file) {
        this.lotTitle = lotTitle;
        this.lotDescription = lotDescription;
        this.startingPrice = startingPrice;
        this.minimalStep = minimalStep;
        this.file = file;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
