package com.quantedge.corebackend.service.auth;


import com.quantedge.corebackend.dto.user.AuthResponse;
import com.quantedge.corebackend.dto.user.CreateUserRequest;
import com.quantedge.corebackend.dto.user.LoginRequest;
import com.quantedge.corebackend.model.User;
import com.quantedge.corebackend.repository.UserRepository;
import com.quantedge.corebackend.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * INSCRIPTION
     */
    public AuthResponse register(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .build();
    }

    /**
     * CONNEXION
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}
