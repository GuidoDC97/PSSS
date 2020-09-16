package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.repositories.DocenteRepository;
import com.psss.registro.app.security.UserAuthority;
import com.psss.registro.app.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class DocenteService implements CrudService<Docente> {

    // TODO: eventualmente si può organizzare meglio il package security e far chiamare un service
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public DocenteRepository getRepository() {
        return docenteRepository;
    }

    private Optional<Docente> findByUsername(String username) {
        return getRepository().findByUsername(username);
    }

    private Optional<Docente> findByCodiceFiscale(String codiceFiscale){
        return getRepository().findByCodiceFiscale(codiceFiscale);
    }

    public boolean saveDocente(Docente docente) {
        if(docente.getId() == null){

            Optional<Docente> docenteExistentCF = findByCodiceFiscale(docente.getCodiceFiscale());
            Optional<Docente> docenteExistentUser = findByUsername(docente.getUsername());

            if (docenteExistentCF.isPresent() && !docenteExistentCF.get().getId().equals(docente.getId())) {
                return false;
            }
            if(docenteExistentUser.isPresent() && !docenteExistentUser.get().getId().equals(docente.getId())){
                return false;
            }

            UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();
            authority.addUser(docente);
            userAuthorityRepository.saveAndFlush(authority);

            docente.setUserAuthority(authority);

            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = docente.getNome().replaceAll("[^a-zA-Z]", "").toLowerCase()
                    + "." + docente.getCognome().replaceAll("[^a-zA-Z]", "").toLowerCase();

            docente.setPassword(passwordEncoder.encode(password));

            return save(docente);

        }else{
            return false;
        }
    }

    public boolean updateDocente(Docente docente) {

        Optional<Docente> studenteExistent = findByCodiceFiscale(docente.getCodiceFiscale());
        Optional<Docente> docenteExistentUser = findByUsername(docente.getUsername());

        if (studenteExistent.isPresent() && !studenteExistent.get().getId().equals(docente.getId())){
            return false;
        }
        if(docenteExistentUser.isPresent() && !docenteExistentUser.get().getId().equals(docente.getId())){
            return false;
        }

        getRepository().saveAndFlush(docente);
        return true;
    }

}
