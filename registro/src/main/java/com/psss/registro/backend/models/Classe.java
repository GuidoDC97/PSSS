package com.psss.registro.backend.models;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "classi") @ToString(exclude = {"materie", "docenti", "studenti", "insegnamenti"})
@EqualsAndHashCode(exclude = {"id", "materie", "docenti", "studenti", "insegnamenti"})
public class Classe extends AbstractEntity{
    @Min(1)
    @Max(5)
    private int anno;
    @NotNull(message = "Selezionare la sezione")
    private Character sezione;
    @NotNull(message = "Inserire l'anno scolastico")
    private int annoScolastico;

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY) @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Studente> studenti;
    @OneToMany(mappedBy = "classe", cascade = CascadeType.REMOVE) @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Insegnamento> insegnamenti;

    public Classe(int anno, Character sezione, int annoScolastico) {
        this.anno = anno;
        this.sezione = sezione;
        this.annoScolastico = annoScolastico;
        this.studenti = new HashSet<>();
        this.insegnamenti = new HashSet<>();
    }

    public void addStudente(Studente studente) {
        studenti.add(studente);
    }

    @PreRemove
    public void preRemove(){
        studenti.forEach(studente -> studente.setClasse(null));
    }

    public String toString() {
        return this.anno + this.sezione.toString() + " - " + this.annoScolastico;
    }
}
