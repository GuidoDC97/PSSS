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
    public JpaRepository<Studente, Long> getRepository() {
        return studenteRepository;
    }

//    public List<Studente> findAll() {
//        return studenteRepository.findAll();
//    }
//
//    public Optional<Studente> findById(Long id) {
//        return studenteRepository.findById(id);
//    }

    public List<Studente> findByClasse(Classe classe){
        return studenteRepository.findByClasse(classe);
    }

    //TODO: non credo si debba fare altro nella delete
//    public void deleteById(Long id) {
//        studenteRepository.deleteById(id);
//    }


    public Studente create(Studente studente) {

        Classe classeSync = classeRepository.findById(studente.getClasse().getId()).get();

        UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();
        authority.addUser(studente);
        userAuthorityRepository.saveAndFlush(authority);

        studente.setUserAuthority(authority);

        classeSync.addStudente(studente);
        // TODO per guido: è necessario fare il save and flush della classe? (non fatto)

        return getRepository().saveAndFlush(studente);
    }

    public Studente update(Studente studenteOld, Studente studenteNew) {

        //Studente studenteSync = studenteRepository.findById(studente.getId()).get();
        Classe classeOld = classeRepository.findByStudenti(studenteOld);
        classeOld.removeStudente(studenteOld); //Forse da problemi

        studenteOld.setNome(studenteNew.getNome());
        studenteOld.setCognome(studenteNew.getCognome());
        studenteOld.setCodiceFiscale(studenteNew.getCodiceFiscale());
        studenteOld.setSesso(studenteNew.getSesso());
        studenteOld.setData(studenteNew.getData());
        studenteOld.setUsername(studenteNew.getUsername());
        studenteOld.setTelefono(studenteNew.getTelefono());

        studenteOld.setClasse(studenteNew.getClasse());

        studenteNew.getClasse().addStudente(studenteOld);

        return getRepository().saveAndFlush(studenteOld);
    }
}
