package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "classi")
public class Classe {

    private int anno;
    private Character sezione;
    private int annoScolastico;

    private List<Materia> materie;
    private List<Assegno> assegni;
    private List<Docente> docenti;
    private List<Studente> studenti;



}
