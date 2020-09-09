package com.psss.registro.ui.segretario.components;

import com.psss.registro.backend.models.Materia;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class MateriaForm extends FormLayout {

    private final TextField codice = new TextField("Codice");
    private final TextField nome = new TextField("Nome");

    private final Binder<Materia> binder = new Binder<>(Materia.class);

    public MateriaForm() {
        add(codice, nome);
        binder.bindInstanceFields(this);
    }

    public Binder<Materia> getBinder() {
        return binder;
    }
}
