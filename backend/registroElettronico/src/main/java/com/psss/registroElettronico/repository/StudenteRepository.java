package com.psss.registroElettronico.repository;

import com.psss.registroElettronico.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudenteRepository extends JpaRepository<Studente, Long> {

    List<Studente> findByNomeAndCognome(String nome, String cognome);
}
