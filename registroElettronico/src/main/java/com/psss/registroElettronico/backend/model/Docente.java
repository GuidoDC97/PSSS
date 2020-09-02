package com.psss.registroElettronico.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "docenti")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
                       "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
public class Docente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private GregorianCalendar dataNascita;
    @Column(unique = true)
    private String codiceFiscale;
    //TODO: implementare il metodo setCodiceFiscale con gli opportuni controlli. Decidere dove implementarlo.

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    //@JsonBackReference
    private List<Nota> note = new ArrayList<>();
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<Voto> voti = new ArrayList<>();
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<Assegno> assegni = new ArrayList<>();
    @ManyToMany
    private List<Materia> materie = new ArrayList<>();
    @ManyToMany
    private List<Classe> classi = new ArrayList<>();
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<AttivitaDidattica> attivitadidattiche;
    //TODO gestire l'orario del docente

    public void addMateria(Materia materia){
        materia.addDocente(this);
        getMaterie().add(materia);
    }

    public void addClasse(Classe classe){
        classe.addDocente(this);
        getClassi().add(classe);
    }
}
