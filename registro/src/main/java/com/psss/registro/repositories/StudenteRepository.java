package com.psss.registro.repositories;

import com.psss.registro.models.Studente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudenteRepository extends JpaRepository<Studente, Long> {
}
