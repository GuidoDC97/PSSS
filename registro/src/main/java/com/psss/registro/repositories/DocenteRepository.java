package com.psss.registro.repositories;

import com.psss.registro.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findByNomeAndCognome(String nome, String cognome);
    Long deleteByNomeAndCognome(String nome, String cognome);

}
