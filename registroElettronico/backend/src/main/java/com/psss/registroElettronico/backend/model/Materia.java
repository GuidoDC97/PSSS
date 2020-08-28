package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "materie")
public class Materia {

    private String materia;

    private List<Classe> classi;
    private List<Docente> docenti;
    private List<Assegno> assegni;




}
