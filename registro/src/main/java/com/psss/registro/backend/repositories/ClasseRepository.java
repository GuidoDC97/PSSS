package com.psss.registro.backend.repositories;

import com.psss.registro.backend.models.Classe;

import com.psss.registro.backend.models.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    public List<Classe> findByAnnoScolastico(int annoScolastico);

    Classe findByStudenti(Studente studente);

    Optional<Classe> findByAnnoAndAnnoScolasticoAndSezione(int anno, int annoScolastico, Character sezione);

}

