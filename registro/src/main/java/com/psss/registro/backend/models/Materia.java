package com.psss.registro.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString(exclude = {"docenti"})
@EqualsAndHashCode(exclude = {"nome", "docenti"}, callSuper = false)
@Entity(name = "materie")
public class Materia extends AbstractEntity{

    @NotBlank(message = "Inserire il codice")
    @Size(min = 1, max = 10, message = "Il codice deve essere compreso fra 1 e 10 caratteri")
    private String codice;

    @NotBlank(message = "Inserire il nome")
    @Size(min = 1, max = 50, message = "Il nome deve essere compreso fra 1 e 50 caratteri")
    private String nome;

    @ManyToMany(mappedBy = "materie") @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Docente> docenti;

    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
        this.docenti = new HashSet<>();
    }

    public Materia() {
        this.docenti = new HashSet<>();
    }

    public void addDocente(Docente docente){
        docenti.add(docente);
    }

    public void removeDocente(Docente docente) {
        docenti.remove(docente);
    }

    @PreRemove
    public void preRemove(){

        //TODO: gestire l'eliminazione degli insegnamenti di una materia
//        for(Docente docente : docenti){
//            Set<Insegnamento> insegnamenti = docente.getInsegnamenti();
//            for(Insegnamento insegnamento : insegnamenti){
//                if(insegnamento.getMateria().equals(this)){
//                    insegnamento.getClasse().removeInsegnamento(insegnamento);
//                    docente.removeInsegnamento(insegnamento);
//                }
//            }
//        }
//Con lambda
//        docenti.forEach(docente -> {
//            docente.getInsegnamenti().forEach(insegnamento -> {
//                if(insegnamento.getMateria().equals(this))
//                        docente.removeInsegnamento(insegnamento);
//            });
//        });


        //Gestione relazione materia-docente
        for(Docente docente : docenti){
            docente.removeMateria(this);
        }
    }
}

