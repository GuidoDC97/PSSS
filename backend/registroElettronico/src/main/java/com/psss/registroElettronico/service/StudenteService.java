package com.psss.registroElettronico.service;

import com.psss.registroElettronico.model.Studente;
import com.psss.registroElettronico.repository.StudenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
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

    public Studente saveAndFlush(Studente s) {
        return studenteRepository.saveAndFlush(s);
    }

    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }
}
