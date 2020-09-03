package com.psss.registro.services;

import com.psss.registro.models.Docente;
import com.psss.registro.models.Materia;
import com.psss.registro.repositories.DocenteRepository;
import com.psss.registro.repositories.MateriaRepository;
import com.psss.registro.security.UserAuthority;
import com.psss.registro.security.UserAuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private UserAuthorityRepository userAuthorityRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public List<Docente> findAll(String filtro) {
        if(filtro == null || filtro.isEmpty()) {
            return docenteRepository.findAll();
        } else {
            return docenteRepository.findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(filtro, filtro);
        }
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente createDocente(Docente docente, Set<Materia> materie) {
        UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();

        docente.setUserAuthority(authority);
        authority.addUser(docente);

        userAuthorityRepository.saveAndFlush(authority);

        docente.setMaterie(materie);

        Iterator<Materia> materiaIterator = materie.iterator();
        while(materiaIterator.hasNext()) {
            Materia materia = materiaIterator.next();
            materia.addDocente(docente);
            materiaRepository.saveAndFlush(materia);
        }

        return docenteRepository.saveAndFlush(docente);
    }

    public Docente updateDocente(Docente docente, Docente docenteTemp, Set<Materia> materie) {
        docente.setNome(docenteTemp.getNome());
        docente.setCognome(docenteTemp.getCognome());
        docente.setCodiceFiscale(docenteTemp.getCodiceFiscale());
        docente.setSesso(docenteTemp.getSesso());
        docente.setData(docenteTemp.getData());
//        docente.setEmail(docenteTemp.getEmail());
        docente.setUsername(docenteTemp.getUsername());
        docente.setTelefono(docenteTemp.getTelefono());

        Iterator<Materia> materiaIterator = materie.iterator();
        while(materiaIterator.hasNext()) {
            Materia materia = materiaIterator.next();
            if(!docente.getMaterie().contains(materia)) {
                docente.addMateria(materia);
                materia.addDocente(docente);
                materiaRepository.saveAndFlush(materia);
            }
        }

        materiaIterator = docente.getMaterie().iterator();
        while(materiaIterator.hasNext()) {
            Materia materia = materiaIterator.next();
            if(!materie.contains(materia)) {
                docente.removeMateria(materia);
                materia.removeDocente(docente);
                materiaRepository.saveAndFlush(materia);
            }
        }

        return docenteRepository.saveAndFlush(docente);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }

    public Long deleteByNomeAndCognome(String nome, String cognome) {
        return docenteRepository.deleteByNomeAndCognome(nome, cognome);
    }

    public Docente getOne(Long id) {
        return docenteRepository.getOne(id);
    }
}
