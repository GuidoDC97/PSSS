package com.psss.registro.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
//                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "docenti", "classi", "attivitadidattiche"})
@EqualsAndHashCode(exclude = {"id","nome","classi", "docenti"})
public class Materia{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String codice;
    private String nome;

    @ManyToMany(mappedBy = "materie")
    private List<Classe> classi = new ArrayList<>();
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToMany(mappedBy = "materie")
    private List<Docente> docenti = new ArrayList<>();
//    @OneToMany(mappedBy = "materia")
//    private List<Assegno> assegni = new ArrayList<>();


    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
    }

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void addClasse(Classe classe){
        getClassi().add(classe);
    }
}

