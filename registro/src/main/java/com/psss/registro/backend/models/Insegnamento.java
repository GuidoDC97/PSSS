package com.psss.registro.backend.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "insegnamenti") @ToString(exclude = {"docente", "materia", "classe"})
@EqualsAndHashCode(exclude = {"docente", "materia", "classe"})
public class Insegnamento extends AbstractEntity{

    @ManyToOne
    private Docente docente;
    @ManyToOne
    private Materia materia;
    @ManyToOne
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

    public String getDocenteMateria() {
        return docente.getDocente() + " (" + materia.getNome() + ")";
    }

    public String toString() {
        return docente.toString() + " (" + materia.getNome() + ")";
    }
}
