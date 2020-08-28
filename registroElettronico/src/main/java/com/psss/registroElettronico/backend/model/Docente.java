package com.psss.registroElettronico.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
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


    @OneToMany(mappedBy = "docente")
    private List<Nota> note;
    @OneToMany(mappedBy = "docente")
    private List<Voto> voti;
    @OneToMany(mappedBy = "docente")
    private List<Assegno> assegni;
    @ManyToMany
    private List<Materia> materie;
    @ManyToMany
    private List<Classe> classi;
    @OneToMany(mappedBy = "docente")
    private List<AttivitaDidattica> attivitadidattiche;
    //TODO gestire l'orario del docente

}
