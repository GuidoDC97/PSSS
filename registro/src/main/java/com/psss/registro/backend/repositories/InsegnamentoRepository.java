package com.psss.registro.backend.repositories;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsegnamentoRepository extends JpaRepository<Insegnamento, Long> {
    Optional<Insegnamento> findByDocenteAndMateriaAndClasse(Docente docente, Materia materia, Classe classe);
}
