package com.example.auctionportal.dao;

import com.example.auctionportal.models.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFileDao extends JpaRepository<UserFile, Long> {

}
