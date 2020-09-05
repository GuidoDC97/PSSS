package com.psss.registro.models;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie") @ToString(exclude = {"classi","docenti"})
@EqualsAndHashCode(exclude = {"id","nome","classi", "docenti"})
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
