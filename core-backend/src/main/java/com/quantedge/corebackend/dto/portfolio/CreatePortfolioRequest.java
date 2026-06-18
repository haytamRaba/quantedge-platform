package com.quantedge.corebackend.dto.portfolio;


import com.quantedge.corebackend.model.enums.MarketType;
import com.quantedge.corebackend.model.enums.PortfolioType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePortfolioRequest {

    @NotBlank(message = "Le nom du portfolio est obligatoire")
    private String name;

    @NotNull(message = "Le type de trading est obligatoire")
    private PortfolioType type;

    @NotNull(message = "Le marché principal est obligatoire")
    private MarketType primaryMarket;

    @NotNull(message = "Le capital initial est obligatoire")
    @DecimalMin(value = "0.01", message = "Le capital doit être supérieur à 0")
    private BigDecimal initialCapital;
}