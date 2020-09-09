package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ClasseService implements CrudService<Classe>{

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private InsegnamentoRepository insegnamentoRepository;

    @Override
    public JpaRepository<Classe, Long> getRepository() {
        return classeRepository;
    }


    public List<Classe> findByAnnoScolastico(int annoScolastico) {return classeRepository.findByAnnoScolastico(annoScolastico); }

    public boolean update(Classe classe) {

        //Controlli

        save(classe);
        return true;
//
//
//        classeOld.setAnno(classeNew.getAnno());
//        classeOld.setSezione(classeNew.getSezione());
//        classeOld.setAnnoScolastico(classeNew.getAnnoScolastico());
//
//        Set<Insegnamento> temp = new HashSet<>(classeOld.getInsegnamenti());
//
//        for(Insegnamento insegnamento : temp) {
//            if(!classeNew.getInsegnamenti().contains(insegnamento)) {
//                classeOld.removeInsegnamento(insegnamento);
//                insegnamentoRepository.delete(insegnamento);
//            }
//        }
//
//        return getRepository().saveAndFlush(classeOld);
    }



}
