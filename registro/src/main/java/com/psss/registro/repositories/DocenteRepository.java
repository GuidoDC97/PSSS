package com.psss.registro.repositories;

import com.psss.registro.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findByNomeContainingIgnoreCaseOrCognomeContainingIgnoreCase(String nome, String cognome);

    @Transactional @Modifying
    Long deleteByNomeAndCognome(String nome, String cognome);

}
