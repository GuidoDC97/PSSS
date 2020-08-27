package com.psss.registroElettronico.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.GregorianCalendar;

@Entity
@Data
@AllArgsConstructor
public class Segretario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
    @NonNull
    private GregorianCalendar data;
    private Character sesso;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
