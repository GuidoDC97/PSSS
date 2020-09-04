package com.psss.registroElettronico.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "orario")
public class Orario {
    //TODO come riempire questa classe?
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
