package com.example.auctionportal.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class LotRequest {

    @NotBlank(message = "Это поле должно быть заполнено")
    private String lotTitle;
    @NotBlank(message = "Это поле должно быть заполнено")
    private String lotDescription;
    @Pattern(regexp = "[0-9]+", message = "Поле должно быть заполнено одним числом")
    private String startingPrice;
    @Pattern(regexp = "[0-9]+", message = "Поле должно быть заполнено одним числом")
    private String minimalStep;
    private MultipartFile file;

    public LotRequest(String lotTitle, String lotDescription, String startingPrice, String minimalStep, MultipartFile file) {
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

    public String getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(String startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getMinimalStep() {
        return minimalStep;
    }

    public void setMinimalStep(String minimalStep) {
        this.minimalStep = minimalStep;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
