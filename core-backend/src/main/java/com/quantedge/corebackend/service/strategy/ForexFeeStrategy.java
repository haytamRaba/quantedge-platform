package com.quantedge.corebackend.service.strategy;


import com.quantedge.corebackend.model.enums.MarketType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ForexFeeStrategy implements FeeStrategy {
    @Override
    public BigDecimal calculateFees(BigDecimal amount) {
        // 0.05% de frais pour le Forex
        return amount.multiply(BigDecimal.valueOf(0.0005));
    }

    @Override
    public MarketType getMarketType() {
        return MarketType.FOREX;
    }
}