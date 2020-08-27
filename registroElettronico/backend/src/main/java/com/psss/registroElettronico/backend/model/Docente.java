package com.psss.registroElettronico.backend.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.GregorianCalendar;

@Entity(name="docenti")
public class Docente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private GregorianCalendar data;
    private String codiceFiscale;
    private String username;
    private String password;



}
