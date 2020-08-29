package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Materia {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String codice;
    private String materia;

    @ManyToMany(mappedBy = "materie")
    private List<Classe> classi;
    @ManyToMany(mappedBy = "materie")
    private List<Docente> docenti;
    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Assegno> assegni;
    //TODO: si vuole tenere traccia di tutti gli assegni per ciascuna materia?
}
