package com.psss.registro.services;

import com.psss.registro.models.Materia;
import com.psss.registro.repositories.MateriaRepository;
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

    public Materia createMateria(Materia d) {
        return materiaRepository.saveAndFlush(d);
    }

    public Materia updateMateria(Materia materia, Materia materiaUpdated) {
        materia.setCodice(materiaUpdated.getCodice());
        materia.setNome(materiaUpdated.getNome());
        return materiaRepository.saveAndFlush(materia);
    }

    public void deleteById(Long id) {
        materiaRepository.deleteById(id);
    }
}
