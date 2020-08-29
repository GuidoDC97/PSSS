package com.psss.registroElettronico.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "docenti")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
public class Docente {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
//    private GregorianCalendar data;
//    private String codiceFiscale;

<<<<<<< Updated upstream
=======

    @OneToMany(mappedBy = "docente")
    //@JsonBackReference
    private List<Nota> note;
    @OneToMany(mappedBy = "docente")
    private List<Voto> voti;
    @OneToMany(mappedBy = "docente")
    private List<Assegno> assegni;
>>>>>>> Stashed changes
    @ManyToMany
    private List<Materia> materie;
    @ManyToMany
    private List<Classe> classi;
<<<<<<< Updated upstream
=======
    @OneToMany(mappedBy = "docente")
    private List<AttivitaDidattica> attivitadidattiche;
    //TODO gestire l'orario del docente



>>>>>>> Stashed changes
}
