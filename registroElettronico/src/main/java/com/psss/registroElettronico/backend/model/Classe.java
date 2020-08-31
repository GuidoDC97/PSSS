package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "classi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "materie", "docenti", "studenti"})
@ToString(exclude = {"materie", "docenti", "studenti"})
public class Classe {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int anno;
    private Character sezione;
    private int annoScolastico;

    @ManyToMany
    private List<Materia> materie = new ArrayList<>();
    @ManyToMany(mappedBy = "classi")
    private List<Docente> docenti = new ArrayList<>();
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    //TODO: decidere se elminare o meno gli studenti all'atto dell'eliminazione della classe
    private List<Studente> studenti = new ArrayList<>();


    public void addMateria(Materia materia){
        materia.addClasse(this);
        getMaterie().add(materia);
    }

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void addStudente(Studente studente){
        getStudenti().add(studente);
    }
}
