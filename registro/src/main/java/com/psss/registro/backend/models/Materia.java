package com.psss.registro.backend.models;

import com.psss.registro.backend.repositories.DocenteRepository;
import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "materie") @ToString(exclude = {"classi","docenti"})
@EqualsAndHashCode(exclude = {"id","nome", "docenti"})
public class Materia extends AbstractEntity{

//    @Id @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String codice;
    private String nome;
//    @ManyToMany(mappedBy = "materie")
//    private Set<Classe> classi;
    @ManyToMany(mappedBy = "materie") @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Docente> docenti;

    public Materia(String codice, String nome) {
        this.codice = codice;
        this.nome = nome;
//        this.classi = new HashSet<>();
        this.docenti = new HashSet<>();
    }
//    public void addClasse(Classe classe){
//        classi.add(classe);
//    }
//
//    public void removeClasse(Classe classe) {
//        classi.remove(classe);
//    }

    public void addDocente(Docente docente){
        docenti.add(docente);
    }

    public void removeDocente(Docente docente) {
        docenti.remove(docente);
    }

    @PreRemove
    public void preRemove(){

        //TODO: GESTIRE ELIMINAZIONE MATERIA E RELATIVI INSEGNAMENTI

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

