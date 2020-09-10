package com.psss.registro.backend.models;

import com.psss.registro.app.security.User;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @NotBlank(message = "Inserire il nome")
    @Size(min = 1, max = 50, message = "Il nome deve essere compreso fra 1 e 50 caratteri")
    private String nome;
    @NotBlank(message = "Inserire il cognome")
    @Size(min = 1, max = 50, message = "Il cognome deve essere compreso fra 1 e 50 caratteri")
    private String cognome;
    //@Column(unique=true)
    @NotBlank(message = "Inserire il codice Fiscale")
    @Size(min = 1, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    @Pattern(regexp = "/^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$/i", message = "Inserire un codice fiscale valido")
    private String codiceFiscale;
    @Past
    @NotEmpty(message = "Inserire la data")
    private LocalDate data;
    @NotBlank(message = "Selezionare il sesso")
    @Size(min = 1,max = 1, message = "Il sesso dev'essere un carattere")
    private Character sesso;
    @NotBlank(message = "Inserire il numero di telefono")
    @Digits(message = "Inserire un numero di telefono valido", integer = 10, fraction = 0)
    private String telefono;
    
    @ManyToOne
    private Classe classe;
    @ManyToMany
    private Set<Classe> storicoClassi;
    //TODO: non si riesce a tenere traccia delle classi di uno studente così come lo abbiamo gestito.

    //TODO: Controllare se sono necessari i costruttori con questi argomenti e quello senza argomenti
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


    public void setClasse(Classe classe){

        this.classe = classe;
        if (this.classe != null) {
            classe.addStudente(this);
        }
    }

    public String toString() {
        return nome + " " + cognome;
    }
}