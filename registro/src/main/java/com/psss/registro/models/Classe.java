package com.psss.registro.models;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "classi") @ToString(exclude = {"materie", "docenti", "studenti"})
@EqualsAndHashCode(exclude = {"id", "materie", "docenti", "studenti"})
public class Classe {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private int anno;
    private Character sezione;
    private int annoScolastico;
    @ManyToMany
    private Set<Materia> materie;
    @ManyToMany(mappedBy = "classi") @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Docente> docenti;
    @OneToMany(mappedBy = "classe") @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Studente> studenti;
    // TODO: levata istanziazione fuori dal costruttore

    public Classe(int anno, Character sezione, int annoScolastico) {
        this.anno = anno;
        this.sezione = sezione;
        this.annoScolastico = annoScolastico;
        this.materie = new HashSet<>();
        this.docenti = new HashSet<>();
        this.studenti = new HashSet<>();
    }

    public String getClasse() {
        return (this.anno + this.sezione.toString());
    }

    public void addMateria(Materia materia) {
        materie.add(materia);
    }

    public void removeMateria(Materia materia) {
        materie.remove(materia);
    }

    public void addDocente(Docente docente) {
        docenti.add(docente);
    }

    public void removeDocente(Docente docente) {
        docenti.remove(docente);
    }

    public void addStudente(Studente studente) {
        studenti.add(studente);
    }

    public void removeStudente(Studente studente) {
        studenti.remove(studente);
    }
}
