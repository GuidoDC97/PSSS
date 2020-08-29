package com.psss.registroElettronico.backend.controller;

import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.service.DocenteService;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/docenti")
public class DocenteController {

    @Autowired
    DocenteService docenteService;

    @GetMapping
    public List<Docente> getDocenti() {
        return docenteService.findAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Optional<Docente> getDocente(@PathVariable Long id){
        return docenteService.findById(id);
    }

    @GetMapping("/cerca")
    public List<Docente> getDocenteByNomeCognome(@RequestParam(value = "nome") String nome,
                                                 @RequestParam("cognome") String cognome){
        return docenteService.findByNomeAndCognome(nome, cognome);

    }

    @PostMapping
    public Docente create(@RequestBody final Docente docente){
        return docenteService.saveAndFlush(docente);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteDocente(@PathVariable Long id){
        docenteService.deleteById(id);
    }
}
