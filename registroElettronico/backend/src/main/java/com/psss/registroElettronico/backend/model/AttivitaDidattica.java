package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.GregorianCalendar;

@Entity(name = "attivitadidattiche")
public class AttivitaDidattica {
    private GregorianCalendar data;
    private String testo;

    private Docente docente;
    private Classe classe;
}
