package com.psss.registroElettronico.backend.controller;

import com.psss.registroElettronico.backend.model.Classe;
import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.service.ClasseService;
import com.psss.registroElettronico.backend.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/classi")
public class ClasseController {

    @Autowired
    ClasseService classeService;

    @GetMapping
    public List<Classe> getClassi() {
        return classeService.findAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Optional<Classe> getClasse(@PathVariable Long id){
        return classeService.findById(id);
    }

    @GetMapping("/findByAnnoScolastico")
    public List<Classe> findByAnnoScolastico(@RequestParam(value = "anno") int anno){
        return classeService.findByAnnoScolastico(anno);
    }

    @GetMapping("/findByAnnoScolasticoAndSezione")
    public List<Classe> findByAnnoScolasticoAndSezione(@RequestParam(value = "anno") int anno,
                                                       @RequestParam(value = "sezione") Character sezione){
        return classeService.findByAnnoScolasticoAndSezione(anno, sezione);
    }

    @PostMapping
    public Classe create(@RequestBody final Classe classe){
        return classeService.saveAndFlush(classe);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteClasse(@PathVariable Long id){
        classeService.deleteById(id);
    }
}
