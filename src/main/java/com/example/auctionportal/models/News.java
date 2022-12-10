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
    @Column(name = "text")
    private String text;
    @Column(name = "redactered")
    private boolean isRedactered;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private NewsFile image;

    public News(String title, String text, boolean isRedactered) {
        this.title = title;
        this.text = text;
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
