package com.psss.registroElettronico.backend.repository;
import com.psss.registroElettronico.backend.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MateriaRepository extends JpaRepository<Materia,Long> {
    List<Materia> findByCodice(String codice);
    List<Materia> findByMateria(String materia);

}


