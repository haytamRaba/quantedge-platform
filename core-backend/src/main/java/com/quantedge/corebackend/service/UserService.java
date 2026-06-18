package com.quantedge.corebackend.service;


import com.quantedge.corebackend.dto.user.CreateUserRequest;
import com.quantedge.corebackend.dto.user.UserResponse;
import com.quantedge.corebackend.model.User;
import com.quantedge.corebackend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserResponse createUser(CreateUserRequest request) {
        log.info("Création d'un utilisateur avec l'email : {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        User savedUser = userRepository.save(user);
        log.info("Utilisateur créé avec l'ID : {}", savedUser.getId());

        return mapToResponse(savedUser);
    }

    /**
     * Récupère tous les utilisateurs.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Méthode privée pour mapper Entité -> DTO
     */
    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .build();
    }
}