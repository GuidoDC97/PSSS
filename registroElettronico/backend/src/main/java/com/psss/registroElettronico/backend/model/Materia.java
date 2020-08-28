package com.psss.registroElettronico.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie")
public class Materia {
    @Id
    private String materia;

    @ManyToMany
    private List<Classe> classi;
    @ManyToMany
    private List<Docente> docenti;
    @OneToMany
    private List<Assegno> assegni;
}
