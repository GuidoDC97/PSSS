package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.model.Materia;
import com.psss.registroElettronico.backend.repository.DocenteRepository;
import com.psss.registroElettronico.backend.repository.MateriaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    public List<Materia> findAll() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> findById(Long id) {
        return materiaRepository.findById(id);
    }

    public List<Materia> findByCodice(String codice) {
        return materiaRepository.findByCodice(codice);
    }
    public List<Materia> findByMateria(String materia) {
        return materiaRepository.findByMateria(materia);
    }

    public Materia saveAndFlush(Materia m) {
        return materiaRepository.saveAndFlush(m);
    }

    public void deleteById(Long id) {
        materiaRepository.deleteById(id);



//        Optional<Materia> materia = materiaRepository.findById(id);
//        if(materia.isPresent()){
//            List<Docente> docentiList = materia.get().getDocenti();
//
//            for(int i=0; i<docentiList.size(); i++){
//
//                List<Materia> materieList = docentiList.get(i).getMaterie();
//                for (int j=0; j<materieList.size();j++){
//                    if (materieList.get(j).equals(materia))
//                        materieList.remove(materieList.get(j));
//                        docenteRepository.saveAndFlush(docentiList.get(i));
//                }
//
//            }
//            materiaRepository.deleteById(id);
//        }

    }
}



