package com.psss.registro.backend.models;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.psss.registro.backend.models.Classe;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "classi") @ToString(exclude = {"materie", "docenti", "studenti"})
@EqualsAndHashCode(exclude = {"id", "materie", "docenti", "studenti"})
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

//    public String getClasse() {
//        return (this.anno + this.sezione.toString() + " - " + this.annoScolastico);
//    }

    public void addStudente(Studente studente) {
        studenti.add(studente);
    }

    @PreRemove
    public void preRemove(){
        studenti.forEach(studente -> studente.setClasse(null));
    }

    //    public void addDocente(Docente docente) {
//        docenti.add(docente);
//    }
//
//    public void removeDocente(Docente docente) {
//        docenti.remove(docente);
//    }

    public String toString() {
        return this.anno + this.sezione.toString() + " - " + this.annoScolastico;
    }
}
