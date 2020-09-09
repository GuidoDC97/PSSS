package com.psss.registro.ui.segretario.components.studenti;


import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Studente;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class StudenteForm extends FormLayout {

    private final TextField nome = new TextField("Nome");
    private final TextField cognome= new TextField("Cognome");
    private final TextField codiceFiscale = new TextField("Codice Fiscale");
    private final EmailField email = new EmailField("Email");
    private final DatePicker dataNascita = new DatePicker("Data di nascita");
    private final ComboBox<Character> sesso = new ComboBox<>("Sesso");
    private final TextField numeroTelefono = new TextField("Telefono");
    private final ComboBox<Classe> classe = new ComboBox<>("Classe");

    private final Binder<Studente> binder = new BeanValidationBinder<>(Studente.class);


    public StudenteForm() {
        nome.setClearButtonVisible(true);
        nome.addValueChangeListener(e->{
            nome.setValue(nome.getValue().toUpperCase());
        });

        sesso.setItems('M','F');
       // classe.setItems(classi);
       // classe.setItemLabelGenerator(Classe::getClasse);
        add(nome, cognome,codiceFiscale,email,dataNascita,sesso,numeroTelefono,classe);
        binder.bindInstanceFields(this);
    }

    public Binder<Studente> getBinder() {
        return binder;
    }

    public TextField getNome() {
        return nome;
    }
}
