package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.repositories.ClasseRepository;
import com.psss.registro.repositories.DocenteRepository;
import com.psss.registro.repositories.MateriaRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class DocenteService {

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ClasseRepository classeRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }

    public Docente createDocente(String username, String nome, String cognome, String codiceFiscale, Character sesso,
                                 LocalDate data, String telefono, Set<Materia> materie, Set<Classe> classi) {

        Docente docente = new Docente(username, nome, cognome, codiceFiscale, sesso, data, telefono, materie, classi);

        UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();
        authority.addUser(docente);
        userAuthorityRepository.saveAndFlush(authority);

        docente.setUserAuthority(authority);

        // TODO: il docente va aggiunto alla materia qua o nel model?
        for (Materia materia : materie) {
            materia.addDocente(docente);
            materiaRepository.saveAndFlush(materia);
        }

        for (Classe classe : classi) {
            classe.addDocente(docente);
            // TODO per guido: è necessario fare il save and flush della classe? (fatto)
            classeRepository.saveAndFlush(classe);
        }

        for (Classe classe : classi) {
            classe.addDocente(docente);
            classeRepository.saveAndFlush(classe);
        }

        return docenteRepository.saveAndFlush(docente);
    }

    public Docente updateDocente(Docente docente, String username, String nome, String cognome, String codiceFiscale, Character sesso,
                                 LocalDate data, String telefono, Set<Materia> materie, Set<Classe> classi) {

        docente.setUsername(username);
        docente.setNome(nome);
        docente.setCognome(cognome);
        docente.setCodiceFiscale(codiceFiscale);
        docente.setSesso(sesso);
        docente.setData(data);
        docente.setTelefono(telefono);

        // Aggiunge le nuove materie al docente ed aggiunge il docente alla nuove materie
        for (Materia materia : materie) {
            if(!docente.getMaterie().contains(materia)) {
                docente.addMateria(materia);
                materia.addDocente(docente);
                materiaRepository.saveAndFlush(materia);
            }
        }

        // TODO: risolvere un bug dovuto ad un null pointer
        // Rimuove le vecchie materia al docente e rimuove il docente dalle vecchie materie
        Set<Materia> materiaSet = new HashSet<>(docente.getMaterie());
        for (Materia materia : materiaSet) {
            if(!materie.contains(materia)) {
                docente.removeMateria(materia);
                materia.removeDocente(docente);
                materiaRepository.saveAndFlush(materia);
            }
        }

        // Aggiunge le nuove classi al docente ed aggiunge il docente alla nuove classi
        for (Classe classe : classi) {
            if(!docente.getClassi().contains(classe)) {
                docente.addClasse(classe);
                classe.addDocente(docente);
                classeRepository.saveAndFlush(classe);
            }
        }

        // Rimuove le vecchie classi al docente e rimuove il docente dalle vecchie classi
        Set<Classe> classeSet = new HashSet<>(docente.getClassi());
        for (Classe classe : classeSet) {
            if(!classi.contains(classe)) {
                docente.removeClasse(classe);
                classe.removeDocente(docente);
                classeRepository.saveAndFlush(classe);
            }
        }

        return docenteRepository.saveAndFlush(docente);
    }

}
