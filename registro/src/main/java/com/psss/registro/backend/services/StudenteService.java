package com.psss.registro.backend.services;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.repositories.ClasseRepository;
import com.psss.registro.backend.repositories.StudenteRepository;
import com.psss.registro.app.security.UserAuthority;
import com.psss.registro.app.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public List<Studente> findByClasse(Classe classe){
        return studenteRepository.findByClasse(classe);
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

        classeSync.addStudente(studente);
        // TODO per guido: è necessario fare il save and flush della classe? (non fatto)

        return studenteRepository.saveAndFlush(studente);
    }

    public Studente updateStudente(Studente studente, String username, String nome, String cognome, String codiceFiscale,
                                   Character sesso, LocalDate data, String telefono, Classe classe) {

        //Studente studenteSync = studenteRepository.findById(studente.getId()).get();
        Classe classeOld = classeRepository.findByStudenti(studente);
        classeOld.removeStudente(studente); //Forse da problemi

        studente.setNome(nome);
        studente.setCognome(cognome);
        studente.setCodiceFiscale(codiceFiscale);
        studente.setSesso(sesso);
        studente.setData(data);
        studente.setUsername(username);
        studente.setTelefono(telefono);

        studente.setClasse(classe);

        classe.addStudente(studente);

        return studenteRepository.saveAndFlush(studente);
    }
}
