package com.psss.registro.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.psss.registro.security.User;
import com.psss.registro.security.UserAuthority;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "docenti")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "note", "voti", "assegni",
//                        "materie", "classi", "attivitadidattiche"})
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
@EqualsAndHashCode(exclude = {"id","nome","cognome","sesso","data","telefono"})
public class Docente extends User {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private Character sesso;
    private LocalDate data;
//    private String email;
    private String telefono;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Materia> materie;
    @ManyToMany
    private List<Classe> classi;

//    @OneToMany(mappedBy = "docente")
//    @OneToMany(mappedBy = "docente")
    //@JsonBackReference
//    private List<Nota> note;
//    @OneToMany(mappedBy = "docente")
//    private List<Voto> voti;
//    @OneToMany(mappedBy = "docente")
//    private List<Assegno> assegni;
//    private List<AttivitaDidattica> attivitadidattiche;
//    //TODO gestire l'orario del docente

    public Docente(String nome, String cognome, String codiceFiscale, Character sesso, LocalDate data, String email, String telefono) {

        super(email, "");

        String password = nome+"."+cognome;
        password = password.replaceAll("[^a-zA-Z0-9]", "");
        this.setPassword(password);

        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.data = data;
//        this.email = email;
        this.telefono = telefono;

        this.materie = new ArrayList<>();
    }

    public Set<Materia> getMaterie() {
        Set<Materia> set = new HashSet<>(this.materie);
        return set;
    }

    public void setMaterie(Set<Materia> materie) {
        List<Materia> list = new ArrayList<>(materie);
        this.materie = list;
    }

    public void addMateria(Materia materia) {
        materie.add(materia);
    }

    public void removeMateria(Materia materia) {
        materie.remove(materia);
    }
}
