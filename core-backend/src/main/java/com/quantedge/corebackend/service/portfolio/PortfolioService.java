package com.quantedge.corebackend.service.portfolio;

import com.quantedge.corebackend.dto.portfolio.CreatePortfolioRequest;
import com.quantedge.corebackend.dto.portfolio.PortfolioResponse;
import com.quantedge.corebackend.model.Portfolio;
import com.quantedge.corebackend.model.User;
import com.quantedge.corebackend.repository.PortfolioRepository;
import com.quantedge.corebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;


    public PortfolioResponse createPortfolio(CreatePortfolioRequest request, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        log.info("Création d'un portfolio '{}' pour l'utilisateur {}", request.getName(), email);

        Portfolio portfolio = Portfolio.builder()
                .name(request.getName())
                .type(request.getType())
                .primaryMarket(request.getPrimaryMarket())
                .initialCapital(request.getInitialCapital())
                .user(user)
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        log.info("Portfolio créé avec l'ID : {}", savedPortfolio.getId());

        return mapToResponse(savedPortfolio);
    }

    /**
     * Récupère tous les portfolios de l'utilisateur authentifié
     */
    @Transactional(readOnly = true)
    public List<PortfolioResponse> getUserPortfolios(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        return portfolioRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PortfolioResponse mapToResponse(Portfolio portfolio) {
        return PortfolioResponse.builder()
                .id(portfolio.getId())
                .name(portfolio.getName())
                .type(portfolio.getType())
                .primaryMarket(portfolio.getPrimaryMarket())
                .initialCapital(portfolio.getInitialCapital())
                .currentBalance(portfolio.getCurrentBalance())
                .createdAt(portfolio.getCreatedAt())
                .build();
    }
}