package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Materia;
import com.psss.registroElettronico.backend.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> findById(Long id) {
        return materiaRepository.findById(id);
    }

    public List<Materia> findByCodice(String codice) {
        return materiaRepository.findByCodice(codice);
    }
    public List<Materia> findByMateria(String materia) {
        return materiaRepository.findByMateria(materia);
    }

    public Materia saveAndFlush(Materia m) {
        return materiaRepository.saveAndFlush(m);
    }

    public void deleteById(Long id) {
        materiaRepository.deleteById(id);
    }
}



