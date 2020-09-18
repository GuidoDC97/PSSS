package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Classe;
import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Insegnamento;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.ServiceFacade;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class InsegnamentoForm extends FormLayout {

    private final ComboBox<Docente> docente = new ComboBox<>("Docente");
    private final ComboBox<Materia> materia = new ComboBox<>("Materia");
    private final ComboBox<Classe> classe = new ComboBox<>("Classe");

    private final Binder<Insegnamento> binder = new BeanValidationBinder<>(Insegnamento.class);

    private ServiceFacade serviceFacade;

    public InsegnamentoForm(ServiceFacade serviceFacade) {

        this.serviceFacade = serviceFacade;

        docente.setItems(serviceFacade.findAllDocenti());
        docente.addValueChangeListener(event -> {
            if(event.getValue()!= null) {
                materia.setEnabled(true);
                materia.setItems(event.getValue().getMaterie());
            }
        });

        materia.setEnabled(false);
        materia.setItemLabelGenerator(Materia::getNome);

        classe.setReadOnly(true);

        add(docente, materia, classe);
        binder.bindInstanceFields(this);
    }

    public Binder<Insegnamento> getBinder() {
        return binder;
    }

    public ComboBox<Docente> getDocente() {
        return docente;
    }

    public ComboBox<Classe> getClasse() {
        return classe;
    }
}
