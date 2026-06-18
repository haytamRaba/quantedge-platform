
package com.quantedge.corebackend.dto.portfolio;

import com.quantedge.corebackend.model.enums.MarketType;
import com.quantedge.corebackend.model.enums.PortfolioType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponse {
    private UUID id;
    private String name;
    private PortfolioType type;
    private MarketType primaryMarket;
    private BigDecimal initialCapital;
    private BigDecimal currentBalance;
    private LocalDateTime createdAt;
}