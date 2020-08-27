package com.psss.registroElettronico.backend.controller;

import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studente")
public class StudenteController {

    @Autowired
    StudenteService studenteService;

    @GetMapping
    public List<Studente> findAll() {
        return studenteService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Studente> findById(@PathVariable Long id) {
        return studenteService.findById(id);
    }

//    @GetMapping("/cerca")
//    public List<Studente> findByNomeCognome(@RequestParam(value = "nome") String nome,
//                                       @RequestParam(value = "cognome") String cognome) {
//        return studenteService.findByNomeCognome(nome, cognome);
//    }

}
