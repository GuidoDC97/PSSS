package com.psss.registro.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "insegnamenti") @ToString(exclude = {"docente", "materia", "classe"})
@EqualsAndHashCode()
public class Insegnamento {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Docente docente;
    @ManyToOne
    private Materia materia;
    @ManyToOne
    private Classe classe;
}
