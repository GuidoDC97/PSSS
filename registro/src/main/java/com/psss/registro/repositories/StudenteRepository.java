package com.psss.registro.repositories;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Studente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudenteRepository extends JpaRepository<Studente, Long> {

    List<Studente> findByClasse(Classe classe);

}

