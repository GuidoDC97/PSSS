package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Utente;
import com.psss.registroElettronico.backend.repository.RuoloRepository;
import com.psss.registroElettronico.backend.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RuoloRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
    public Utente saveAndFlush(Utente u) {
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        return utenteRepository.saveAndFlush(u);
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }
}
