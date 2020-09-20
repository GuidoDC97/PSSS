package com.psss.registro.backend.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MateriaTest {

    private Materia materia;
    private Docente docente;
    private HashSet<Docente> docenteHashSet;

    @BeforeEach
    void setUp() {
        materia = new Materia("MAT", "Matematica");

        docente = new Docente();
        docente.setUsername("antman");
        docente.setPassword("leo");
        docente.setNome("Antimo");
        docente.setCognome("Iannucci");
        docente.setCodiceFiscale("AAAAAA11A11A111A");
        docente.setSesso('M');
        docente.setData(LocalDate.of(1997, 11, 20));
        docente.setTelefono("3339843344");

        docenteHashSet = new HashSet<>();
        docenteHashSet.add(docente);

        materia.setDocenti(docenteHashSet);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCodice() {
        assertEquals("MAT", materia.getCodice());
        assertNotEquals("FIS", materia.getCodice());
    }

    @Test
    void getNome() {
        assertEquals("Matematica", materia.getNome());
        assertNotEquals("Fisica", materia.getNome());
    }

    @Test
    void getDocenti() {
        HashSet<Docente> docenteHashSet = new HashSet<>();
        docenteHashSet.add(docente);

        assertIterableEquals(docenteHashSet, materia.getDocenti());
    }

    @Test
    void setCodice() {
        materia.setCodice("STO");

        assertEquals("STO", materia.getCodice());
        assertNotEquals("MAT", materia.getCodice());
    }

    @Test
    void setNome() {
        materia.setNome("Storia");

        assertEquals("Storia", materia.getNome());
        assertNotEquals("Matematica", materia.getNome());
    }

    @Test
    void setDocenti() {
        HashSet<Docente> docenteHashSet = new HashSet<>();

        Docente docenteSet = new Docente();
        docenteSet.setUsername("fabiod20");
        docenteSet.setPassword("leo");
        docenteSet.setNome("Fabio");
        docenteSet.setCognome("d'Andrea");
        docenteSet.setCodiceFiscale("AAAAAA11A11A111A");
        docenteSet.setSesso('M');
        docenteSet.setData(LocalDate.of(1997, 11, 20));
        docenteSet.setTelefono("3339843344");

        docenteHashSet.add(docente);
        docenteHashSet.add(docenteSet);

        materia.setDocenti(docenteHashSet);

        HashSet<Docente> docenteHashSet1 = new HashSet<>();

        Docente docenteSet1 = new Docente();
        docenteSet1.setUsername("fabiod20");
        docenteSet1.setPassword("leo");
        docenteSet1.setNome("Fabio");
        docenteSet1.setCognome("d'Andrea");
        docenteSet1.setCodiceFiscale("AAAAAA11A11A111A");
        docenteSet1.setSesso('M');
        docenteSet1.setData(LocalDate.of(1997, 11, 20));
        docenteSet1.setTelefono("3339843344");

        docenteHashSet1.add(docenteSet);
        docenteHashSet1.add(docente);

        assertIterableEquals(docenteHashSet1, materia.getDocenti());
    }

    @Test
    void testEquals() {
        Materia materiaEquals = new Materia("MAT", "Matematica");
        assertEquals(materia, materiaEquals);

        Materia materiaNotEquals = new Materia("FIS", "Fisica");
        assertNotEquals(materia, materiaNotEquals);
    }

    @Test
    void addDocente() {
        Docente docenteAdd = new Docente();
        docenteAdd.setUsername("fabiod20");
        docenteAdd.setPassword("leo");
        docenteAdd.setNome("Fabio");
        docenteAdd.setCognome("d'Andrea");
        docenteAdd.setCodiceFiscale("AAAAAA11A11A111A");
        docenteAdd.setSesso('M');
        docenteAdd.setData(LocalDate.of(1997, 11, 20));
        docenteAdd.setTelefono("3339843344");

        assertFalse(materia.getDocenti().contains(docenteAdd));

        materia.addDocente(docenteAdd);

        assertTrue(materia.getDocenti().contains(docenteAdd));
    }

    @Test
    void removeDocente() {
        assertTrue(materia.getDocenti().contains(docente));

        materia.removeDocente(docente);

        assertFalse(materia.getDocenti().contains(docente));
    }
}