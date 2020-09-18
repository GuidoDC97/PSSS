package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.MateriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
class MateriaService implements CrudService<Materia> {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public MateriaRepository getRepository() {
        return materiaRepository;
    }

    private Optional<Materia> findByCodice(String codice){
        return getRepository().findByCodice(codice);
    }

    public boolean saveMateria(Materia materia) {

        Optional<Materia> materiaExistent = findByCodice(materia.getCodice());

        if (materiaExistent.isPresent() && !materiaExistent.get().getId().equals(materia.getId())) {
            return false;
        }
        return save(materia);
    }

    public boolean updateMateria(Materia materia) {

        Optional<Materia> materiaExistent = findByCodice(materia.getCodice());

        if (materiaExistent.isPresent() && !materiaExistent.get().getId().equals(materia.getId())){
            return false;
        }

        getRepository().saveAndFlush(materia);

        return true;
    }

}
