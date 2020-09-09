package com.psss.registro.backend.models;

import com.psss.registro.security.User;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "studenti") @ToString(exclude = {"classe"}) @EqualsAndHashCode(exclude = {"classe", "storicoClassi"})
public class Studente extends User {

//    @Id @GeneratedValue(strategy= GenerationType.AUTO)
//    private Long id;
    private String nome;
    private String cognome;
    @Column(unique=true)
    private String codiceFiscale;
    private LocalDate data;
    private Character sesso;
    private String telefono;
    
    @ManyToOne
    private Classe classe;
    @ManyToMany
    private Set<Classe> storicoClassi;
    //TODO: non si riesce a tenere traccia delle classi di uno studente così come lo abbiamo gestito.

    public Studente(String username, String nome, String cognome, String codiceFiscale, Character sesso, LocalDate data,
                    String telefono, Classe classe) {

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
        this.classe = classe;
    }

//    public void setClasse(Classe classe){
//        this.classe = classe;
//        classe.addStudente(this);
//    }
}