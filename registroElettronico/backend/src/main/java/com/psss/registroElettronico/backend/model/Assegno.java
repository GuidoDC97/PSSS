package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.GregorianCalendar;

@Entity(name = "assegno")
public class Assegno {
    private GregorianCalendar data;
    private String testo;

    private Docente docente;
    private Classe classe;
    private Materia materia;


}
