package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Ruolo;
import com.psss.registroElettronico.backend.repository.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;

    public List<Ruolo> findAll() {
        return ruoloRepository.findAll();
    }

    public Optional<Ruolo> findById(Long id) {
        return ruoloRepository.findById(id);
    }

    public Ruolo saveAndFlush(Ruolo r) {
        return ruoloRepository.saveAndFlush(r);
    }

    public void deleteById(Long id) {
        ruoloRepository.deleteById(id);
    }
}
