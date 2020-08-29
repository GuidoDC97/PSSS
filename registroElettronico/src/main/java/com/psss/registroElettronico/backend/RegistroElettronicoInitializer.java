package com.psss.registroElettronico.backend;

import com.psss.registroElettronico.backend.sicurezza.User;
import com.psss.registroElettronico.backend.sicurezza.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistroElettronicoInitializer implements SmartInitializingSingleton {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void afterSingletonsInstantiated() {

        // Decommentare la prima volta per popolare il database
        User fabio = new User("fabio", passwordEncoder.encode("leo"));
        User antimo = new User("antimo", passwordEncoder.encode("leo"));

        fabio.addAuthority("READ");
        fabio.addAuthority("WRITE");
        antimo.addAuthority("READ");

        userRepository.saveAndFlush(fabio);
        userRepository.saveAndFlush(antimo);
    }
}
