package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "studenti")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "assenze", "ritardi", "voti", "classe"})
@ToString(exclude = {"note", "assenze", "ritardi", "voti", "classe"})
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
//    private GregorianCalendar data;

    @OneToMany(mappedBy = "studente")
    private List<Assenza> assenze;
    @OneToMany(mappedBy = "studente")
    private List<Ritardo> ritardi;
    @OneToMany(mappedBy = "studente")
    private List<Nota> note;
    @OneToMany(mappedBy = "studente")
    private List<Voto> voti;
    @ManyToOne(fetch = FetchType.LAZY)
    private Classe classe;
    //TODO Gestire l'orario dello studente

    public Studente (String nome, String cognome){
        this.nome = nome;
        this.cognome = cognome;
    }


}
