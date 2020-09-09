package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ClasseService implements CrudService<Classe>{

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private InsegnamentoRepository insegnamentoRepository;

//    public List<Classe> findAll() {
//        return classeRepository.findAll();
//    }
//
//    public Optional<Classe> findById(Long id) {
//        return classeRepository.findById(id);
//    }

    public List<Classe> findByAnnoScolastico(int annoScolastico) {return classeRepository.findByAnnoScolastico(annoScolastico); }

//    public void deleteById(Long id) {
//        classeRepository.deleteById(id);
//    }

//    public Classe createClasse(int anno, Character sezione, int annoScolastico) {
//        Classe classe = new Classe(anno, sezione, annoScolastico);
//        return classeRepository.saveAndFlush(classe);
//    }

    //TODO: implementare il "costruttore di copia"?
    @Override
    public Classe update(Classe classeOld, Classe classeNew) {
        classeOld.setAnno(classeNew.getAnno());
        classeOld.setSezione(classeNew.getSezione());
        classeOld.setAnnoScolastico(classeNew.getAnnoScolastico());

        Set<Insegnamento> temp = new HashSet<>(classeOld.getInsegnamenti());

        for(Insegnamento insegnamento : temp) {
            if(!classeNew.getInsegnamenti().contains(insegnamento)) {
                classeOld.removeInsegnamento(insegnamento);
                insegnamentoRepository.delete(insegnamento);
            }
        }

        return getRepository().saveAndFlush(classeOld);
    }

    @Override
    public JpaRepository<Classe, Long> getRepository() {
        return classeRepository;
    }


}
