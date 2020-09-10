package com.psss.registro.ui.segretario.components.docenti;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.models.Studente;
import com.psss.registro.backend.services.MateriaService;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import com.vaadin.flow.data.validator.EmailValidator;
import org.vaadin.gatanaso.MultiselectComboBox;

public class DocenteForm extends FormLayout {

    private final TextField nome = new TextField("Nome");
    private final TextField cognome = new TextField("Cognome");
    private final TextField codiceFiscale = new TextField("Codice Fiscale");
    private final TextField telefono = new TextField("Telefono");

    private final ComboBox<Character> sesso = new ComboBox<>("Sesso");

    private final DatePicker data = new DatePicker("Data");

    private final EmailField username = new EmailField("E-mail");

    private final MultiselectComboBox<Materia> materie = new MultiselectComboBox<>("Materia");

    private final Binder<Docente> binder = new BeanValidationBinder<>(Docente.class);

    private MateriaService materiaService;

    public DocenteForm(MateriaService materiaService) {
        this.materiaService = materiaService;

        nome.setClearButtonVisible(true);

        cognome.setClearButtonVisible(true);

        codiceFiscale.setClearButtonVisible(true);
        codiceFiscale.addValueChangeListener(e->{
            codiceFiscale.setValue(codiceFiscale.getValue().toUpperCase());
        });

        telefono.setClearButtonVisible(true);

        sesso.setItems('M','F');

        username.setClearButtonVisible(true);
//        binder.forField(username)
//                .asRequired()
//                .withValidator(new EmailValidator("Inserire una e-mail valida"))
//                .bind(Docente::getUsername, Docente::setUsername);

        materie.setItems(materiaService.findAll());
        materie.setItemLabelGenerator(Materia::getNome);

        add(nome, cognome, codiceFiscale, username, data, sesso, telefono, materie);
        binder.bindInstanceFields(this);
    }

    public Binder<Docente> getBinder() {
        return binder;
    }

    public TextField getNome() {
        return nome;
    }
}
