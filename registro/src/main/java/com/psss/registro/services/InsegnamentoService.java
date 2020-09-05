package com.psss.registro.services;

import com.psss.registro.models.Insegnamento;
import com.psss.registro.repositories.InsegnamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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

    public void createInsegnamento() {


    }
}
