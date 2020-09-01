package com.psss.registro.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "docenti")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
//                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
public class Docente {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
//    private GregorianCalendar data;
//    private String codiceFiscale;

//    @OneToMany(mappedBy = "docente")
//    //@JsonBackReference
//    private List<Nota> note;
//    @OneToMany(mappedBy = "docente")
//    private List<Voto> voti;
//    @OneToMany(mappedBy = "docente")
//    private List<Assegno> assegni;
//    @ManyToMany
//    private List<Materia> materie;
//    @ManyToMany
//    private List<Classe> classi;
//    @OneToMany(mappedBy = "docente")
//    private List<AttivitaDidattica> attivitadidattiche;
//    //TODO gestire l'orario del docente

    public Docente(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }


}
