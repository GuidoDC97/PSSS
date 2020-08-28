package com.psss.registroElettronico.backend.repository;

import com.psss.registroElettronico.backend.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findByNomeAndCognome(String nome, String cognome);


}
