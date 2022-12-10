package com.example.auctionportal.dao;

import com.example.auctionportal.models.LotFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotFileDao extends JpaRepository<LotFile, Long> {

}
