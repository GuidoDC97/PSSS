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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
class StudenteServiceTest {

    @Autowired
    StudenteService studenteServiceTest;
    private Studente studenteTest;

    @BeforeEach
    void setUp() {
        String nomeTest = "nomeTest";
        String cognomeTest = "CognomeTest";
        Long id = 1L;
        String codiceFiscale = "DCHGDU97D22A783D";
        studenteTest = new Studente(id, nomeTest,cognomeTest,new GregorianCalendar(1990,01,01), codiceFiscale, new ArrayList<Assenza>(),
                new ArrayList<Ritardo>(), new ArrayList<Nota>(), new ArrayList<Voto>(), new Classe());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveAndFlush() {
        studenteServiceTest.saveAndFlush(studenteTest);

        List<Studente> studente = studenteServiceTest.findByCodiceFiscale(studenteTest.getCodiceFiscale());
        if(!studente.isEmpty()) {
            Assert.assertEquals(studenteTest.getCodiceFiscale(), studente.get(0).getCodiceFiscale());
            studenteServiceTest.deleteById(studente.get(0).getId());
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
        studenteServiceTest.saveAndFlush(studenteTest);

        List<Studente> studente = studenteServiceTest.findByCodiceFiscale(studenteTest.getCodiceFiscale());

        Optional<Studente> studenteOptional = studenteServiceTest.findById(studente.get(0).getId());
        if(studenteOptional.isPresent()){
            System.out.println(studenteOptional);
            Assert.assertEquals(studenteTest.getCodiceFiscale(), studenteOptional.get().getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }

        studenteServiceTest.deleteById(studente.get(0).getId());
    }

    @Test
    void findByNomeAndCognome() {
        studenteServiceTest.saveAndFlush(studenteTest);

        List<Studente> studente = studenteServiceTest.findByNomeAndCognome(studenteTest.getNome(),studenteTest.getCognome());
        if(!studente.isEmpty()){
            System.out.println(studente.get(0));
            Assert.assertEquals(studenteTest.getCodiceFiscale(), studente.get(0).getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }

        studenteServiceTest.deleteById(studente.get(0).getId());
    }

    @Test
    void findByCodiceFiscale() {
        studenteServiceTest.saveAndFlush(studenteTest);

        List<Studente> studente = studenteServiceTest.findByCodiceFiscale(studenteTest.getCodiceFiscale());
        if(!studente.isEmpty()){
            System.out.println(studente.get(0));
            Assert.assertEquals(studenteTest.getCodiceFiscale(), studente.get(0).getCodiceFiscale());
        }else{
            Assert.fail("Studente non trovato");
        }
    }

    @Test
    void deleteById() {
        studenteServiceTest.saveAndFlush(studenteTest);

        List<Studente> studente = studenteServiceTest.findByCodiceFiscale(studenteTest.getCodiceFiscale());
        studenteServiceTest.deleteById(studente.get(0).getId());

        studente = studenteServiceTest.findByCodiceFiscale(studenteTest.getCodiceFiscale());
        if(!studente.isEmpty())
            Assert.fail("Studente non eliminato");
    }


}