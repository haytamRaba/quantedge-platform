package com.quantedge.corebackend.repository;


import com.quantedge.corebackend.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {

    List<Portfolio> findByUserId(UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);
}