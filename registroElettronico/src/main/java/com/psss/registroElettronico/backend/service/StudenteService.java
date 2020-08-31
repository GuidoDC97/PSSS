package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.repository.StudenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }

    public Optional<Studente> findById(Long id) {
        return studenteRepository.findById(id);
    }

    public List<Studente> findByNomeAndCognome(String nome, String cognome) {
        return studenteRepository.findByNomeAndCognome(nome, cognome);
    }

    public List<Studente> findByCodiceFiscale(String codiceFiscale) {
        return studenteRepository.findByCodiceFiscale(codiceFiscale);
    }

    public Studente saveAndFlush(Studente s) {
        return studenteRepository.saveAndFlush(s);
    }

    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }
}
