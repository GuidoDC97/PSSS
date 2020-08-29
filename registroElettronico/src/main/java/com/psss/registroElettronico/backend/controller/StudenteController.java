package com.psss.registroElettronico.backend.controller;

import com.psss.registroElettronico.backend.model.Nota;
import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studenti")
public class StudenteController {

    @Autowired
    StudenteService studenteService;

    @GetMapping
    public List<Studente> getStudenti() {
        List<Studente> lista = studenteService.findAll();
        System.out.println("Ho chiamato la findAll()");
        List<Nota> note = lista.get(0).getNote();
        return lista;
    }

    @GetMapping("/{id}")
    public Optional<Studente> getStudente(@PathVariable Long id) {
        return studenteService.findById(id);
    }

    @GetMapping("/cerca")
    public List<Studente> getStudenteByNomeAndCognome(@RequestParam(value = "nome") String nome,
                                                   @RequestParam(value = "cognome") String cognome) {
        return studenteService.findByNomeAndCognome(nome, cognome);
    }

    @PostMapping
    public Studente create(@RequestBody final Studente studente){
        return studenteService.saveAndFlush(studente);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteStudente(@PathVariable Long id){
        studenteService.deleteById(id);
    }
}