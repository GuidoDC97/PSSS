package com.psss.registroElettronico.backend.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.GregorianCalendar;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="docenti")
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


    private List<Nota> note;
    private List<Voto> voti;
    private List<Assegno> assegni;
    private List<Materia> materie;
    private List<Classe> classi;
    private List<AttivitaDidattica> attivitadidattiche;
    //TODO gestire l'orario del docente




}
