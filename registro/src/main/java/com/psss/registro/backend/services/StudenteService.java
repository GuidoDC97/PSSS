package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.repositories.StudenteRepository;
import com.psss.registro.app.security.UserAuthority;
import com.psss.registro.app.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
class StudenteService implements CrudService<Studente>{

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private StudenteRepository studenteRepository;

    @Override
    public StudenteRepository getRepository() {
        return studenteRepository;
    }

    private Optional<Studente> findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    private Optional<Studente> findByCodiceFiscale(String codiceFiscale){
        return getRepository().findByCodiceFiscale(codiceFiscale);
    }

    public boolean saveStudente(Studente studente) {

        if(studente.getId() == null){

            Optional<Studente> studenteExistent = findByCodiceFiscale(studente.getCodiceFiscale());
            Optional<Studente> studenteExistentUser = findByUsername(studente.getUsername());

            if (studenteExistent.isPresent() && !studenteExistent.get().getId().equals(studente.getId())) {
                return false;
            }
            if(studenteExistentUser.isPresent() && !studenteExistentUser.get().getId().equals(studente.getId())){
                return false;
            }

            UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();
            authority.addUser(studente);
            userAuthorityRepository.saveAndFlush(authority);

            studente.setUserAuthority(authority);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = studente.getNome().replaceAll("[^a-zA-Z]", "").toLowerCase()
                    + "." + studente.getCognome().replaceAll("[^a-zA-Z]", "").toLowerCase();

            studente.setPassword(passwordEncoder.encode(password));

            return save(studente);

        }else{
            return false;
        }

    }

    public boolean updateStudente(Studente studente) {

        Optional<Studente> studenteExistent = findByCodiceFiscale(studente.getCodiceFiscale());
        Optional<Studente> studenteExistentUser = findByUsername(studente.getUsername());

        if (studenteExistent.isPresent() && !studenteExistent.get().getId().equals(studente.getId())){
            return false;
        }
        if(studenteExistentUser.isPresent() && !studenteExistentUser.get().getId().equals(studente.getId())){
            return false;
        }

        getRepository().saveAndFlush(studente);
        return true;
    }

}
