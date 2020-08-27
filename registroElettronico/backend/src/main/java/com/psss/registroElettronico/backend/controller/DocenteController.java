package com.psss.registroElettronico.backend.controller;


import com.psss.registroElettronico.backend.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("docente")
public class DocenteController {

    @Autowired
    StudenteService docenteService;

    @GetMapping
    public void getDocenti() {

    }


}
