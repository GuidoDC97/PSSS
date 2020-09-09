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

//    public List<Materia> findAll() {
//        return materiaRepository.findAll();
//    }
//
//    public Optional<Materia> findById(Long id) {
//        return materiaRepository.findById(id);
//    }
//
//    public void deleteById(Long id) {
//        materiaRepository.deleteById(id);
//    }

    @Override
    public Materia update(Materia materiaOld, Materia materiaNew) {
        materiaOld.setCodice(materiaNew.getCodice());
        materiaOld.setNome(materiaNew.getNome());
        return getRepository().saveAndFlush(materiaOld);
    }

//    public Materia createMateria(String codice, String nome) {
//        Materia materia = new Materia(codice, nome);
//        return materiaRepository.saveAndFlush(materia);
//    }


}
