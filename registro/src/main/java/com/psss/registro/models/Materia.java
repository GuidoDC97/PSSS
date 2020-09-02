package com.psss.registro.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materia")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
//                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "docenti", "classi", "attivitadidattiche"})
public class Materia{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String codice;
    private String nome;


    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
    }


    @ManyToMany(mappedBy = "materie")
    private List<Classe> classi = new ArrayList<>();
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "materie")
    private List<Docente> docenti = new ArrayList<>();
    @OneToMany(mappedBy = "materia")

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void addClasse(Classe classe){
        getClassi().add(classe);
    }
}

