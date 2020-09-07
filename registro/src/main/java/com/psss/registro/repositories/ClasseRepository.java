package com.psss.registro.repositories;

import com.psss.registro.models.Classe;

import com.psss.registro.models.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    public List<Classe> findByAnnoScolastico(int annoScolastico);

    Classe findByStudenti(Studente studente);

}

