package com.psss.registro.backend.services;

import com.psss.registro.backend.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServiceFacade {

    @Autowired
    MateriaService materiaService;

    @Autowired
    ClasseService classeService;

    @Autowired
    InsegnamentoService insegnamentoService;

    @Autowired
    DocenteService docenteService;

    @Autowired
    StudenteService studenteService;

    public boolean saveMateria(Materia materia) {
        return materiaService.saveMateria(materia);
    }

    public boolean updateMateria(Materia materia) {
        return materiaService.updateMateria(materia);
    }

    public boolean deleteMateriaById(Long id) {
        return materiaService.deleteById(id);
    }

    public List<Materia> findAllMaterie() {
        return materiaService.findAll();
    }

    public boolean saveClasse(Classe classe) {
        return classeService.saveClasse(classe);
    }

    public boolean updateClasse(Classe classe) {
        return classeService.updateClasse(classe);
    }

    public boolean deleteClasseById(Long id) {
        return classeService.deleteById(id);
    }

    public List<Classe> findAllClassi() {
        return classeService.findAll();
    }

    public List<Classe> findClasseByAnnoScolastico(int annoScolastico) {
        return classeService.findByAnnoScolastico(annoScolastico);
    }

    public boolean saveInsegnamento(Insegnamento insegnamento) {
        return insegnamentoService.saveInsegnamento(insegnamento);
    }

    public boolean saveDocente(Docente docente) {
        return docenteService.saveDocente(docente);
    }

    public boolean updateDocente(Docente docente) {
        return docenteService.updateDocente(docente);
    }

    public boolean deleteDocenteById(Long id) {
        return docenteService.deleteById(id);
    }

    public List<Docente> findAllDocenti() {
        return docenteService.findAll();
    }

    public boolean saveStudente(Studente studente) {
        return studenteService.saveStudente(studente);
    }

    public boolean updateStudente(Studente studente) {
        return studenteService.updateStudente(studente);
    }

    public boolean deleteStudenteById(Long id) {
        return studenteService.deleteById(id);
    }

    public List<Studente> findAllStudenti() {
        return studenteService.findAll();
    }
}
