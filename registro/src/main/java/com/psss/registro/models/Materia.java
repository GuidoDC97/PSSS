package com.psss.registro.models;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "materie")
@ToString(exclude = {"docenti", "classi"})
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
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany(mappedBy = "materie")
    private List<Docente> docenti = new ArrayList<>();

    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.docenti = new ArrayList<>();
    }

    public void addDocente(Docente docente){
        getDocenti().add(docente);
    }

    public void addClasse(Classe classe){
        getClassi().add(classe);
    }

    public void removeDocente(Docente docente) {
        docenti.remove(docente);
    }
}

