package com.psss.registroElettronico.backend.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToOne
    @JoinColumn(name = "id_ruolo")
    private Ruolo ruolo;
}