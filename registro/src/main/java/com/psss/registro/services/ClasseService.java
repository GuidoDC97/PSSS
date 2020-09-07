package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Insegnamento;
import com.psss.registro.repositories.ClasseRepository;
import com.psss.registro.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private InsegnamentoRepository insegnamentoRepository;

    public List<Classe> findAll() {
        return classeRepository.findAll();
    }

    public Optional<Classe> findById(Long id) {
        return classeRepository.findById(id);
    }

    public List<Classe> findByAnnoScolastico(int annoScolastico) {return classeRepository.findByAnnoScolastico(annoScolastico); }

    public void deleteById(Long id) {
        classeRepository.deleteById(id);
    }

    public Classe createClasse(int anno, Character sezione, int annoScolastico) {
        Classe classe = new Classe(anno, sezione, annoScolastico);
        return classeRepository.saveAndFlush(classe);
    }

    public Classe updateClasse(Classe classe, int anno, Character sezione, int annoScolastico,
                               Set<Insegnamento> insegnamenti) {
        classe.setAnno(anno);
        classe.setSezione(sezione);
        classe.setAnnoScolastico(annoScolastico);

        Set<Insegnamento> temp = new HashSet<>(classe.getInsegnamenti());

        for(Insegnamento insegnamento : temp) {
            if(!insegnamenti.contains(insegnamento)) {
                classe.removeInsegnamento(insegnamento);
                insegnamentoRepository.delete(insegnamento);
            }
        }

        return classeRepository.saveAndFlush(classe);
    }
}
