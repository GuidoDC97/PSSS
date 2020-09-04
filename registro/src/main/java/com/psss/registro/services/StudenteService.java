package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Studente;
import com.psss.registro.repositories.ClasseRepository;
import com.psss.registro.repositories.MateriaRepository;
import com.psss.registro.repositories.StudenteRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {

    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private ClasseRepository classeRepository;

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }

    public Optional<Studente> findById(Long id) {
        return studenteRepository.findById(id);
    }

    public Studente createStudente(Studente s) {
        UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();

        s.setUserAuthority(authority);
        authority.addUser(s);


        userAuthorityRepository.saveAndFlush(authority);

        Classe classe = s.getClasse();
        classe.addStudente(s);

        return studenteRepository.saveAndFlush(s);
    }

    public Studente updateStudente(Studente studente, Studente studenteUpdated) {
        studente.setNome(studenteUpdated.getNome());
        studente.setCognome(studenteUpdated.getCognome());
        studente.setCodiceFiscale(studenteUpdated.getCodiceFiscale());
        studente.setSesso(studenteUpdated.getSesso());
        studente.setData(studenteUpdated.getData());
        studente.setUsername(studenteUpdated.getUsername());
        studente.setTelefono(studenteUpdated.getTelefono());
        studente.setClasse(studenteUpdated.getClasse());

        Classe classe = studenteUpdated.getClasse();
        classe.addStudente(studenteUpdated);

        return studenteRepository.saveAndFlush(studente);
    }

    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }
}
