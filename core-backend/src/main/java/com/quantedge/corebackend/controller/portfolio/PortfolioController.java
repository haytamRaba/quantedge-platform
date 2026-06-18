package com.quantedge.corebackend.controller.portfolio;


import com.quantedge.corebackend.dto.portfolio.CreatePortfolioRequest;
import com.quantedge.corebackend.dto.portfolio.PortfolioResponse;
import com.quantedge.corebackend.service.portfolio.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    /**
     * POST /api/portfolios
     * Crée un nouveau portfolio pour l'utilisateur connecté.
     */
    @PostMapping
    public ResponseEntity<PortfolioResponse> createPortfolio(
            @Valid @RequestBody CreatePortfolioRequest request,
            Authentication authentication
    ) {
        PortfolioResponse createdPortfolio = portfolioService.createPortfolio(request, authentication);
        return new ResponseEntity<>(createdPortfolio, HttpStatus.CREATED);
    }

    /**
     * GET /api/portfolios
     * Récupère tous les portfolios de l'utilisateur connecté.
     */
    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getUserPortfolios(Authentication authentication) {
        List<PortfolioResponse> portfolios = portfolioService.getUserPortfolios(authentication);
        return ResponseEntity.ok(portfolios);
    }
}