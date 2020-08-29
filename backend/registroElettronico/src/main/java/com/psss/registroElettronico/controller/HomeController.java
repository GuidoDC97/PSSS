package com.psss.registroElettronico.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return("<h1>Benvenuto<h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return("<h1>Benvenuto Admin<h1>");
    }

    @GetMapping("/docente")
    public String docente() {
        return("<h1>Benvenuto Docente<h1>");
    }
}
