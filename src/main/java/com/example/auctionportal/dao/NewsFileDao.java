package com.example.auctionportal.dao;

import com.example.auctionportal.models.NewsFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFileDao extends JpaRepository<NewsFile, Long> {

}
