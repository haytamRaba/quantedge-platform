package com.quantedge.corebackend.controller.trade;


import com.quantedge.corebackend.dto.trade.CreateTradeRequest;
import com.quantedge.corebackend.dto.trade.TradeResponse;
import com.quantedge.corebackend.service.trade.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<TradeResponse> executeTrade(@Valid @RequestBody CreateTradeRequest request) {
        TradeResponse response = tradeService.executeTrade(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<TradeResponse>> getPortfolioTrades(@PathVariable UUID portfolioId) {
        return ResponseEntity.ok(tradeService.getPortfolioTrades(portfolioId));
    }
}