package com.quantedge.corebackend.config;


import com.quantedge.corebackend.model.User;
import com.quantedge.corebackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        log.info("=== DÉMARRAGE DU TEST DE LA BD ===");

//        User testUser = User.builder()
//                .email("trader@test.com")
//                .password("password123")
//                .build();
//
//        User savedUser = userRepository.save(testUser);
//        log.info("Utilisateur créé avec ID : {}", savedUser.getId());
//
//        var foundUser = userRepository.findByEmail("trader@test.com");
//
//        if (foundUser.isPresent()) {
//            log.info(" Utilisateur trouvé : {}", foundUser.get().getEmail());
//        } else {
//            log.error(" Utilisateur non trouvé !");
//        }
//
//        var allUsers = userRepository.findAll();
//        log.info(" Nombre total d'utilisateurs : {}", allUsers.size());

        log.info("=== FIN DU TEST ===");
    }
}