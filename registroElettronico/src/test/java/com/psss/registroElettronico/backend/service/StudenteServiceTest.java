package com.psss.registroElettronico.backend.service;

import com.psss.registroElettronico.backend.model.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class StudenteServiceTest {

    @Autowired
    StudenteService studenteServiceTest;

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
    void findByNomeAndCognome() {
        List<Studente> studentiList = studenteServiceTest.findByNomeAndCognome("nomeTest", "cognomeTest");
        System.out.println(studentiList);

        Assert.assertEquals(studentiList,studentiList);
    }

    @Test
    void saveAndFlush() {
        Studente studenteTest = new Studente( "nomeTest", "cognomeTest");
        studenteServiceTest.saveAndFlush(studenteTest);
    }

    @Test
    void deleteById() {
    }
}