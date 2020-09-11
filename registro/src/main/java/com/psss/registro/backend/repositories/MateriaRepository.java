package com.psss.registro.backend.repositories;

import com.psss.registro.backend.models.Materia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Optional<Materia> findByCodice(String codice);
}
