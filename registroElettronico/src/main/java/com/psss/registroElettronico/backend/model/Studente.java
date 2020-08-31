package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
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
    private String nome;
    private String cognome;
    private GregorianCalendar dataNascita;
    @Column(unique = true)
    private String codiceFiscale;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL)
    private List<Assenza> assenze = new ArrayList<>();
    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL)
    private List<Ritardo> ritardi = new ArrayList<>();
    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL)
    private List<Nota> note = new ArrayList<>();
    @OneToMany(mappedBy = "studente", cascade = CascadeType.ALL)
    private List<Voto> voti = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Classe classe;
    //TODO Gestire l'orario dello studente

    public void addClasse(Classe classe){
        classe.addStudente(this);
        this.classe = classe;
    }

}
