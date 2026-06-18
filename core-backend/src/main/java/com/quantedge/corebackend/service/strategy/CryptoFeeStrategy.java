package com.quantedge.corebackend.service.strategy;

import com.quantedge.corebackend.model.enums.MarketType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class CryptoFeeStrategy implements FeeStrategy {
    @Override
    public BigDecimal calculateFees(BigDecimal amount) {

        return amount.multiply(BigDecimal.valueOf(0.015));
    }

    @Override
    public MarketType getMarketType() {
        return MarketType.CRYPTO;
    }
}