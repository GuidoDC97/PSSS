package com.psss.registro.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.psss.registro.security.User;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "studenti")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
//                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
public class Studente extends User {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate data;
    private String codiceFiscale;
    private Character sesso;
    private String telefono;

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

    public Studente(String nome, String cognome, LocalDate data, String codiceFiscale, Character sesso, String email, String telefono) {
        super(email, "");

        String password = nome+"."+cognome;
        password = password.replaceAll("[^a-zA-Z0-9]", "");
        this.setPassword(password);

        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.data = data;
        this.telefono = telefono;
    }

}