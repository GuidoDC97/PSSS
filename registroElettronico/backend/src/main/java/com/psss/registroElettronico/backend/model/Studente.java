package com.psss.registroElettronico.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="studenti")
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String cognome;
//    private GregorianCalendar data;

    private List<AssenzaRitardo> assenzeRitardi;
    private List<Nota> note;
    private List<Voto> voti;
    private List<VotoScrutinio> votiScrutinio;
    private Classe classe;
    //TODO Gestire l'orario dello studente

}
