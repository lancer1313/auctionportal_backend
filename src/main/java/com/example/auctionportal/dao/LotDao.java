package com.example.auctionportal.dao;

import com.example.auctionportal.models.Lot;
import com.example.auctionportal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotDao extends JpaRepository<Lot, Long> {
    List<Lot> findAllByUser(User user);
}
