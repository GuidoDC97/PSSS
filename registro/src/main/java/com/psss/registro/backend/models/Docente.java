package com.psss.registro.backend.models;

import com.psss.registro.security.User;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "docenti") @ToString(exclude = {"materie", "classi"})
@EqualsAndHashCode(exclude = {"materie", "classi", "insegnamenti"})
public class Docente extends User {

//    @Id @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String nome;
    private String cognome;
    @Column(unique=true)
    private String codiceFiscale;
    private Character sesso;
    private LocalDate data;
    private String telefono;
    @ManyToMany @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Materia> materie;
//    @ManyToMany
//    private Set<Classe> classi;
    @OneToMany(mappedBy = "docente", cascade = CascadeType.REMOVE) @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Insegnamento> insegnamenti;

    public Docente(String username, String nome, String cognome, String codiceFiscale, Character sesso,
                   LocalDate data, String telefono, Set<Materia> materie) {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = nome.replaceAll("[^a-zA-Z]", "").toLowerCase()
                + "." + cognome.replaceAll("[^a-zA-Z]", "").toLowerCase();
        this.setPassword(passwordEncoder.encode(password));
        this.setUsername(username);

        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.data = data;
        this.telefono = telefono;
        this.materie = materie;
//        this.classi = classi;
        this.insegnamenti = new HashSet<Insegnamento>();
    }

    public String getDocente() {
        return (this.nome + " " + this.cognome);
    }

    public void addMateria(Materia materia) {
        materie.add(materia);
    }

    public void removeMateria(Materia materia) {
        materie.remove(materia);
    }

//    public void addClasse(Classe classe) {
//        classi.add(classe);
//    }
//
//    public void removeClasse(Classe classe) {
//        classi.remove(classe);
//    }

    public void addInsegnamento(Insegnamento insegnamento) {
        insegnamenti.add(insegnamento);
    }

    public void removeInsegnamento(Insegnamento insegnamento) {
        insegnamenti.remove(insegnamento);
    }}
