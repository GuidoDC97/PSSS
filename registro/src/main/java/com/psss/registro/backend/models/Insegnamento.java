package com.psss.registro.backend.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "insegnamenti") @ToString(exclude = {"docente", "materia", "classe"})
@EqualsAndHashCode(exclude = {"docente", "materia", "classe"})
public class Insegnamento {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
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

    public String getDocenteMateria() {
        return docente.getDocente() + " (" + materia.getNome() + ")";
    }
}
