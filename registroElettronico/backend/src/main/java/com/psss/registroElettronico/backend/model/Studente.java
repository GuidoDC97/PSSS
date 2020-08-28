package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "studenti")
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
//    private GregorianCalendar data;

    @OneToMany
    private List<Assenza> assenze;
    @OneToMany
    private List<Nota> note;
    @OneToMany
    private List<Voto> voti;
    @ManyToOne
    private Classe classe;
    //TODO Gestire l'orario dello studente

}
