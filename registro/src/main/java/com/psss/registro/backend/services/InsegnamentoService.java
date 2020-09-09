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

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InsegnamentoService implements CrudService<Insegnamento>{

    @Autowired
    InsegnamentoRepository insegnamentoRepository;

//    public List<Insegnamento> findAll() {
//        return insegnamentoRepository.findAll();
//    }
//
//    public Optional<Insegnamento> findById(Long id) {
//        return insegnamentoRepository.findById(id);
//    }
//
//    public void deleteById(Long id) {
//        insegnamentoRepository.deleteById(id);
//    }

    @Override
    public JpaRepository<Insegnamento, Long> getRepository() {
        return insegnamentoRepository;
    }

    public Insegnamento create(Insegnamento insegnamento) {

        insegnamento.getDocente().addInsegnamento(insegnamento);
        insegnamento.getClasse().addInsegnamento(insegnamento);
        //TODO: verificare se è necessario salvare docente e classe

        return getRepository().saveAndFlush(insegnamento);
    }

    //TODO: Controllare se è opportuno implementarla in questo modo.
    @Override
    public Insegnamento update(Insegnamento oldEntity, Insegnamento newEntity) {
        return null;
    }
}
