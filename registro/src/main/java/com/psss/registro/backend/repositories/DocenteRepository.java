package com.psss.registro.backend.repositories;

import com.psss.registro.backend.models.Docente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<Docente, Long> {
}
