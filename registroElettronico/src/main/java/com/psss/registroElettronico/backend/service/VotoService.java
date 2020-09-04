package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.model.Voto;
import com.psss.registroElettronico.backend.repository.StudenteRepository;
import com.psss.registroElettronico.backend.repository.VotoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    public List<Voto> findAll() {
        return votoRepository.findAll();
    }

    public Optional<Voto> findById(Long id) {
        return votoRepository.findById(id);
    }

    public List<Voto> findByDocente(Long id){
        return votoRepository.findByDocente(id);
    }

    public List<Voto> findByStudente(Long id){
        return votoRepository.findByDocente(id);
    }

    public Voto saveAndFlush(Voto v) {
        return votoRepository.saveAndFlush(v);
    }

    public void deleteById(Long id) {
        votoRepository.deleteById(id);
    }
}
