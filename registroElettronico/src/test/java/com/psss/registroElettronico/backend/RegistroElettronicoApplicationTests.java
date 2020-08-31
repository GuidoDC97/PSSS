package com.psss.registroElettronico.backend;

import com.psss.registroElettronico.backend.model.Classe;
import com.psss.registroElettronico.backend.model.Docente;
import com.psss.registroElettronico.backend.model.Materia;
import com.psss.registroElettronico.backend.model.Studente;
import com.psss.registroElettronico.backend.repository.ClasseRepository;
import com.psss.registroElettronico.backend.repository.DocenteRepository;
import com.psss.registroElettronico.backend.repository.MateriaRepository;
import com.psss.registroElettronico.backend.repository.StudenteRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class RegistroElettronicoApplicationTests {

	@Autowired
	private DocenteRepository docenteRepository;
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private MateriaRepository materiaRepository;
	@Autowired
	private StudenteRepository studenteRepository;

	@Test
	void contextLoads() {
		Materia materia1 = new Materia();
		materia1.setCodice("MAT");
		materia1.setMateria("Matematica");;
		materiaRepository.saveAndFlush(materia1);

		Classe classe1 = new Classe();
		classe1.setAnno(5);
		classe1.setSezione('c');
		classe1.setAnnoScolastico(2020);
		classe1.addMateria(materia1);
		classeRepository.saveAndFlush(classe1);

		Studente studente1 = new Studente();
		studente1.setNome("Antimo");
		studente1.setCognome("Iannucci");
		studente1.setCodiceFiscale("AAAAAA00A00A000A");
		studente1.setDataNascita(new GregorianCalendar());
		studente1.addClasse(classe1);
		studenteRepository.saveAndFlush(studente1);


		Docente docente1 = new Docente();
		docente1.setNome("Fabio");
		docente1.setCognome("d'Andrea");
		docente1.setCodiceFiscale("ffffff00f00f000f");
		docente1.setDataNascita(new GregorianCalendar());
		docente1.addClasse(classe1);
		docente1.addMateria(materia1);
		docenteRepository.saveAndFlush(docente1);


//		//Prova LazyInitialization
//		System.out.println("Ricerco la materia");
//		Materia materia = materiaRepository.findByCodice("MAT").get(0);
//		System.out.println("Richiamo il metodo getDocenti()");
//		List<Docente> docenti = materia.getDocenti();
//		System.out.println("Richiedo la stampa dei docenti");
//		System.out.println(docenti);



//		Voto voto1 = new Voto();
//        voto1.setDocente(docente1);
//        voto1.setStudente(studente1);
//        voto1.setData(new GregorianCalendar());
//        voto1.setVoto(7);
//
//        Nota nota1 = new Nota();
//        nota1.setData(new GregorianCalendar());
//        nota1.setDocente(docente1);
//        nota1.setStudente(studente1);
//        nota1.setTesto("TESTO DELLA NOTA");
	}

}
