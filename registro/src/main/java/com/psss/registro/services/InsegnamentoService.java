package com.psss.registro.services;

import com.psss.registro.models.Classe;
import com.psss.registro.models.Docente;
import com.psss.registro.models.Insegnamento;
import com.psss.registro.models.Materia;
import com.psss.registro.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsegnamentoService {

    @Autowired
    InsegnamentoRepository insegnamentoRepository;

    public List<Insegnamento> findAll() {
        return insegnamentoRepository.findAll();
    }

    public Optional<Insegnamento> findById(Long id) {
        return insegnamentoRepository.findById(id);
    }

    public void deleteById(Long id) {
        insegnamentoRepository.deleteById(id);
    }

    public Insegnamento createInsegnamento(Docente docente, Materia materia, Classe classe) {

        Insegnamento insegnamento = new Insegnamento(docente, materia, classe);
        docente.addInsegnamento(insegnamento);
        classe.addInsegnamento(insegnamento);
        //TODO: verificare se è necessario salvare docente e classe

        return insegnamentoRepository.saveAndFlush(insegnamento);
    }
}
