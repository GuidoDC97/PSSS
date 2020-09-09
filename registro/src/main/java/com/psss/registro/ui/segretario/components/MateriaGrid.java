package com.psss.registro.ui.segretario.components;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.MateriaService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.ArrayList;
import java.util.List;

public class MateriaGrid extends Div {

    private final Grid<Materia> grid = new Grid<>(Materia.class);
    private final TextField filtro = new TextField();
    private final Button aggiungi = new Button("Aggiungi");
    private final MateriaDialog dialog = new MateriaDialog();

    private MateriaEditor editor;

    private MateriaService materiaService;

    public MateriaGrid(MateriaService materiaService) {
        setId("grid-wrapper");
        setWidthFull();

        this.materiaService = materiaService;

        grid.setColumns("codice", "nome");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(materiaService.findAll());
        grid.asSingleSelect().addValueChangeListener(event -> {
            Materia materia = event.getValue();

            editor.getForm().getBinder().readBean(materia);
            editor.setVisible(!event.getHasValue().isEmpty());

//            docentiDetails.setOpened(false);
//            docentiList.setItems("");
//
//            if(editor.isVisible()) {
//                List<String> docenti = new ArrayList<>();
//                for (Docente docente : materia.getDocenti()) {
//                    docenti.add(docente.getNome() + " " + docente.getCognome());
//                }
//                docentiList.setItems(docenti);
//                docentiDetails.setContent(docentiList);
//            }
        });

        add(createToolbarLayout(), grid);
    }

    private HorizontalLayout createToolbarLayout() {
        HorizontalLayout toolBarLayout = new HorizontalLayout();
        toolBarLayout.setId("button-layout");
        toolBarLayout.setWidthFull();
        toolBarLayout.setSpacing(false);
        toolBarLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        filtro.setPlaceholder("Cerca...");
        filtro.setClearButtonVisible(true);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiungi.addClickListener(event -> dialog.open());

        toolBarLayout.add(filtro, aggiungi);

        return toolBarLayout;
    }

    public Grid<Materia> getGrid() {
        return grid;
    }

    public void setEditor(MateriaEditor editor) {
        this.editor = editor;
    }
}
