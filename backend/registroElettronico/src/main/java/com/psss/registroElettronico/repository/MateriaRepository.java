package com.psss.registroElettronico.repository;

import com.psss.registroElettronico.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia,Long> {
    List<Materia> findByCodice(String codice);
    List<Materia> findByMateria(String materia);

}


