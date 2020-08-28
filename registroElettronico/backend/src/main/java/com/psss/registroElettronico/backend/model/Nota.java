package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.GregorianCalendar;

@Entity(name = "note")
public class Nota {

    private GregorianCalendar data;
    private String testo;

    private Studente studente;
    private Docente docente;



}
