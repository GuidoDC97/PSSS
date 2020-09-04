package com.psss.registro.models;

import com.psss.registro.security.User;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "studenti")
@ToString(exclude = {"note", "voti", "assegni", "materie", "classi", "attivitadidattiche"})
@EqualsAndHashCode(exclude = {"id","nome","cognome","sesso","data","telefono"})
public class Studente extends User {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate data;
    private String codiceFiscale;
    private Character sesso;
    private String telefono;

    @ManyToOne
    private Classe classe;

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

    public Studente(String nome, String cognome, LocalDate data, String codiceFiscale, Character sesso, String email, String telefono, Classe classe) {
        super(email, "");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = nome.replaceAll("[^a-zA-Z]", "").toLowerCase()
                + "." + cognome.replaceAll("[^a-zA-Z]", "").toLowerCase();
        this.setPassword(passwordEncoder.encode(password));

        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.data = data;
        this.telefono = telefono;
        this.classe = classe;
    }


}