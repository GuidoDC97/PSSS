package com.psss.registro.ui.segretario.components;

import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MateriaEditor extends Div {

    private final MateriaForm form = new MateriaForm();
    private final Button aggiorna = new Button("Aggiorna");
    private final Button elimina = new Button("Elimina");

    private MateriaGrid grid;

    private MateriaService materiaService;

    public MateriaEditor(MateriaService materiaService) {
        setId("editor-layout");

        this.materiaService = materiaService;

        Label titolo = new Label("Scheda materia");
        titolo.setClassName("bold-text-layout");

        Div formDiv = new Div();
        formDiv.setId("editor");
        formDiv.add(titolo, form);

        add(formDiv, createButtonLayout());
    }

    private HorizontalLayout createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(false);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        aggiorna.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        elimina.addThemeVariants(ButtonVariant.LUMO_ERROR);

        aggiorna.addClickListener(event -> {
            Materia materia = grid.getGrid().getSelectedItems().iterator().next();
            form.getBinder().writeBeanIfValid(materia);
            System.out.println(materia.toString());
        });

        buttonLayout.add(aggiorna, elimina);

        return buttonLayout;
    }

    public MateriaForm getForm() {
        return form;
    }

    public void setGrid(MateriaGrid grid) {
        this.grid = grid;
    }
}
