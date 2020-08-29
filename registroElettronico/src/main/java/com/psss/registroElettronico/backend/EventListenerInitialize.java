package com.psss.registroElettronico.backend;

import com.psss.registroElettronico.backend.sicurezza.User;
import com.psss.registroElettronico.backend.sicurezza.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EventListenerInitialize {

    private static final Logger logger = LoggerFactory.getLogger(EventListenerInitialize.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        logger.info("Initializing database");

        // Decommentare la prima volta per popolare il database
//        User fabio = new User("fabio", passwordEncoder.encode("leo"));
//        User antimo = new User("antimo", passwordEncoder.encode("leo"));
//
//        fabio.addAuthority("READ");
//        fabio.addAuthority("WRITE");
//        antimo.addAuthority("READ");
//
//        userRepository.saveAndFlush(fabio);
//        userRepository.saveAndFlush(antimo);
    }
}
