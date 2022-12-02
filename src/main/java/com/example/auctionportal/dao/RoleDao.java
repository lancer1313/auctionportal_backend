package com.example.auctionportal.dao;

import com.example.auctionportal.models.ERole;
import com.example.auctionportal.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
