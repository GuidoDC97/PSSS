package com.psss.registroElettronico.backend.repository;

import com.psss.registroElettronico.backend.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findByUsername(String username);
}
