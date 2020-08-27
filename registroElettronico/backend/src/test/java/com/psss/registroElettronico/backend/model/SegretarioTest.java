package com.psss.registroElettronico.backend.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class SegretarioTest {

    private Segretario segretario;

    @BeforeEach
    void setUp() {
        segretario = new Segretario(1L, "Fabio", "d'Andrea",
                new GregorianCalendar(1997,20,11),
                'M', "fabion20", "psss");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
    }

    @Test
    void getNome() {
    }

    @Test
    void getCognome() {
    }

    @Test
    void getCodiceFiscale() {
    }

    @Test
    void getData() {
    }

    @Test
    void getSesso() {
    }

    @Test
    void getUsername() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void setId() {
    }

    @Test
    void setNome() {
    }

    @Test
    void setCognome() {
    }

    @Test
    void setCodiceFiscale() {
    }

    @Test
    void setData() {
    }

    @Test
    void setSesso() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void setPassword() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void canEqual() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}