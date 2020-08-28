package com.psss.registroElettronico.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.GregorianCalendar;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "note")
public class Nota {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private GregorianCalendar data;
    private String testo;


    @ManyToOne
    @JoinColumn(name="studente_id")
    private Studente studente;
    @ManyToOne
    @JoinColumn(name="docente_id")
    private Docente docente;
}
