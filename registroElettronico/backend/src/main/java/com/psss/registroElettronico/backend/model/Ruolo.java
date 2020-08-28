package com.psss.registroElettronico.backend.model;

import com.psss.registroElettronico.backend.model.Utente;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "ruoli")
public class Ruolo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;

    @OneToMany(targetEntity = Utente.class, mappedBy = "ruolo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Utente> utenti;

}
