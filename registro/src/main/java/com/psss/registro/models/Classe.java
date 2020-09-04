package com.psss.registro.models;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

//TODO: controllare se tutte le annotation di Lombock sono necessarie alla fine del progetto
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "classi")
@ToString(exclude = {"materie", "docenti"})
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
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "classi")
    private List<Docente> docenti = new ArrayList<>();
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "classe")
    private List<Studente> studenti = new ArrayList<>();

    public Classe(int anno, Character sezione, int annoScolastico, List<Materia> materie){
        this.anno = anno;
        this.sezione = sezione;
        this.annoScolastico = annoScolastico;
        this.materie = materie;
        this.docenti = new ArrayList<>();
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

    public String getClasse() {
        return (this.anno + "" + this.sezione);
    }

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void removeDocente(Docente docente) {
        getDocenti().remove(docente);
    }

    public void addStudente(Studente studente){
        getStudenti().add(studente);
    }
}
