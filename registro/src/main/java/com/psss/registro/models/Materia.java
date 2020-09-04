package com.psss.registro.models;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "materie") @ToString(exclude = {"classi","docenti"})
@EqualsAndHashCode(exclude = {"id","nome","classi", "docenti"})
public class Materia{

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String codice;
    private String nome;
    @ManyToMany(mappedBy = "materie")
    private Set<Classe> classi;
    @ManyToMany(mappedBy = "materie") @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Docente> docenti;

    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.classi = new HashSet<>();
        this.docenti = new HashSet<>();
    }

    public void addClasse(Classe classe){
        classi.add(classe);
    }

    public void removeClasse(Classe classe) {
        classi.remove(classe);
    }

    public void addDocente(Docente docente){
        docenti.add(docente);
    }

    public void removeDocente(Docente docente) {
        docenti.remove(docente);
    }
}

