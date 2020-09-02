package com.psss.registro.services;

import com.psss.registro.models.Materia;
import com.psss.registro.models.Materia;
import com.psss.registro.repositories.MateriaRepository;
import com.psss.registro.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public List<Materia> findAll(String filtro) {
        if(filtro == null || filtro.isEmpty()) {
            return materiaRepository.findAll();
        } else {
            return materiaRepository.findByCodiceContainingIgnoreCaseOrNomeContainingIgnoreCase(filtro, filtro);
        }
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

    public Long deleteByNomeAndCognome(String nome, String nomemateria) {
        return materiaRepository.deleteByCodiceAndNome(nome, nomemateria);
    }

    public Materia getOne(Long id) {
        return materiaRepository.getOne(id);
    }
}
