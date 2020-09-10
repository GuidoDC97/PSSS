package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class InsegnamentoService implements CrudService<Insegnamento>{

    @Autowired
    InsegnamentoRepository insegnamentoRepository;

    @Override
    public InsegnamentoRepository getRepository() {
        return insegnamentoRepository;
    }

    public Optional<Insegnamento> findByDocenteAndMateriaAndClasse(Docente docente, Materia materia, Classe classe){
        return getRepository().findByDocenteAndMateriaAndClasse(docente, materia, classe);
    }


    public boolean saveInsegnamento(Insegnamento insegnamento){
        Optional<Insegnamento> insegnamentoExistent = findByDocenteAndMateriaAndClasse(insegnamento.getDocente(),insegnamento.getMateria(), insegnamento.getClasse());

        if (insegnamentoExistent.isPresent() && !insegnamentoExistent.get().getId().equals(insegnamento.getId())){
            return false;

        }

        getRepository().saveAndFlush(insegnamento);
        return true;
    }

}
