package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.Materia;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MateriaServiceTest {

    @Autowired
    private MateriaService materiaServiceTest;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByCodice() {
    }

    @Test
    void findByMateria() {
    }

    @Test
    void saveAndFlush() {
    }

    @Test
    void deleteById() {

       List<Materia> materie = materiaServiceTest.findByCodice("MAT");
       Materia materia = materie.get(0);

       System.out.println(materia);

       if(materia != null) {
           materiaServiceTest.deleteById(materia.getId());
           Optional<Materia> materiaTest = materiaServiceTest.findById(materia.getId());
           Assert.assertEquals(Optional.empty(), materiaTest);
       }else{
           Assert.fail("Materia non trovata");
       }


    }
}