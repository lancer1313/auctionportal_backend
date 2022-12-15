package com.example.auctionportal.models;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "text", length = 2000)
    private String text;
    @Column(name = "created_at")
    private String dateOfCreation;
    @Column(name = "redactered")
    private boolean isRedactered;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private NewsFile image;

    public News(String title, String text, String dateOfCreation, boolean isRedactered) {
        this.title = title;
        this.text = text;
        this.dateOfCreation = dateOfCreation;
        this.isRedactered = isRedactered;
    }

    public News() {

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

    public NewsFile getImage() {
        return image;
    }

    public void setImage(NewsFile image) {
        this.image = image;
    }
}
