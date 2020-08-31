package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "classi", "docenti", "assegni"})
@ToString(exclude = {"classi", "docenti", "assegni"})
public class Materia {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String codice;
    private String materia;

    @ManyToMany(mappedBy = "materie")
    private List<Classe> classi = new ArrayList<>();
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "materie")
    private List<Docente> docenti = new ArrayList<>();
    @OneToMany(mappedBy = "materia")
    private List<Assegno> assegni = new ArrayList<>();
    //TODO: si vuole tenere traccia di tutti gli assegni per ciascuna materia?

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void addClasse(Classe classe){
        getClassi().add(classe);
    }
}
