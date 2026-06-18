package com.quantedge.corebackend.dto.trade;

import com.quantedge.corebackend.model.enums.TradeType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTradeRequest {

    @NotNull(message = "L'ID du portfolio est obligatoire")
    private UUID portfolioId;

    @NotBlank(message = " symbole est obligatoire (ex: AAPL, BTC/USD)")
    private String symbol;

    @NotNull(message = "type de trade est obligatoire (BUY ou SELL)")
    private TradeType type;

    @NotNull(message = "quantité est obligatoire")
    @DecimalMin(value = "0.0001", message = "La quantité doit être supérieure à 0")
    private BigDecimal quantity;

    @NotNull(message = "Le prix unitaire est obligatoire")
    @DecimalMin(value = "0.01", message = "prix doit être supérieur à 0")
    private BigDecimal price;
}