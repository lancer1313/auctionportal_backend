package com.example.auctionportal.models;

import javax.persistence.*;

@Entity
@Table(name = "lots")
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lot_title")
    private String lotTitle;
    @Column(name = "lot_description")
    private String lotDescription;
    @Column(name = "starting_page")
    private int startingPrice;
    @Column(name = "minimal_step")
    private int minimalStep;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private LotFile file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Lot(String lotTitle, String lotDescription, int startingPrice, int minimalStep) {
        this.lotTitle = lotTitle;
        this.lotDescription = lotDescription;
        this.startingPrice = startingPrice;
        this.minimalStep = minimalStep;
    }

    public Lot() {

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

    public LotFile getFile() {
        return file;
    }

    public void setFile(LotFile file) {
        this.file = file;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
