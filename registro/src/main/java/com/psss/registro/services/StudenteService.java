package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.models.Studente;
import com.psss.registro.repositories.ClasseRepository;
import com.psss.registro.repositories.StudenteRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class StudenteService {

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private ClasseRepository classeRepository;

    public List<Studente> findAll() {
        return studenteRepository.findAll();
    }

    public Optional<Studente> findById(Long id) {
        return studenteRepository.findById(id);
    }

    //TODO: non credo si debba fare altro nella delete
    public void deleteById(Long id) {
        studenteRepository.deleteById(id);
    }

    public Studente createStudente(String username, String nome, String cognome, String codiceFiscale, Character sesso,
                                   LocalDate data, String telefono, Classe classe) {

        Classe classeSync = classeRepository.findById(classe.getId()).get();
        Studente studente = new Studente(username, nome, cognome, codiceFiscale, sesso, data, telefono, classeSync);

        UserAuthority authority = userAuthorityRepository.findByAuthority("STUDENTE").get();
        authority.addUser(studente);
        userAuthorityRepository.saveAndFlush(authority);

        studente.setUserAuthority(authority);

        //studente.setClasse(classeSync);
        // TODO per guido: è necessario fare il save and flush della classe? (non fatto)

        return studenteRepository.saveAndFlush(studente);
    }

    public Studente updateStudente(Studente studente, String username, String nome, String cognome, String codiceFiscale,
                                   Character sesso, LocalDate data, String telefono, Classe classe) {

        Classe classeSync = classeRepository.findById(classe.getId()).get();

        studente.setNome(nome);
        studente.setCognome(cognome);
        studente.setCodiceFiscale(codiceFiscale);
        studente.setSesso(sesso);
        studente.setData(data);
        studente.setUsername(username);
        studente.setTelefono(telefono);
        studente.setClasse(classeSync);

        //studente.getClasse().addStudente(studente);
        // TODO: problema: devo rimuovere lo studente dalla vecchia classe oppure no?????

        return studenteRepository.saveAndFlush(studente);
    }
}
