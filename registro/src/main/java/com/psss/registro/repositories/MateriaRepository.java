package com.psss.registro.repositories;

import com.psss.registro.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    List<Materia> findByCodiceContainingIgnoreCaseOrNomeContainingIgnoreCase(String codice, String nome);

    //TODO: è necessaria l'annotation?
    @Transactional
    Long deleteByCodice(String codice);

}
