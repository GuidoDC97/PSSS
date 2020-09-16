package com.psss.registro.backend.repositories;

import com.psss.registro.backend.models.Docente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByCodiceFiscale(String codiceFiscale);
    Optional<Docente> findByUsername(String username);

}
