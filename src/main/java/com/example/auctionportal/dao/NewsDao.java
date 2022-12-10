package com.example.auctionportal.dao;

import com.example.auctionportal.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDao extends JpaRepository<News, Long> {

}
