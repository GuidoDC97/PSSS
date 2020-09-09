package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.MateriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> findById(Long id) {
        return materiaRepository.findById(id);
    }

    public void deleteById(Long id) {
        materiaRepository.deleteById(id);
    }

    public Materia createMateria(String codice, String nome) {
        Materia materia = new Materia(codice, nome);
        return materiaRepository.saveAndFlush(materia);
    }

    public Materia updateMateria(Materia materia, String codice, String nome) {
        materia.setCodice(codice);
        materia.setNome(nome);
        return materiaRepository.saveAndFlush(materia);
    }

}
