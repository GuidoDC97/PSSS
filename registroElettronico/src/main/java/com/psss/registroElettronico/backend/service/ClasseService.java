package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Classe;
import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.repository.ClasseRepository;
import com.psss.registroElettronico.backend.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    public Optional<Classe> findById(Long id) {
        return classeRepository.findById(id);
    }

    public List<Classe> findByAnnoScolastico(int anno) {
        return classeRepository.findByAnnoScolastico(anno);
    }

    public List<Classe> findByAnnoScolasticoAndSezione(int anno, Character sezione){
        return classeRepository.findByAnnoScolasticoAndSezione(anno, sezione);
    }

    public Classe saveAndFlush(Classe c) {
        return classeRepository.saveAndFlush(c);
    }

    public void deleteById(Long id) {
        classeRepository.deleteById(id);
    }



}
