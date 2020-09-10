package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ClasseService implements CrudService<Classe>{

    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private InsegnamentoRepository insegnamentoRepository;

    @Override
    public ClasseRepository getRepository() {
        return classeRepository;
    }

    public boolean saveClasse(Classe classe){
        Optional<Classe> classeExistent = findByAnnoAndAnnoScolasticoAndSezione(classe.getAnno(),classe.getAnnoScolastico(),classe.getSezione());

        if (classeExistent.isPresent() && !classeExistent.get().getId().equals(classe.getId())) {
            return false;
        }
        return save(classe);
    }

    public List<Classe> findByAnnoScolastico(int annoScolastico) {return classeRepository.findByAnnoScolastico(annoScolastico); }

    public Optional<Classe> findByAnnoAndAnnoScolasticoAndSezione(int anno, int annoScolastico, Character sezione){return getRepository().findByAnnoAndAnnoScolasticoAndSezione(anno, annoScolastico,sezione);}

    public boolean updateClasse(Classe classe) {

        //Controlli

        Optional<Classe> classeExistent = findByAnnoAndAnnoScolasticoAndSezione(classe.getAnno(),classe.getAnnoScolastico(),classe.getSezione());

        if (classeExistent.isPresent() && !classeExistent.get().getId().equals(classe.getId())){
            return false;

        }

        getRepository().saveAndFlush(classe);
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
