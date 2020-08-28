package com.psss.registroElettronico.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "docenti")
public class Docente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
//    private GregorianCalendar data;
//    private String codiceFiscale;


    @OneToMany
    private List<Nota> note;
    @OneToMany
    private List<Voto> voti;
    @OneToMany
    private List<Assegno> assegni;
    @OneToMany
    private List<Materia> materie;
    @ManyToMany
    private List<Classe> classi;
    @OneToMany
    private List<AttivitaDidattica> attivitadidattiche;
    //TODO gestire l'orario del docente

}
