package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.repositories.ClasseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClasseService implements CrudService<Classe>{

    @Autowired
    private ClasseRepository classeRepository;

    @Override
    public ClasseRepository getRepository() {
        return classeRepository;
    }

    public List<Classe> findByAnnoScolastico(int annoScolastico) {
        return classeRepository.findByAnnoScolastico(annoScolastico);
    }

    private Optional<Classe> findByAnnoAndAnnoScolasticoAndSezione(int anno, int annoScolastico, Character sezione){return getRepository().findByAnnoAndAnnoScolasticoAndSezione(anno, annoScolastico,sezione);}

    public boolean saveClasse(Classe classe){
        Optional<Classe> classeExistent = findByAnnoAndAnnoScolasticoAndSezione(classe.getAnno(),classe.getAnnoScolastico(),classe.getSezione());

        if (classeExistent.isPresent() && !classeExistent.get().getId().equals(classe.getId())) {
            return false;
        }
        return save(classe);
    }

    public boolean updateClasse(Classe classe) {

        Optional<Classe> classeExistent = findByAnnoAndAnnoScolasticoAndSezione(classe.getAnno(),classe.getAnnoScolastico(),classe.getSezione());

        if (classeExistent.isPresent() && !classeExistent.get().getId().equals(classe.getId())){
            return false;
        }

        getRepository().saveAndFlush(classe);
        return true;
    }

}
