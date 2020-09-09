package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.StudenteRepository;
import com.psss.registro.app.security.UserAuthority;
import com.psss.registro.app.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class StudenteService implements CrudService<Studente>{

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private ClasseRepository classeRepository;

    @Override
    public StudenteRepository getRepository() {
        return studenteRepository;
    }

    public List<Studente> findByClasse(Classe classe){
        return studenteRepository.findByClasse(classe);
    }

    //TODO: non si dovrebbe gestire il save dello studente come il save del docente tramite le Autorità?
    public boolean saveStudente(Studente studente) {

        if(studente.getId() == null){
            Classe classeSync = classeRepository.findById(studente.getClasse().getId()).get();

            UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();
            authority.addUser(studente);
            userAuthorityRepository.saveAndFlush(authority);

            studente.setUserAuthority(authority);

            classeSync.addStudente(studente);
            // TODO per guido: è necessario fare il save and flush della classe? (non fatto)

            //getRepository().saveAndFlush(studente);
            return save(studente);

        }else{
            return false;
            //throw new EntityExistsException();
        }

    }

    public Optional<Studente> findByCodiceFiscale(String codiceFiscale){return getRepository().findByCodiceFiscale(codiceFiscale);}


    public boolean updateStudente(Studente studente) {

        //Controlli + Lazy initialization


        Optional<Studente> studenteExistent = findByCodiceFiscale(studente.getCodiceFiscale());

        if (studenteExistent.isPresent() && studenteExistent.get().getId() != studente.getId()){
            return false;
        }

        getRepository().saveAndFlush(studente);
        return true;



        //Studente studenteSync = studenteRepository.findById(studente.getId()).get();
//        Classe classeOld = classeRepository.findByStudenti(studenteOld);
//        classeOld.removeStudente(studenteOld); //Forse da problemi
//
//        studenteOld.setNome(studenteNew.getNome());
//        studenteOld.setCognome(studenteNew.getCognome());
//        studenteOld.setCodiceFiscale(studenteNew.getCodiceFiscale());
//        studenteOld.setSesso(studenteNew.getSesso());
//        studenteOld.setData(studenteNew.getData());
//        studenteOld.setUsername(studenteNew.getUsername());
//        studenteOld.setTelefono(studenteNew.getTelefono());
//
//        studenteOld.setClasse(studenteNew.getClasse());
//
//        studenteNew.getClasse().addStudente(studenteOld);

        //return save(studente);
    }

}
