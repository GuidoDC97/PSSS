package com.psss.registro.ui.segretario.components.materie;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.models.Materia;
import com.psss.registro.backend.services.ServiceFacade;

import com.psss.registro.ui.segretario.abstractComponents.AbstractEditor;
import com.psss.registro.ui.segretario.abstractComponents.AbstractGrid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class MateriaGrid extends Div implements AbstractGrid {

    private final Grid<Materia> grid = new Grid<>(Materia.class);

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");

    private MateriaDialog dialog;

    private MateriaEditor editor;

    private ServiceFacade serviceFacade;

    private List<Materia> materie;

    public MateriaGrid(ServiceFacade serviceFacade) {
        setId("grid-wrapper");
        setWidthFull();

        this.serviceFacade = serviceFacade;
        materie = this.serviceFacade.findAllMaterie();

        grid.setColumns("codice", "nome");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(materie);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Materia materia = event.getValue();

            editor.getForm().getBinder().readBean(materia);
            editor.setVisible(!event.getHasValue().isEmpty());

            editor.getDetails().setOpened(false);
            editor.getListBox().setItems((Docente) null);

            if(editor.isVisible()) {
                editor.getListBox().setItems(materia.getDocenti());
            }
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
        filtro.addValueChangeListener(event -> {
            Set<Materia> foundMateria = materie.stream()
                    .filter(materia -> materia.getCodice().startsWith(event.getValue().toUpperCase()) ||
                            materia.getNome().toLowerCase().startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundMateria);
        });

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiungi.addClickListener(event -> {
            dialog = new MateriaDialog(this.serviceFacade);
            dialog.setGrid(this);
            dialog.open();
        });

        toolBarLayout.add(filtro, aggiungi);

        return toolBarLayout;
    }

    public Grid<Materia> getGrid() {
        return grid;
    }

    public List<Materia> getMaterie() {
        return materie;
    }

    public void setEditor(AbstractEditor editor) {
        this.editor = (MateriaEditor) editor;
    }
}
