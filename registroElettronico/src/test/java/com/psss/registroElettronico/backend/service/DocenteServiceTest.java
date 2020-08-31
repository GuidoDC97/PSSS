package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DocenteServiceTest {

    @Autowired
    DocenteService docenteServiceTest;
    private Docente docenteTest;

    @BeforeEach
    void setUp() {
        String nomeTest = "nomeTest";
        String cognomeTest = "CognomeTest";
        Long id = 1L;
        String codiceFiscale = "DCHGDU97D22A783D";
        String codice = "MAT";
        String nomeMateria = "Matematica";


        ArrayList<Materia> materie = new ArrayList<Materia>();
        Materia materia = new Materia(id, codice, nomeMateria, new ArrayList<Classe>(), new ArrayList<Docente>(), new ArrayList<Assegno>());
        materie.add(materia);

        docenteTest = new Docente(id, nomeTest,cognomeTest, new GregorianCalendar(1990,01,01), codiceFiscale, new ArrayList<Nota>(),
                new ArrayList<Voto>(), new ArrayList<Assegno>(), materie, new ArrayList<Classe>(), new ArrayList<AttivitaDidattica>());
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void saveAndFlush() {
        // è NECESSARIO AVERE NEL DB PRIMA LA MATERIA ALTRIMENTI RESTITUISCE ERRORE
        docenteServiceTest.saveAndFlush(docenteTest);

        List<Docente> docente = docenteServiceTest.findByCodiceFiscale(docenteTest.getCodiceFiscale());
        if(!docente.isEmpty()) {
            Assert.assertEquals(docenteTest.getCodiceFiscale(), docente.get(0).getCodiceFiscale());
            docenteServiceTest.deleteById(docente.get(0).getId());
            //TODO: la gestione della data di nascita va effettuata in modo differente altrimenti non risultano uguali.
        }
        else
            Assert.fail("Studente non inserito");
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        docenteServiceTest.saveAndFlush(docenteTest);

        List<Docente> studente = docenteServiceTest.findByCodiceFiscale(docenteTest.getCodiceFiscale());

        Optional<Docente> docenteOptional = docenteServiceTest.findById(studente.get(0).getId());
        if(docenteOptional.isPresent()){
            System.out.println(docenteOptional);
            Assert.assertEquals(docenteTest.getCodiceFiscale(), docenteOptional.get().getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }

        docenteServiceTest.deleteById(studente.get(0).getId());
    }

    @Test
    void findByNomeAndCognome() {
        docenteServiceTest.saveAndFlush(docenteTest);

        List<Docente> docente = docenteServiceTest.findByNomeAndCognome(docenteTest.getNome(),docenteTest.getCognome());
        if(!docente.isEmpty()){
            System.out.println(docente.get(0));
            Assert.assertEquals(docenteTest.getCodiceFiscale(), docente.get(0).getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }

        docenteServiceTest.deleteById(docente.get(0).getId());
    }

    @Test
    void findByCodiceFiscale() {
        docenteServiceTest.saveAndFlush(docenteTest);

        List<Docente> docente = docenteServiceTest.findByCodiceFiscale(docenteTest.getCodiceFiscale());
        if(!docente.isEmpty()){
            System.out.println(docente.get(0));
            Assert.assertEquals(docenteTest.getCodiceFiscale(), docente.get(0).getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }
    }

    @Test
    void deleteById() {
        List<Docente> docenti = docenteServiceTest.findByNomeAndCognome("fabio", "d'Andrea");
        Docente docente = docenti.get(0);

        System.out.println(docente);

        if(docente != null){
            docenteServiceTest.deleteById(docente.getId());
            Optional<Docente> docenteTest = docenteServiceTest.findById(docente.getId());
            Assert.assertEquals(Optional.empty(), docenteTest);
        }else{
            Assert.fail("Docente non trovato");
        }

    }
}