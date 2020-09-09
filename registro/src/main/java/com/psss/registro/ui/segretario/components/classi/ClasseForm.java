package com.psss.registro.ui.segretario.components.classi;

import com.psss.registro.backend.models.Classe;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;

public class ClasseForm extends FormLayout {

    private final ComboBox<Integer> anno = new ComboBox<> ("Anno");
    private final ComboBox<Character> sezione = new ComboBox<> ("Sezione");
    private final IntegerField annoScolastico = new IntegerField("Anno colastico");

    private final Binder<Classe> binder = new Binder<>(Classe.class);

    public ClasseForm(){
        anno.setAutofocus(true);
        anno.getElement().getClassList().add("full-width");
        anno.setItems(1,2,3,4,5);
        sezione.getElement().getClassList().add("full-width");
        sezione.setItems('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
        annoScolastico.setClearButtonVisible(true);
        annoScolastico.getElement().getClassList().add("full-width");
        add(anno,sezione,annoScolastico);
        binder.bindInstanceFields(this);
    }

    public Binder<Classe> getBinder() {
        return binder;
    }

    public ComboBox<Integer> getAnno() {
        return anno;
    }
}
