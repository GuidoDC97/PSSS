package com.psss.registro;

import com.psss.registro.security.User;
import com.psss.registro.security.UserRepository;
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
        User segretario = new User("admin", passwordEncoder.encode("admin"));
        User antimo = new User("antimo", passwordEncoder.encode("leo"));

        segretario.addAuthority("SEGRETARIO");
        antimo.addAuthority("DOCENTE");

        userRepository.saveAndFlush(segretario);
        userRepository.saveAndFlush(antimo);
    }
}