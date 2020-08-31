package com.psss.registroElettronico.backend.controller;

import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.model.Materia;
import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.service.MateriaService;
import com.psss.registroElettronico.backend.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/materie")
public class MateriaController {

    @Autowired
    MateriaService materiaService;

    @GetMapping
    public List<Materia> getMateria() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Materia> getMateria(@PathVariable Long id) {


        System.out.println("Ricerco la materia");
        Materia materia = materiaService.findByCodice("MAT").get(0);
        System.out.println("Richiamo il metodo getDocenti()");
        List<Docente> docenti = materia.getDocenti();
        System.out.println("Richiedo la stampa dei docenti");
        System.out.println(docenti);

        return materiaService.findById(id);
    }

    @GetMapping("/findByCodice")
    public List<Materia> getMateriaByCodice(@RequestParam(value = "codice") String codice){
        return materiaService.findByCodice(codice);
    }

    @GetMapping("/findByMateria")
    public List<Materia> getMateriaByMateria(@RequestParam(value = "materia") String codice){
        return materiaService.findByMateria(codice);
    }

    @PostMapping
    public Materia create(@RequestBody final Materia materia){
        return materiaService.saveAndFlush(materia);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        materiaService.deleteById(id);
    }
}
