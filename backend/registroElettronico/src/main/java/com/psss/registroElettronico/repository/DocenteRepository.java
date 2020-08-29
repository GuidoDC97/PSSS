package com.psss.registroElettronico.repository;

import com.psss.registroElettronico.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findByNomeAndCognome(String nome, String cognome);


}
