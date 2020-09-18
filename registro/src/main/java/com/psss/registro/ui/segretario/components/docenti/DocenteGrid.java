package com.psss.registro.ui.segretario.components.docenti;

import com.psss.registro.backend.models.Docente;
import com.psss.registro.backend.services.ServiceFacade;

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

public class DocenteGrid extends Div {

    private final Grid<Docente> grid = new Grid<>(Docente.class);

    private final TextField filtro = new TextField();

    private final Button aggiungi = new Button("Aggiungi");

    private DocenteDialog dialog;

    private DocenteEditor editor;

    private ServiceFacade serviceFacade;

    private List<Docente> docenti;

    public DocenteGrid(ServiceFacade serviceFacade) {
        setId("grid-wrapper");
        setWidthFull();

        this.serviceFacade = serviceFacade;

        docenti = this.serviceFacade.findAllDocenti();

        grid.setColumns("nome", "cognome", "codiceFiscale", "sesso", "data", "username", "telefono");
        grid.getColumnByKey("username").setHeader("E-mail");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
        grid.setItems(docenti);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Docente docente = event.getValue();

            editor.getForm().getBinder().readBean(docente);
            editor.setVisible(!event.getHasValue().isEmpty());
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
            Set<Docente> foundDocenti = docenti.stream()
                    .filter(docente -> docente.getNome().toLowerCase().startsWith(event.getValue().toLowerCase()) ||
                            docente.getCognome().toLowerCase().startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            grid.setItems(foundDocenti);
        });

        aggiungi.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        aggiungi.addClickListener(event -> {
            dialog = new DocenteDialog(this.serviceFacade);
            dialog.setGrid(this);
            dialog.open();
        });

        toolBarLayout.add(filtro, aggiungi);

        return toolBarLayout;
    }

    public Grid<Docente> getGrid() {
        return grid;
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public void setEditor(DocenteEditor editor) {
        this.editor = editor;
    }
}
