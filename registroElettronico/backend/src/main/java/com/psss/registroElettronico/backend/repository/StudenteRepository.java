package com.psss.registroElettronico.backend.repository;

import com.psss.registroElettronico.backend.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudenteRepository extends JpaRepository<Studente, Long> {

    List<Studente> findByNomeCognome(String nome, String cognome);
}
