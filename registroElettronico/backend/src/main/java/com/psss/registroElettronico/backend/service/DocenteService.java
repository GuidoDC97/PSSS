package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @GetMapping
    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public List<Docente> findByNomeCognome(String nome, String cognome) {
        return docenteRepository.findByNomeCognome(nome, cognome);
    }

    public Docente saveAndFlush(Docente d) {
        return docenteRepository.saveAndFlush(d);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }
}
