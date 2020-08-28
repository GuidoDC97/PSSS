package com.psss.registroElettronico.backend.repository;

import com.psss.registroElettronico.backend.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByAnnoScolastico(int anno);
    List<Classe> findByAnnoScolasticoAndSezione(int anno, Character sezione);
}
