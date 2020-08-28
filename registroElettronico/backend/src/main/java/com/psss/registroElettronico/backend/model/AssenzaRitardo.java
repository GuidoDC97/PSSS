package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.GregorianCalendar;

@Entity(name = "assenzeritardi")
public class AssenzaRitardo {
    private GregorianCalendar data;
    private String testo;

    private Studente studente;
    private Docente docente;
}
