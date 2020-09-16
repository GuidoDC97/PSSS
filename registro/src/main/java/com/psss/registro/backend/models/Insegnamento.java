package com.psss.registro.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "insegnamenti")
public class Insegnamento extends AbstractEntity{

    @ManyToOne
    @NotNull(message = "Selezionare il docente")
    private Docente docente;

    @ManyToOne
    @NotNull(message = "Selezionare la materia")
    private Materia materia;

    @ManyToOne
    @NotNull(message = "Selezionare la classe")
    private Classe classe;

    public Insegnamento(Docente docente, Materia materia, Classe classe) {
        this.docente = docente;
        this.materia = materia;
        this.classe = classe;
    }

    public void setDocente(Docente docente){
        this.docente = docente;
        docente.getInsegnamenti().add(this);
    }

    public void setClasse(Classe classe){
        this.classe = classe;
        classe.getInsegnamenti().add(this);
    }

    public String toString() {
        return docente.toString() + " (" + materia.getNome() + ")";
    }
}
