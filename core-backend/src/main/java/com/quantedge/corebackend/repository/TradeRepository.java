package com.quantedge.corebackend.repository;


import com.quantedge.corebackend.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TradeRepository extends JpaRepository<Trade, UUID> {

    List<Trade> findByPortfolioId(UUID portfolioId);
}