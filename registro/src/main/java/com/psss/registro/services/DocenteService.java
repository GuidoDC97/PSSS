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
    @Autowired
    private ClasseRepository classeRepository;

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    @Transactional
    public Docente createDocente(Docente docente, Set<Materia> materie, Set<Classe> classi) {
        UserAuthority authority = userAuthorityRepository.findByAuthority("DOCENTE").get();

        docente.setUserAuthority(authority);
        authority.addUser(docente);

        userAuthorityRepository.saveAndFlush(authority);

        docente.setMaterie(materie);
        docente.setClassi(classi);

        // TODO: va bene collegare nel service o va gestito nel model?
        for (Materia materia : materie) {
            materia.addDocente(docente);
            materiaRepository.saveAndFlush(materia);
        }

        for (Classe classe : classi) {
            classe.addDocente(docente);
            classeRepository.saveAndFlush(classe);
        }

        return docenteRepository.saveAndFlush(docente);
    }

    @Transactional
    public Docente updateDocente(Docente docente, Docente docenteTemp, Set<Materia> materie, Set<Classe> classi) {
        docente.setNome(docenteTemp.getNome());
        docente.setCognome(docenteTemp.getCognome());
        docente.setCodiceFiscale(docenteTemp.getCodiceFiscale());
        docente.setSesso(docenteTemp.getSesso());
        docente.setData(docenteTemp.getData());
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

        Iterator<Classe> classeIterator = classi.iterator();
        while(classeIterator.hasNext()) {
            Classe classe = classeIterator.next();
            if(!docente.getClassi().contains(classe)) {
                docente.addClasse(classe);
                classe.addDocente(docente);
                classeRepository.saveAndFlush(classe);
            }
        }

        classeIterator = docente.getClassi().iterator();
        while(classeIterator.hasNext()) {
            Classe classe = classeIterator.next();
            if(!classi.contains(classe)) {
                docente.removeClasse(classe);
                classe.removeDocente(docente);
                classeRepository.saveAndFlush(classe);
            }
        }

        return docenteRepository.saveAndFlush(docente);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }
}
