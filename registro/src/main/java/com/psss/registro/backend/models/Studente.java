package com.psss.registro.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"classe"}, callSuper = true)
@Entity(name = "studenti")
public class Studente extends Persona {

    @ManyToOne
    private Classe classe;

    public void setClasse(Classe classe){
        this.classe = classe;
        if (this.classe != null) {
            classe.addStudente(this);
        }
    }

    public String toString() {
        return this.getNome() + " " + this.getCognome();
    }
}