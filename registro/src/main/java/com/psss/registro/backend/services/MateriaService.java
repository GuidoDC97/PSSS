package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.MateriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MateriaService implements CrudService<Materia> {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public JpaRepository<Materia, Long> getRepository() {
        return materiaRepository;
    }

    public boolean update(Materia materia) {
        save(materia);
        return true;
    }

}
