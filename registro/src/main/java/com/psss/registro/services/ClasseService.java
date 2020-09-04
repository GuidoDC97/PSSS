package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.repositories.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;

    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    public Optional<Classe> findById(Long id) {
        return classeRepository.findById(id);
    }

    public void deleteById(Long id) {
        classeRepository.deleteById(id);
    }

    public Classe createClasse(int anno, Character sezione, int annoScolastico) {
        Classe classe = new Classe(anno, sezione, annoScolastico);
        return classeRepository.saveAndFlush(classe);
    }

    public Classe updateClasse(Classe classe, int anno, Character sezione, int annoScolastico) {
        classe.setAnno(anno);
        classe.setSezione(sezione);
        classe.setAnnoScolastico(annoScolastico);
        return classeRepository.saveAndFlush(classe);
    }
}
