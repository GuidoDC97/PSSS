package com.psss.registro.repositories;

import com.psss.registro.models.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudenteRepository extends JpaRepository<Studente, Long> {

    List<Studente> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);

    @Transactional
    Long deleteByNomeAndCognome(String nome, String cognome);

}
