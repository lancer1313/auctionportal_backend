package com.example.auctionportal.dto;

import org.springframework.web.multipart.MultipartFile;

public class AvatarRequest {
    private Long id;
    private MultipartFile image;

    public AvatarRequest(Long id, MultipartFile image) {
        this.id = id;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
