package com.psss.registro.models;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

//TODO: controllare se tutte le annotation di Lombock sono necessarie alla fine del progetto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "classi")
@ToString(exclude = {"materie", "docenti", "studenti"})
@EqualsAndHashCode(exclude = {"id", "materie", "docenti"})
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
    //@OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    //TODO: decidere se elminare o meno gli studenti all'atto dell'eliminazione della classe
//    private List<Studente> studenti = new ArrayList<>();

    public Classe(int anno, Character sezione, int annoScolastico, List<Materia> materie){
        this.anno = anno;
        this.sezione = sezione;
        this.annoScolastico = annoScolastico;
        this.materie = materie;
    }

    public void addMateria(Materia materia){
        materia.addClasse(this);
        getMaterie().add(materia);
    }

    //TODO: controllare se questo setter funziona correttamente
    public void setMaterie(List<Materia> materie){
        for(Materia materia : materie){
            addMateria(materia);
        }
    }

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

//    public void addStudente(Studente studente){
//        getStudenti().add(studente);
//    }
}
