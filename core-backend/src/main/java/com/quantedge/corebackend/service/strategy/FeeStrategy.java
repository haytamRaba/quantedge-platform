package com.quantedge.corebackend.service.strategy;

import com.quantedge.corebackend.model.enums.MarketType;

import java.math.BigDecimal;

public interface FeeStrategy {
    BigDecimal calculateFees(BigDecimal amount);

     MarketType getMarketType();
}