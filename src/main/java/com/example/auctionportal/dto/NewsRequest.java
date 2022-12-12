package com.example.auctionportal.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class NewsRequest {

    @NotBlank(message = "Дайте новости название")
    private String title;
    @NotBlank(message = "Новость требует текстовое сопровождение")
    private String text;
    private MultipartFile image;

    public NewsRequest(String title, String text, MultipartFile image) {
        this.title = title;
        this.text = text;
        this.image = image;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
