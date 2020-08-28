package com.psss.registroElettronico.backend.controller;

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
        return studenteService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Studente> getStudente(@PathVariable Long id) {
        return studenteService.findById(id);
    }

    @GetMapping("/cerca")
    public List<Studente> getStudenteByNomeCognome(@RequestParam(value = "nome") String nome,
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
