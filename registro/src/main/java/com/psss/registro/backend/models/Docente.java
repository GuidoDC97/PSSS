package com.psss.registro.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"materie", "insegnamenti"}, callSuper = true)
@Entity(name = "docenti")
public class Docente extends Persona {

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Materia> materie;

    @OneToMany(mappedBy = "docente", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Insegnamento> insegnamenti;

    public void setMaterie(Set<Materia> materie) {
        this.materie = materie;
        for (Materia materia : materie) {
            materia.addDocente(this);
        }
    }

    public void removeMateria(Materia materia) {
        materie.remove(materia);
    }

    public String toString() {
        return this.getNome() + " " + this.getCognome();
    }
}
