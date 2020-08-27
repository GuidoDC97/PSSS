package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public List<Docente> findByNomeCognome(String nome, String cognome) {
        return docenteRepository.findByNomeCognome(nome, cognome);
    }

    public Docente save(Docente d) {
        return docenteRepository.save(d);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }
}
