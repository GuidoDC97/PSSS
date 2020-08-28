package com.psss.registroElettronico.backend.repository;

import com.psss.registroElettronico.backend.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotoRepository extends JpaRepository<Voto, Long>{

    List<Voto> findByDocente(Long id);

}
