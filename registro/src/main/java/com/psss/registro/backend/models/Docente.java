package com.psss.registro.backend.models;

import com.psss.registro.app.security.User;

import lombok.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity(name = "docenti")
@ToString(exclude = {"codiceFiscale","sesso","data","telefono","materie", "insegnamenti"})
@EqualsAndHashCode(exclude = {"materie", "classi", "insegnamenti"})
public class Docente extends User {


    @NotBlank(message = "Inserire il nome")
    @Size(min = 1, max = 50, message = "Il nome deve essere compreso fra 1 e 50 caratteri")
    private String nome;
    @NotBlank(message = "Inserire il cognome")
    @Size(min = 1, max = 50, message = "Il cognome deve essere compreso fra 1 e 50 caratteri")
    private String cognome;
    //@Column(unique = true)
    @NotBlank(message = "Inserire il codice Fiscale")
    @Size(min = 1, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    @Pattern(regexp = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$", message = "Inserire un codice fiscale valido")
    private String codiceFiscale;
    @NotNull(message = "Selezionare il sesso")
    private Character sesso;
    @Past(message = "La data dev'essere nel passato")
    @NotNull(message = "Inserire la data")
    private LocalDate data;
    @NotBlank(message = "Inserire il numero di telefono")
    @Size(min = 10, max = 10, message = "Il numero di telefono dev'essere di 10 cifre")
    @Digits(message = "Inserire un numero di telefono valido", integer = 10, fraction = 0)
    private String telefono;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Materia> materie;
    //    @ManyToMany
//    private Set<Classe> classi;
    @OneToMany(mappedBy = "docente", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Insegnamento> insegnamenti;

    public String getDocente() {
        return (this.nome + " " + this.cognome);
    }

    public void setMaterie(Set<Materia> materie) {
        this.materie = materie;
        for (Materia materia : materie) {
            materia.addDocente(this);
        }
    }

    //Vengono utilizzate nella pre-remove della materia
    public void addMateria(Materia materia) {
        materie.add(materia);
    }

    public void removeMateria(Materia materia) {
        materie.remove(materia);
    }

    public String toString() {
        return nome + " " + cognome;
    }
}
