package com.psss.registro.services;

import com.psss.registro.models.Studente;
import com.psss.registro.repositories.StudenteRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }

    public List<Studente> findAll(String filtro) {
        if(filtro == null || filtro.isEmpty()) {
            return studenteRepository.findAll();
        } else {
            return studenteRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(filtro, filtro);
        }
    }

    public Optional<Studente> findById(Long id) {
        return studenteRepository.findById(id);
    }

    public Studente createStudente(Studente s) {
        UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();

        s.setUserAuthority(authority);
        authority.addUser(s);

        userAuthorityRepository.saveAndFlush(authority);
        return studenteRepository.saveAndFlush(s);
    }

    public Studente updateStudente(Studente Studente, Studente studenteUpdated) {
        Studente.setNome(studenteUpdated.getNome());
        Studente.setCognome(studenteUpdated.getCognome());
        return studenteRepository.saveAndFlush(Studente);
    }

    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }

    public Long deleteByNomeAndCognome(String nome, String cognome) {
        return studenteRepository.deleteByNomeAndCognome(nome, cognome);
    }

    public Studente getOne(Long id){
        return studenteRepository.getOne(id);
    }
}
