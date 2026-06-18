package com.quantedge.corebackend.service.trade;


import com.quantedge.corebackend.dto.trade.CreateTradeRequest;
import com.quantedge.corebackend.dto.trade.TradeResponse;
import com.quantedge.corebackend.model.Portfolio;
import com.quantedge.corebackend.model.Trade;
import com.quantedge.corebackend.model.enums.TradeType;
import com.quantedge.corebackend.repository.PortfolioRepository;
import com.quantedge.corebackend.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {

    private final TradeRepository tradeRepository;
    private final PortfolioRepository portfolioRepository;


    @Transactional // Tout ou rien : si une erreur survient, tout est annulé
    public TradeResponse executeTrade(CreateTradeRequest request) {


        Portfolio portfolio = portfolioRepository.findById(request.getPortfolioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Portfolio non trouvé avec l'ID : " + request.getPortfolioId()));

        BigDecimal totalAmount = request.getQuantity().multiply(request.getPrice());

        log.info("Exécution du trade : {} {} {} à {} = {}",
                request.getType(),
                request.getQuantity(),
                request.getSymbol(),
                request.getPrice(),
                totalAmount);


        if (request.getType() == TradeType.BUY) {
            if (portfolio.getCurrentBalance().compareTo(totalAmount) < 0) {
                throw new IllegalArgumentException(
                        "Solde insuffisant ! Solde actuel : " + portfolio.getCurrentBalance()
                                + "€, Montant requis : " + totalAmount + "€");
            }
            portfolio.setCurrentBalance(
                    portfolio.getCurrentBalance().subtract(totalAmount)
            );
            log.info("Solde après achat : {}€", portfolio.getCurrentBalance());
        }

        if (request.getType() == TradeType.SELL) {
            portfolio.setCurrentBalance(
                    portfolio.getCurrentBalance().add(totalAmount)
            );
            log.info("Solde après vente : {}€", portfolio.getCurrentBalance());
        }


        Trade trade = Trade.builder()
                .symbol(request.getSymbol())
                .type(request.getType())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .totalAmount(totalAmount)
                .portfolio(portfolio)
                .build();


        Trade savedTrade = tradeRepository.save(trade);
        portfolioRepository.save(portfolio); // Met à jour le solde

        log.info("✅ Trade exécuté avec succès ! ID : {}", savedTrade.getId());


        return TradeResponse.builder()
                .id(savedTrade.getId())
                .symbol(savedTrade.getSymbol())
                .type(savedTrade.getType())
                .quantity(savedTrade.getQuantity())
                .price(savedTrade.getPrice())
                .totalAmount(savedTrade.getTotalAmount())
                .tradeDate(savedTrade.getTradeDate())
                .portfolioId(portfolio.getId())
                .build();
    }


    @Transactional(readOnly = true)
    public List<TradeResponse> getPortfolioTrades(UUID portfolioId) {
        return tradeRepository.findByPortfolioId(portfolioId)
                .stream()
                .map(trade -> TradeResponse.builder()
                        .id(trade.getId())
                        .symbol(trade.getSymbol())
                        .type(trade.getType())
                        .quantity(trade.getQuantity())
                        .price(trade.getPrice())
                        .totalAmount(trade.getTotalAmount())
                        .tradeDate(trade.getTradeDate())
                        .portfolioId(portfolioId)
                        .build())
                .toList();
    }
}