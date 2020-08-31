package com.psss.registro.services;

import com.psss.registro.models.Docente;
import com.psss.registro.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public List<Docente> findAll(String filtro) {
        if(filtro == null || filtro.isEmpty()) {
            return docenteRepository.findAll();
        } else {
            return docenteRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(filtro, filtro);
        }
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente saveAndFlush(Docente d) {
        return docenteRepository.saveAndFlush(d);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }

    public Long deleteByNomeAndCognome(String nome, String cognome) {
        return docenteRepository.deleteByNomeAndCognome(nome, cognome);
    }
}
