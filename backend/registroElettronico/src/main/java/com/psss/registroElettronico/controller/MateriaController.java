package com.psss.registroElettronico.controller;

import com.psss.registroElettronico.model.Materia;
import com.psss.registroElettronico.model.Studente;
import com.psss.registroElettronico.service.MateriaService;
import com.psss.registroElettronico.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/materia")
public class MateriaController {

    @Autowired
    MateriaService materiaService;

    @GetMapping
    public List<Materia> getMateria() {
        return materiaService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Materia> getMateria(@PathVariable Long id) {
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
